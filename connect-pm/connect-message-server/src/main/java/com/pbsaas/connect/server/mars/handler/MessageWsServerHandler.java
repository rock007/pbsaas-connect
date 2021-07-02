package com.pbsaas.connect.server.mars.handler;

import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.server.mars.logic.LogicManager;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * 用来处理websocket过来的消息
 */
public final class MessageWsServerHandler extends MessageServerHandler {

    private Logger logger = LoggerFactory.getLogger(MessageServerHandler.class);

    private WebSocketServerHandshaker handshaker;

    public MessageWsServerHandler(LogicManager logicManager) {
        super(logicManager);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            logger.debug("HttpRequest");
            // 完成 握手
            handleHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof ProtoMessage) {
            super.channelRead(ctx, msg);
        } else if (msg instanceof WebSocketFrame) {
            // websocketService.handleFrame(ctx, (WebSocketFrame) msg);
            // 这句应该走不到
            logger.debug("WebSocketFrame");
        } else {
            logger.debug("other:{}", msg);
        }
    }

    /**
     * 处理Http请求，完成WebSocket握手<br/>
     * 注意：WebSocket连接第一次请求使用的是Http
     * 
     * @param ctx
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest request)
            throws Exception {
        // 如果HTTP解码失败，返回HHTP异常
        if (!request.decoderResult().isSuccess()
                || (!"websocket".equals(request.headers().get("Upgrade")))) {

            sendHttpResponse(ctx, request,
                    new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://" + request.headers().get(HttpHeaderNames.HOST), null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 向客户端发送websocket握手,完成握手
            logger.debug("向客户端发送websocket握手,完成握手");
            handshaker.handshake(ctx.channel(), request);
        }
    }

    /**
     * Http返回
     * 
     * @param ctx
     * @param request
     * @param response
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest request,
            DefaultHttpResponse response) {
        // 返回应答给客户端
        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) || response.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
