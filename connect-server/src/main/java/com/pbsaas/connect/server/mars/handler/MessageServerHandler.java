package com.pbsaas.connect.server.mars.handler;

import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.logic.LogicManager;
import com.pbsaas.connect.server.mars.logic.SendMsgQueue;
import io.netty.util.ReferenceCountUtil;
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

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        logger.debug("channel#handlerRemoved");
        logicManager.remove(ctx);
        super.handlerRemoved(ctx);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {

        ProtoMessage<MessageLite> message = (ProtoMessage<MessageLite>) object;

        try{

            MsgHeader header = message.getHeader();
            //final Connect.ReqBody request = Connect.ReqBody.parseFrom(message.getBody().toByteArray());
            //PaintFriend.ParamList paramList = request.getData().unpack(PaintFriend.ParamList.class);

            // 处理请求分发
            switch (header.getCmdId()) {

                case Connect.CmdID.CMD_LOGIN_VALUE:
                    logicManager.doLogin(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_LOGOFF_VALUE:
                    logicManager.doLogout(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_FRIEND_VALUE:
                    //好友
                    break;
                case Connect.CmdID.CMD_GROUP_VALUE:
                    //群组
                    logicManager.doGroup(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_FILE_VALUE:
                    //文件
                    logicManager.doMessage(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_CALL_VALUE:
                    //webrtc
                    logicManager.doCall(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_PROFILE_VALUE:
                    //个人信息
                    //logicManager.doMessage(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_MSG_VALUE:
                    //消息
                    logicManager.doMessage(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_UNKNOWN_VALUE:
                    //其它
                    //logicManager.doMessage(ctx, header.getActId(), header, message.getBody());
                    break;
                case Connect.CmdID.CMD_NOOPING_VALUE:
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
                    logger.warn("暂不支持的服务ID{}", header.getServiceId());
                    break;
            }

        }catch (Exception ex){
            logger.error("运行发送消息任务出错：",ex);
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
