package com.pbsaas.connect.server;

import com.pbsaas.connect.server.config.MarsConfig;
import com.pbsaas.connect.server.mars.cluster.MessageServerCluster;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.pbsaas.connect.service"})
public class MessageServerApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MessageServerApplication.class);

	private ServerBootstrap serverBootstrap;
	private ChannelFuture future;


	@Autowired
	@Qualifier("unifiedChannelInitializer")
	private ChannelInitializer<SocketChannel> channelInboundHandler;

	@Autowired
	private Registration registration;

	@Autowired
	private MarsConfig marsConfig;

	@Autowired
	private MessageServerCluster messageServerCluster;

	//外网IP
	private String ipadress;
	private String priorIP;
	private int port;

	private  boolean started=false;

	public static void main(String[] args) {
		SpringApplication.run(MessageServerApplication.class, args);
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

			// 绑定端口,开始接收进来的连接
			String registHost = registration.getHost();
			future = serverBootstrap.bind(registHost, marsConfig.getPort()).sync();

			// 获取绑定的端口号
			if (future.channel().localAddress() instanceof InetSocketAddress) {
				InetSocketAddress socketAddress = (InetSocketAddress)future.channel().localAddress();
				this.priorIP = marsConfig.getIp();
				this.ipadress = socketAddress.getAddress().getHostAddress();
				this.port = socketAddress.getPort();
				this.started = true;
				logger.info("MarsServer 启动了，address={}:{}", socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
			}

			// messageServerCluster
			messageServerCluster.registLocal(this.priorIP,this.ipadress,this.port);

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
}
