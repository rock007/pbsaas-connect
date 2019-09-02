/**
*@Project: paintFriend_backend
*@Author: sam
*@Date: 2017年4月18日
*@Copyright: 2017  All rights reserved.
*/
package com.pbsaas.connect.server.mars.logicserver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.pbsaas.connect.server.mars.connect.ConnInfo;
import com.pbsaas.connect.server.mars.proxy.NetMsgHandler;
import com.pbsaas.connect.server.mars.connect.SessionPool;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author sam
 *
 */
@Component
public class MarsServer  implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MarsServer.class);
	
    private int port=10013;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    private ChannelHandler channelHandler;

    private ContextTimeoutChecker checker;

    /**
     *
     * 初始化
     */
    @PostConstruct
    public void init() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        channelHandler = new ChannelInitializerImpl<SocketChannel>();
    }

    @Override
    public void run(String... args) throws Exception{
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)
                    .childHandler(channelHandler)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            checker = new ContextTimeoutChecker();
            Timer timer = new Timer();
            timer.schedule(checker, 15 * 60 * 1000, 15 * 60 * 1000);

            ChannelFuture channelFuture = serverBootstrap
            		.bind(port)
            		.sync();
            channelFuture.channel()
            			.closeFuture()
            			.sync();
        }
        catch (Exception e) {
        	logger.error("ProxyServer:start", e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     *
     * @param <T>
     */
    public class ChannelInitializerImpl<T extends Channel> extends ChannelInitializer<T> {

        @Override
        protected void initChannel(T channel) throws Exception {
            channel.pipeline().addLast(new NetMsgHandler());
        }

    }
    
    @PreDestroy
    public void shutdown() {

        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.debug("ProxyServer  shutdown");
    }

    /**
     *
     */
    public class ContextTimeoutChecker extends TimerTask {

        @Override
        public void run() {

            //logger.info(LogUtils.format("check longlink alive per 15 minutes, " + this));

            for (ConnInfo conn : SessionPool.getAll().values()) {
                if (System.currentTimeMillis() - conn.getRefreshTime() > 15 * 60 * 1000) {

                    SessionPool.remove(conn.getChannel());

                    //logger.info(LogUtils.format("%s expired, deleted.", conn.toString()));
                }
            }
        }
    }
}
