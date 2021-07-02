package com.pbsaas.connect.try_test.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端1联通了服务1....");
        ctx.fireChannelActive();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        System.out.println("客户端1接收到服务1发送过来的一条消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        ctx.fireChannelRead(byteBuf);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端1发生错误了...");
        cause.printStackTrace();
        ctx.close();
    }
}
