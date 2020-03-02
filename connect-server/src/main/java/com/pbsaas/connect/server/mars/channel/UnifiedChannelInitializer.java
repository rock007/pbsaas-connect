/**
 * sam@here 2019/9/29
 **/
package com.pbsaas.connect.server.mars.channel;

import com.pbsaas.connect.server.mars.handler.PortUnifiedServerHandler;
import com.pbsaas.connect.server.mars.logic.LogicManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("unifiedChannelInitializer")
public class UnifiedChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SslContext sslCtx;

    @Autowired
    private LogicManager logicManager;

    public UnifiedChannelInitializer() {

        // Configure SSL context
        // TODO 证书 sslCtx = SslContext.newServerContext(certificate, privateKey);
        logger.warn("暂不支持SSL，如果需要，请配置对应的证书文件");
        sslCtx = null;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        PortUnifiedServerHandler portUnificationServerHandler = new PortUnifiedServerHandler(sslCtx);

        portUnificationServerHandler.setLogicManager(logicManager);
        pipeline.addLast(portUnificationServerHandler);
    }

}
