/**
 * sam@here 2019/9/29
 **/
package com.pbsaas.connect.server.mars;

import com.pbsaas.connect.server.mars.connect.ConnInfo;
import com.pbsaas.connect.server.mars.connect.SessionPool;
import com.pbsaas.connect.server.mars.logicserver.MarsServer;
import com.pbsaas.connect.server.mars.proxy.NetMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class ConnectServer  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ConnectServer.class);

    @Value("${mars.connect.port}")
    private int port=10013;

    //@Autowired
    //private Registration registration;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    @Autowired
    @Qualifier("unifiedChannelInitializer")
    private ChannelInitializer<SocketChannel> channelInboundHandler;

    /**
     *
     * 初始化
     */
    @PostConstruct
    public void init() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

    }

    @Override
    public void run(String... args) throws Exception{
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInboundHandler)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);


            ChannelFuture channelFuture = serverBootstrap
                    .bind(port)
                    .sync();
            channelFuture.channel()
                    .closeFuture()
                    .sync();
        }
        catch (Exception e) {
            logger.error("MarsServer:start", e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void shutdown() {

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.debug("MarsServer  shutdown");
    }

}
