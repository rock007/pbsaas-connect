package com.pbsaas.connect.server.mars.coder;

import java.util.Arrays;

import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * socket 返回消息处理
 */
public final class PacketEncoder extends MessageToByteEncoder<ProtoMessage<MessageLite>> {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    protected void encode(final ChannelHandlerContext ctx, final ProtoMessage<MessageLite> protoMessage, final ByteBuf out) throws Exception {

        try {
            logger.trace("Protobuf encode started.");
            
            // [HEADER] data
            MsgHeader header = protoMessage.getHeader();
            
            byte[] bytes = protoMessage.getBody().toByteArray();
            int length = bytes.length;

            // Set the length of bytebuf
            header.setLength(MsgHeader.FIXED_HEADER_SKIP + length);
            
            byte[] allbytes = DataBufferCoder.encode(header).array();
            allbytes = Arrays.copyOf(allbytes, MsgHeader.FIXED_HEADER_SKIP + length);
            
            for (int i = 0; i < length; i++) {
                allbytes[i + 16] = bytes[i];
            }
            
            out.writeBytes(allbytes);
            logger.trace("Sent protobuf: commandId={}", header.getCmdId());
        } catch (Exception e) {
            logger.error("编码异常", e);
        } finally {
            logger.trace("Protobuf encode finished.");
        }
    }
}
