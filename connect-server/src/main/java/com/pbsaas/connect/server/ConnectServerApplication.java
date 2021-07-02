package com.pbsaas.connect.server;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.proto.user.LoginReq;
import com.pbsaas.connect.server.config.MarsConfig;
import com.pbsaas.connect.server.mars.connect.ProtobufParseMap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.TimerTask;

@SpringBootApplication
@ComponentScan(basePackages ={ "com.pbsaas.connect.framework","com.pbsaas.connect.db.service","com.pbsaas.connect.server"})
@EnableJpaRepositories(basePackages ={ "com.pbsaas.connect.db.repository"})
@EntityScan(basePackages ={ "com.pbsaas.connect.db.entity"})
@EnableAutoConfiguration(exclude = {
		FreeMarkerAutoConfiguration.class
})
public class ConnectServerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ConnectServerApplication.class);

	private ServerBootstrap serverBootstrap;
	private ChannelFuture future;

	private ContextTimeoutChecker checker;

	@Autowired
	@Qualifier("unifiedChannelInitializer")
	private ChannelInitializer<SocketChannel> channelInboundHandler;

	@Autowired
	private MarsConfig marsConfig;

	public static void main(String[] args) {
		SpringApplication.run(ConnectServerApplication.class, args);
	}

	private void initProtobuf() {

		ProtobufParseMap.register(Connect.CmdID.CMD_LOGIN_VALUE, Connect.ActID.ACT_LOGIN_REQ_VALUE,
				LoginReq::parseFrom, LoginReq.class);

	}

	@PostConstruct
	public void init() {
		initProtobuf();
	}

	@Override
	public void run(String... args) throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.getDefaultFactory());

			serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(channelInboundHandler)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

			checker = new ContextTimeoutChecker();

			future = serverBootstrap.bind(marsConfig.getPort()).sync();

			// 获取绑定的端口号
			if (future.channel().localAddress() instanceof InetSocketAddress) {
				InetSocketAddress socketAddress = (InetSocketAddress)future.channel().localAddress();

				logger.info("MarsServer 启动了，address={}:{}", socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
			}

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

	@PreDestroy
	public void shutdown() {

		if (future != null && future.channel() != null) {

			if (future.channel().isOpen()) {
				future.channel().close();
			}
		}
		logger.debug("MarsServer  shutdown");
	}

	public  class ContextTimeoutChecker extends TimerTask {

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
