/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.pbsaas.connect.server.mars.coder;

import java.util.List;

import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;

/**
 * 返回WebSocketFrame数据
 */
public class PacketWsFrameEncoder extends MessageToMessageEncoder<ProtoMessage<MessageLite>> implements WebSocketFrameEncoder {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoMessage<MessageLite> protoMessage,
            List<Object> out) throws Exception {

        try {
            logger.debug("Protobuf encode started.");
            
            // [HEADER] data
            MsgHeader header = protoMessage.getHeader();
            
            byte[] bytes = protoMessage.getBody().toByteArray();
            int length = bytes.length;

            // Set the length of bytebuf
            header.setLength(MsgHeader.FIXED_HEADER_SKIP+ length);
            
            ByteBuf allbytes = DataBufferCoder.encode(header).buffer;
            allbytes.writeBytes(bytes);
            
            // allbytes
            BinaryWebSocketFrame wsFrame = new BinaryWebSocketFrame(allbytes);
            
            out.add(wsFrame);
            logger.debug("Sent protobuf: commandId={}", header.getCmdId());
        } catch (Exception e) {
            logger.error("编码异常", e);
        } finally {
            logger.debug("Protobuf encode finished.");
        }
    
    }

}
