package com.pbsaas.connect.server.mars.handler;

import java.util.List;

import com.pbsaas.connect.server.mars.coder.PacketDecoder;
import com.pbsaas.connect.server.mars.coder.PacketEncoder;
import com.pbsaas.connect.server.mars.coder.PacketWsFrameDecoder;
import com.pbsaas.connect.server.mars.coder.PacketWsFrameEncoder;
import com.pbsaas.connect.server.mars.logic.LogicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 */
public class PortUnifiedServerHandler extends ByteToMessageDecoder {

    private final SslContext sslCtx;
    private final boolean detectSsl;
    private final boolean detectGzip;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LogicManager logicManager;

    public PortUnifiedServerHandler(SslContext sslCtx) {
        this(sslCtx, true, true);
    }

    private PortUnifiedServerHandler(SslContext sslCtx, boolean detectSsl, boolean detectGzip) {
        this.sslCtx = sslCtx;
        this.detectSsl = detectSsl;
        this.detectGzip = detectGzip;
    }

    public void setLogicManager(LogicManager logicManager) {
        this.logicManager = logicManager;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Will use the first five bytes to detect a protocol.
        if (in.readableBytes() < 5) {
            return;
        }

        if (isSsl(in)) {
            enableSsl(ctx);
        } else {
            final int magic1 = in.getUnsignedByte(in.readerIndex());
            final int magic2 = in.getUnsignedByte(in.readerIndex() + 1);
            
            if (isGzip(magic1, magic2)) {
                enableGzip(ctx);
            } else if (isHttp(magic1, magic2)) {
                switchToHttp(ctx);
            } else if (isFactorial(magic1)) {
                logger.debug("Factorial protocol is not supported");
                switchToFactorial(ctx);
            } else if (isNativeSocket(magic1, magic2)) {
                switchToSocket(ctx);
            } else {
                // Unknown protocol; discard everything and close the connection.
                logger.debug("Unknown protocol");
                in.clear();
                ctx.close();
            }
        }
    }

    private boolean isSsl(ByteBuf buf) {
        if (detectSsl) {
            return SslHandler.isEncrypted(buf);
        }
        return false;
    }

    private boolean isGzip(int magic1, int magic2) {
        if (detectGzip) {
            return magic1 == 31 && magic2 == 139;
        }
        return false;
    }

    private static boolean isHttp(int magic1, int magic2) {
        return
            magic1 == 'G' && magic2 == 'E' || // GET
            magic1 == 'P' && magic2 == 'O' || // POST
            magic1 == 'P' && magic2 == 'U' || // PUT
            magic1 == 'H' && magic2 == 'E' || // HEAD
            magic1 == 'O' && magic2 == 'P' || // OPTIONS
            magic1 == 'P' && magic2 == 'A' || // PATCH
            magic1 == 'D' && magic2 == 'E' || // DELETE
            magic1 == 'T' && magic2 == 'R' || // TRACE
            magic1 == 'C' && magic2 == 'O';   // CONNECT
    }

    private static boolean isFactorial(int magic1) {
        return magic1 == 'F';
    }

    private static boolean isNativeSocket(int magic1, int magic2) {
        return magic1 == 0x0 && magic2 == 0x0;
    }
    
    private void enableSsl(ChannelHandlerContext ctx) {
        ChannelPipeline p = ctx.pipeline();
        p.addLast("ssl", sslCtx.newHandler(ctx.alloc()));
        p.addLast("unificationA", new PortUnifiedServerHandler(sslCtx, false, detectGzip));
        p.remove(this);
    }

    private void enableGzip(ChannelHandlerContext ctx) {
        ChannelPipeline p = ctx.pipeline();
        p.addLast("gzipdeflater", ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
        p.addLast("gzipinflater", ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        p.addLast("unificationB", new PortUnifiedServerHandler(sslCtx, detectSsl, false));
        p.remove(this);
    }

    private void switchToHttp(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new WebSocketFrameAggregator(65536)); // Http消息组装  
        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持  
        pipeline.addLast("decoder", new PacketWsFrameDecoder());
        pipeline.addLast("encoder", new PacketWsFrameEncoder());
        pipeline.addLast("handler", new MessageWsServerHandler());
        
        pipeline.remove(this);
    }

    private void switchToFactorial(ChannelHandlerContext ctx) {
//        ChannelPipeline p = ctx.pipeline();
//        p.addLast("decoder", new BigIntegerDecoder());
//        p.addLast("encoder", new NumberEncoder());
//        p.addLast("handler", new FactorialServerHandler());
//        p.remove(this);
    }
    private void switchToSocket(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(400 * 1024, 0, 4, -4, 0));
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("handler", new MessageServerHandler(logicManager));
        pipeline.remove(this);
    }
}