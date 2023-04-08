/**
 * sam@here 2019/9/30
 **/
package com.pbsaas.connect.server.mars.logic;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.logic.action.UserAction;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogicManager {

    private static final Logger logger = LoggerFactory.getLogger(LogicManager.class);

    //@Autowired
    //private MessageServerCluster messageServerCluster;

    @Autowired
    private UserAction userAction;

    public void remove(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        String userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

        // routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_OFFLINE);
        //messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_OFFLINE);

        ClientUser clientUser = ClientUserManager.getUserById(userId);
        if (clientUser != null) {
            clientUser.unValidateMsgConn(handleId, ctx);

            if (clientUser.isConnEmpty()) {
                ClientUserManager.removeUser(clientUser);
            }
        }
    }

    /**
     * 掉线时的处理
     */
    public void offline(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        String userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

         //routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_OFFLINE);
        //messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_OFFLINE);

        ClientUser clientUser = ClientUserManager.getUserById(userId);
        if (clientUser != null) {
            clientUser.unValidateMsgConn(handleId, ctx);
        }
    }


    /**
     * 连接时的处理
     */
    public void online(ChannelHandlerContext ctx) {

        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        String userId = ctx.channel().attr(ClientUser.USER_ID).get();

        if (handleId != null) {
            // 关闭
        }
        if (userId == null) {
            return;
        }

        // routerHandler.sendUserStatusUpdate(ctx, UserStatType.USER_STATUS_ONLINE);
        //messageServerCluster.userStatusUpdate(userId, ctx, Connect.StateType.STATE_ONLINE);

        ClientUser clientUser = ClientUserManager.getUserById(userId);
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

        logger.debug("doGroup commandId: {}", actId);

        switch (actId) {
            case Connect.ActID.ACT_GROUP_ACCEPT_REQ_VALUE:
                //userAction.login(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_SEARCH_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_QUIT_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_RELEASE_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_PROFILE_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_MEMBER_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_GROUP_ADD_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            default:
                logger.warn("Unsupport command id {}", actId);
                break;
        }
    }

    public void doMessage(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) {

        switch (actId) {
            case Connect.ActID.ACT_MSG_GET_REQ_VALUE:
                //userAction.login(header, body, ctx);
                break;
            case Connect.ActID.ACT_MSG_READ_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_MSG_SEARCH_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_MSG_SEND_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            default:
                logger.warn("Unsupport command id {}", actId);
                break;
        }
    }

    public void doCall(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) {

        switch (actId) {
            case Connect.ActID.ACT_CALL_CANCEL_REQ_VALUE:
                //userAction.login(header, body, ctx);
                break;
            case Connect.ActID.ACT_CALL_HUNGUP_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            case Connect.ActID.ACT_CALL_INITIATE_REQ_VALUE:
                //userAction.logOut(header, body, ctx);
                break;
            default:
                logger.warn("Unsupport command id {}", actId);
                break;
        }
    }

    public void doLogin(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) throws Exception {

        userAction.login(header, body, ctx);
    }

    public void doLogout(ChannelHandlerContext ctx, int actId, MsgHeader header, MessageLite body) throws Exception {

        userAction.logOut(header, body, ctx);
    }
}