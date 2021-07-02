package com.pbsaas.connect.try_test.client;

import com.google.protobuf.Any;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.proto.user.LoginReq;
import com.pbsaas.connect.try_test.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class TrySocketClient implements   Runnable{

    private String remoteIp="";

    private int port;

    public TrySocketClient(String ip,int port){

        this.remoteIp=ip;
        this.port=port;
    }

    @Override
    public  void run() {

        System.out.println("let's do this");

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 10013))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();

            System.out.println("客户端已经连接..");

            //登录

            //发送消息

            //login
            LoginReq loginReq=LoginReq.newBuilder()
                    .setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoiaGVsbG8gdGhlIGhlbGwiLCJ1c2VyX2lkIjoiMTM0NjI1ODIxMTQ5MzI1MzEyMCIsInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiYWxsIiwiYSIsImIiLCJjIl0sImV4cCI6MTYyNDg3NzMzNiwiaXNNYW4iOmZhbHNlLCJhdXRob3JpdGllcyI6WyLns7vnu5_nrqHnkIblkZgiXSwianRpIjoiZDhjYjlmNzQtOGI4OC00MDc4LTljZmYtMThlZDVjOGI3YzQ1IiwiY2xpZW50X2lkIjoidGVzdDEifQ.xsGnnsYF6YFHX7A5lcoupGC3Jrtz0bhlXYWNhifyFTM")
                    .build();

            final Connect.ReqBody reqBody = Connect.ReqBody.newBuilder()
                    .setCmdid(Connect.CmdID.CMD_LOGIN)
                    .setActid(Connect.ActID.ACT_LOGIN_REQ)
                    .setData(Any.pack(loginReq))
                    .build();

            future.channel().writeAndFlush(reqBody.toByteArray());

            future.channel().closeFuture().sync();

        }catch(Exception ex){
            ex.printStackTrace();
        } finally {

            eventLoopGroup.shutdownGracefully();
        }
    }

    private void reqLogin(){


    }

    private void sendMessage(Channel channel, ProtoMessage message) {

        ChannelFuture recChannel=channel.writeAndFlush(message);

        recChannel.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    //关闭连接
                    System.out.println("回复成功 ");
                } else {
                    System.err.println("回复失败");
                }
            }
        });
    }


}
