/**
 * sam@here 2020/2/29
 **/
package com.pbsaas.connect.server.mars.logic;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.core.utils.CommonUtils;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.handler.MessageServerHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientUser {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String userId;
    private String loginName;
    private String nickName;
    private boolean updated;
    private int status;
    private boolean validate;

    private Map<Long, ChannelHandlerContext> connMap = new HashMap<>();
    private List<ChannelHandlerContext> unValidateConnSet = new ArrayList<>();

    public static final AttributeKey<Long> HANDLE_ID = AttributeKey.valueOf("HANDLE_ID");
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("USER_ID");

    //客户端类型
    public static final AttributeKey<Connect.ClientType> CLIENT_TYPE = AttributeKey.valueOf("CLIENT_TYPE");
    public static final AttributeKey<Connect.StateType> STATUS = AttributeKey.valueOf("STATUS");

    public ClientUser() {
        this.validate = false;
        this.userId = "";
        this.updated = false;
        this.status = Connect.StateType.STATE_OFFLINE_VALUE;
    }

    public ClientUser(String userName) {
        this();
        this.loginName = userName;
    }


    /**
     * @param ctx
     */
    public ClientUser(ChannelHandlerContext ctx, String userId, long handleId, Connect.ClientType clientType, Connect.StateType statType) {
        this();
        this.userId = userId;
        this.connMap.put(handleId, ctx);
        ctx.channel().attr(HANDLE_ID).set(handleId);
        ctx.channel().attr(USER_ID).set(userId);
        ctx.channel().attr(CLIENT_TYPE).set(clientType);
        ctx.channel().attr(STATUS).set(statType);
    }

    public ChannelHandlerContext getConn(long handle) {
        return connMap.get(handle);
    }

    public void addConn(long handle, ChannelHandlerContext conn) {
        connMap.put(handle, conn);
    }

    public void delConn(long handle) {
        connMap.remove(handle);
    }

    public ChannelHandlerContext getUnvalidateConn(long handle) {

        for (ChannelHandlerContext clientConn: unValidateConnSet) {
            if (clientConn.channel().attr(HANDLE_ID).get() == handle) {
                return clientConn;
            }
        }
        return null;
    }

    public void addUnvalidateConn(ChannelHandlerContext conn) {
        unValidateConnSet.add(conn);
    }

    public void delUnvalidateConn(ChannelHandlerContext conn) {
        unValidateConnSet.remove(conn);
    }

    public void validateMsgConn(long handle, ChannelHandlerContext conn) {
        this.addConn(handle, conn);
        this.delUnvalidateConn(conn);
    }

    /**
     * 设置为无效连接
     * @param handle
     * @param conn
     * @since  1.0
     */
    public void unValidateMsgConn(long handle, ChannelHandlerContext conn) {
        this.delConn(handle);
        this.addUnvalidateConn(conn);
    }

    public UserConn getUserConn() {
        int count = 0;
        for (ChannelHandlerContext conn: connMap.values()) {
            if (conn.channel().isOpen()) {
                count++;
            }
        }
        return new UserConn(this.userId, count);
    }

    public void broadcast(ProtoMessage<MessageLite> message, ChannelHandlerContext fromCtx) {
        for (ChannelHandlerContext conn: connMap.values()) {
            if (conn != fromCtx) {
                logger.trace("发送消息> {}", conn.channel().remoteAddress());
                conn.writeAndFlush(message);
                // conn > AddToSendList
            }
        }

    }

    public void broadcastWithOutMobile(ProtoMessage<MessageLite> message, ChannelHandlerContext fromCtx) {
        for (ChannelHandlerContext conn: connMap.values()) {
            if (conn != fromCtx && isPc(conn.channel().attr(CLIENT_TYPE).get())) {
                logger.trace("发送消息> {}", conn.channel().remoteAddress());
                conn.writeAndFlush(message);
            }
        }
    }

    private boolean isPc(Connect.ClientType clientType) {

        return  false;
    }

    public void broadcastToMobile(ProtoMessage<MessageLite> message, ChannelHandlerContext fromCtx) {
        for (ChannelHandlerContext conn: connMap.values()) {


            if (conn != fromCtx && isMobile(conn.channel().attr(CLIENT_TYPE).get())) {
                logger.trace("发送消息> {}", conn.channel().remoteAddress());
                conn.writeAndFlush(message);
            }
        }
    }

    private boolean isMobile(Connect.ClientType clientType) {
        //todo
        return  false;
    }

    public void broadcaseMessage(ProtoMessage<MessageLite> message, long messageId, ChannelHandlerContext fromCtx, long fromId) {
        for (ChannelHandlerContext conn: connMap.values()) {
            if (conn != fromCtx) {
                logger.trace("发送消息> {}", conn.channel().remoteAddress());
                conn.writeAndFlush(message);
                // conn AddToSendList
            }
        }
    }

    public void broadcastData(ByteBuf message, int len, ChannelHandlerContext fromCtx) {
        for (ChannelHandlerContext conn: connMap.values()) {
            if (conn != fromCtx) {
                logger.trace("发送消息> {}", conn.channel().remoteAddress());
                conn.writeAndFlush(message);
            }
        }
    }

    public void kickUser(ChannelHandlerContext conn, int reason) {
        long handle = conn.channel().attr(HANDLE_ID).get();
        MsgHeader header = new MsgHeader();

        /**!!!
        IMLogin.IMKickUser message = IMLogin.IMKickUser.newBuilder().setUserId(this.userId)
                .setKickReason(IMBaseDefine.KickReasonType.forNumber(reason)).build();


        header.setCmdId((short)ServiceID.SID_LOGIN_VALUE);
        header.setActId((short)LoginCmdID.CID_LOGIN_KICK_USER_VALUE);
        ****/
        conn.writeAndFlush(new ProtoMessage<>(header, /**message***/null));
        conn.close();
        // conn ->SetKickOff();
        // conn ->Close();
    }

    /**
     * 只支持一个WINDOWS/MAC客户端登陆,或者一个ios/android登录
     * @param clientType
     * @param reason
     * @param fromCtx
     * @since  1.0
     */
    public void kickSameClientType(int clientType, int reason, ChannelHandlerContext fromCtx) {
        for (ChannelHandlerContext conn: connMap.values()) {
            int connClientType = conn.channel().attr(CLIENT_TYPE).get().getNumber();
            // 16进制位移计算
            if ((connClientType ^ clientType) >> 4 == 0 && conn != fromCtx) {
                this.kickUser(conn, reason);
            }
        }
    }
    /**
     * 判断是PC/Mobile登录
     *
     * @return 客户端登录类型
     * @since  1.0
     */
    public int getClientFlag() {

        int clientFlag = 0;//!!SysConstant.CLIENT_TYPE_FLAG_NONE;
/**
        for (ChannelHandlerContext conn: connMap.values()) {
            int connClientType = conn.attr(CLIENT_TYPE).get().getNumber();
            if (CommonUtils.isPc(connClientType)) {
                clientFlag |= SysConstant.CLIENT_TYPE_FLAG_PC;
            } else if (CommonUtils.isMobile(connClientType)) {
                clientFlag |= SysConstant.CLIENT_TYPE_FLAG_MOBILE;
            } else {
                // 不识别
            }
        }
        ***/
        return clientFlag;
    }

    public boolean isConnEmpty() {
        return connMap.isEmpty();
    }

    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }
    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    /**
     * @return the updated
     */
    public boolean isUpdated() {
        return updated;
    }
    /**
     * @param updated the updated to set
     */
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * @return the validate
     */
    public boolean isValidate() {
        return validate;
    }
    /**
     * @param validate the validate to set
     */
    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    /**
     * @return the connMap
     */
    public Map<Long, ChannelHandlerContext> getConnMap() {
        return connMap;
    }

    /**
     * @return the unvalidateConnSet
     */
    public List<ChannelHandlerContext> getUnValidateConnSet() {
        return unValidateConnSet;
    }

    public static class UserConn {
        private String userId;
        private int connCount;

        public UserConn(String userId, int count) {
            this.userId = userId;
            this.connCount = count;
        }
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getConnCount() {
            return connCount;
        }

        public void setConnCount(int connCount) {
            this.connCount = connCount;
        }
    }
}
