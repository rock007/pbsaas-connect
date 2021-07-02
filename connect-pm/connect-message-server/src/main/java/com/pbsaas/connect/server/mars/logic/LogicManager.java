/**
 * sam@here 2019/9/30
 **/
package com.pbsaas.connect.server.mars.logic;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.cluster.MessageServerCluster;
import com.pbsaas.connect.server.mars.logic.action.UserAction;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicManager {

    private static final Logger logger = LoggerFactory.getLogger(LogicManager.class);

    @Autowired
    private MessageServerCluster messageServerCluster;

    @Autowired
    private UserAction userAction;

    public void remove(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        Long userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

        // routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_OFFLINE);
        messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_OFFLINE);

        ClientUser clientUser = ClientsManager.getUserById(userId);
        if (clientUser != null) {
            clientUser.unValidateMsgConn(handleId, ctx);

            if (clientUser.isConnEmpty()) {
                ClientsManager.removeUser(clientUser);
            }
        }
    }

    /**
     * 掉线时的处理
     */
    public void offline(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        Long userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

        // routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_OFFLINE);
        messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_OFFLINE);

        ClientUser clientUser = ClientsManager.getUserById(userId);
        if (clientUser != null) {
            clientUser.unValidateMsgConn(handleId, ctx);
        }
    }


    /**
     * 连接时的处理
     */
    public void online(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        Long userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

        // routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_ONLINE);
        messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_ONLINE);

        ClientUser clientUser = ClientsManager.getUserById(userId);
        if (clientUser != null) {
            clientUser.validateMsgConn(handleId, ctx);
        }
    }

    /**
     * 处理用户是否登录
     */
    private boolean hasLogin(ChannelHandlerContext ctx) {

        if (ctx.channel().attr(ClientUser.USER_ID).get() != null) {
            return true;
        }
        return false;
    }

    public void doGroup(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) {

    }

    public void doMessage(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) {

    }

    public void doLogin(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) throws Exception {

        logger.debug("doLogin commandId: {}", actId);

        userAction.login(header, body, ctx);
        /**
        switch (commandId) {
            case LoginCmdID.CID_LOGIN_REQ_MSGSERVER_VALUE:
                // this was do at login_server
                logger.warn("this was do at login_server: commandId={}", commandId);
                break;
            case LoginCmdID.CID_LOGIN_REQ_USERLOGIN_VALUE:
                userAction.login(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_REQ_LOGINOUT_VALUE:
                imLoginHandler.logOut(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_KICK_USER_VALUE:
                imLoginHandler.kickUser(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_REQ_DEVICETOKEN_VALUE:
                imLoginHandler.deviceToken(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_REQ_KICKPCCLIENT_VALUE:
                imLoginHandler.kickPcClient(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_REQ_PUSH_SHIELD_VALUE:
                imLoginHandler.pushShield(header, body, ctx);
                break;
            case LoginCmdID.CID_LOGIN_REQ_QUERY_PUSH_SHIELD_VALUE:
                imLoginHandler.queryPushShield(header, body, ctx);
                break;
            default:
                logger.warn("Unsupport command id {}", commandId);
                break;
        }
        ***/
    }
}