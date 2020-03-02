package com.pbsaas.connect.server.mars.handler;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.logic.LogicManager;
import com.pbsaas.connect.server.mars.model.MsgHeader;
import com.pbsaas.connect.server.mars.model.ProtoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息处理
 */
public class MessageServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(MessageServerHandler.class);

    private final LogicManager logicManager;

    public MessageServerHandler(LogicManager logicManager) {
        super();
        this.logicManager = logicManager;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        logger.debug("channel#handlerAdded");
        // 保存客户端连接
        // ClientConnectionMap.addClientConnection(ctx);
    }

    /**
     * 每当服务端断开客户端连接时,客户端的channel从ChannelGroup中移除,并通知列表中其他客户端channel
     * 
     * @param ctx 连接context
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        logger.debug("channel#handlerRemoved");
        logicManager.remove(ctx);
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {

        // 暂时只支持一次接收一个完整的消息
        // 如需要处理组合消息（长消息分多次发），需要另外处理，特别是解码逻辑
        ProtoMessage<MessageLite> message = (ProtoMessage<MessageLite>) object;

        // ctx.fireChannelReadComplete();
        MsgHeader header = message.getHeader();
        
        // 处理请求分发
        switch(header.getCmdId()) {

            case Connect.CmdID.CMD_ID_UNKNOWN_VALUE:
                logicManager.doLogin(ctx, header.getActId(), header, message.getBody());
                break;
            case Connect.CmdID.CMD_ID_FRIEND_VALUE:
                //好友

                break;
            case Connect.CmdID.CMD_ID_GROUP_VALUE:
                //群组
                logicManager.doGroup(ctx, header.getActId(), header, message.getBody());
                break;
            case Connect.CmdID.CMD_ID_MSG_VALUE:
                //消息
                logicManager.doMessage(ctx, header.getActId(), header, message.getBody());
                break;
            case Connect.CmdID.CMD_ID_NOOPING_VALUE:
                //心跳
                break;
/**
            case IMBaseDefine.ServiceID.SID_LOGIN_VALUE:
                handlerManager.doLogin(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_BUDDY_LIST_VALUE:
                handlerManager.doBuddyList(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_MSG_VALUE:
                handlerManager.doMessage(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_GROUP_VALUE:
                handlerManager.doGroup(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_OTHER_VALUE:
                handlerManager.doOther(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_FILE_VALUE:
                handlerManager.doFile(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_SWITCH_SERVICE_VALUE:
                handlerManager.doSwitch(ctx, header.getCommandId(), header, message.getBody());
                break;
            case IMBaseDefine.ServiceID.SID_AVCALL_VALUE: // for webrtc
                handlerManager.doWebrtc(ctx, header.getCommandId(), header, message.getBody());
                break;
            ***/
            default:
                logger.warn("暂不支持的服务ID{}" , header.getServiceId());
                break;
        }
    
    }

    /**
     * 服务端监听到客户端活动
     * 
     * @param ctx 连接context
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 服务端接收到客户端上线通知
        Channel incoming = ctx.channel();
        logger.debug("MessageServerHandler:" + incoming.remoteAddress() + "在线");
        logicManager.online(ctx);
        ctx.fireChannelActive();
    }

    /**
     * 服务端监听到客户端不活动
     * 
     * @param ctx 连接context
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 服务端接收到客户端掉线通知
        Channel incoming = ctx.channel();
        logger.debug("MessageServerHandler:" + incoming.remoteAddress() + "掉线");
        logicManager.offline(ctx);
        ctx.fireChannelInactive();
    }
    /**
     * 当服务端的IO 抛出异常时被调用
     * 
     * @param ctx 连接context
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        Channel incoming = ctx.channel();
        logger.debug("MessageServerHandler异常:{}", incoming.remoteAddress());

        // 异常出现就关闭连接
        ctx.fireExceptionCaught(cause).close();
    }
}
