/*
 * Copyright © 2013-2017 BLT, Co., Ltd. All Rights Reserved.
 */

package com.pbsaas.connect.server.mars.coder;

import java.util.List;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.connect.ProtobufParseMap;
import com.pbsaas.connect.server.mars.model.MsgHeader;
import com.pbsaas.connect.server.mars.model.ProtoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;

/**
 * 供WebSocketFrame使用的
 * <br>
 * 接收消息处理(Protobuf to IMProtoMessage)
 */
public class PacketWsFrameDecoder extends MessageToMessageDecoder<WebSocketFrame>  implements WebSocketFrameDecoder {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out)
            throws Exception {
        try {
            final ByteBuf in = msg.content();
            logger.debug("Protobuf decode started.");
            in.markReaderIndex();
            if (in.readableBytes() < 4) {
                logger.info("Readable Bytes length less than 4 bytes, ignored");
                in.resetReaderIndex();
                return;
            }

            DataBuffer dataBuf = new DataBuffer(in);

            MsgHeader header = new MsgHeader();
            if( !header.decode(dataBuf)){

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

            MessageLite content = ProtobufParseMap.getMessage(header.getCmdId(), header.getActId(), body);

            ProtoMessage<MessageLite> protoMessage = new ProtoMessage<>(header, content);
            out.add(protoMessage);

            final Connect.ReqBody request = Connect.ReqBody.parseFrom(header.getBody());

            out.add(request);

            logger.debug("Received protobuf : length={}, commandId={}", header.getLength(), header.getCmdId());
        } catch (Exception e) {
            logger.error(ctx.channel().remoteAddress() + ",decode failed.", e);
        } finally {
            logger.debug("Protobuf decode finished.");
        }
    
    }
    
}
