package com.pbsaas.connect.server.mars.coder;

import java.util.List;

import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.connect.ProtobufParseMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * socket 接收消息处理
 *
 */
public final class PacketDecoder extends ByteToMessageDecoder {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {

        try {

            logger.trace("Protobuf decode started.");
            in.markReaderIndex();
            if (in.readableBytes() < 4) {
                logger.debug("Readable Bytes length less than 4 bytes, ignored");
                in.resetReaderIndex();
                return;
            }

            DataBuffer dataBuf = new DataBuffer(in);

            MsgHeader header = DataBufferCoder.decode(dataBuf);

            if (header==null) {
                ctx.close();
                logger.error("message length less than 0, channel closed");
                return;
            }

            ByteBuf byteBuf = ctx.alloc().buffer(header.getLength() - MsgHeader.FIXED_HEADER_SKIP);

            in.readBytes(byteBuf);
            byte[] body;
            if (byteBuf.hasArray()) {
                body = byteBuf.array();
            } else {
                body = new byte[byteBuf.capacity()];
                byteBuf.readBytes(body);
            }

            MessageLite msg = ProtobufParseMap.getMessage(header.getCmdId(), header.getActId(), body);

            ProtoMessage<MessageLite> protoMessage = new ProtoMessage<>(header, msg);
            out.add(protoMessage);

            /**
            final Connect.ReqBody request = Connect.ReqBody.parseFrom(header.getBody());

            out.add(request);
            ***/
            logger.trace("Received protobuf : length={}, commandId={}", header.getLength(), header.getCmdId());
        } catch (Exception e) {
            logger.error(ctx.channel().remoteAddress() + ",decode failed.", e);
        } finally {
            logger.trace("Protobuf decode finished.");
        }
    }

}
