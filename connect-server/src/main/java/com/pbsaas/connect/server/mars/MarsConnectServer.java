/**
 * sam@here 2019/9/29
 **/
package com.pbsaas.connect.server.mars;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.config.MarsConfig;
import com.pbsaas.connect.server.mars.connect.ProtobufParseMap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

//@Component
public class MarsConnectServer  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MarsConnectServer.class);

    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;

    private ContextTimeoutChecker checker;

    @Autowired
    @Qualifier("unifiedChannelInitializer")
    private ChannelInitializer<SocketChannel> channelInboundHandler;

    @Autowired
    private MarsConfig marsConfig;

    /**
     *
     * 初始化
     */
    @PostConstruct
    public void init() {
        initProtobuf();
    }

    @Override
    public void run(String... args) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            checker = new ContextTimeoutChecker();
            Timer timer = new Timer();
            timer.schedule(checker, 15 * 60 * 1000, 15 * 60 * 1000);

            InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.getDefaultFactory());

            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInboundHandler)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

            future = serverBootstrap.bind(marsConfig.getPort()).sync();

            // 获取绑定的端口号
            if (future.channel().localAddress() instanceof InetSocketAddress) {
                InetSocketAddress socketAddress = (InetSocketAddress)future.channel().localAddress();

                logger.info("MarsServer 启动了，address={}:{}", socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
            }

            // 等待服务器socket关闭
            future.channel().closeFuture().sync();
        }
        catch (Exception e) {
            logger.error("MarsServer:start", e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private void initProtobuf() {
/**
        ProtobufParseMap.register(ServiceID.SID_GROUP_VALUE, GroupCmdID.CID_GROUP_NORMAL_LIST_REQUEST_VALUE,
                IMGroup.IMNormalGroupListReq::parseFrom, IMGroup.IMNormalGroupListReq.class);

        ProtobufParseMap.register(ServiceID.SID_GROUP_VALUE, GroupCmdID.CID_GROUP_INFO_REQUEST_VALUE,
                IMGroup.IMGroupInfoListReq::parseFrom, IMGroup.IMGroupInfoListReq.class);

        ProtobufParseMap.register(Connect.CmdID.CMD_ID_UNKNOWN_VALUE, Connect.ActID.ACT_ID_LOGIN_VALUE,
               Connect.ReqBody::parseFrom, Connect.ReqBody.class);
***/
    }

    @PreDestroy
    public void shutdown() {

        if (future != null && future.channel() != null) {

            if (future.channel().isOpen()) {
                future.channel().close();
            }
        }
        logger.debug("MarsServer  shutdown");
    }

    public class ContextTimeoutChecker extends TimerTask {

        @Override
        public void run() {

            //logger.info(LogUtils.format("check longlink alive per 15 minutes, " + this));

            /**
            for (ConnInfo conn : SessionPool.getAll().values()) {
                if (System.currentTimeMillis() - conn.getRefreshTime() > 15 * 60 * 1000) {

                    SessionPool.remove(conn.getChannel());

                    //logger.info(LogUtils.format("%s expired, deleted.", conn.toString()));
                }
            }
            ***/
        }
    }
}
