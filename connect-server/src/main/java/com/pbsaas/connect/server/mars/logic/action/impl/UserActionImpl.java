/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.logic.action.impl;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.utils.CommonUtils;
import com.pbsaas.connect.proto.LoginReqOuterClass;

import com.pbsaas.connect.server.mars.logic.action.ActionBase;
import com.pbsaas.connect.server.mars.logic.action.UserAction;
import com.pbsaas.connect.server.mars.model.MsgHeader;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserActionImpl extends ActionBase implements UserAction {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void login(MsgHeader header, MessageLite msg, ChannelHandlerContext ctx) throws Exception {

        int serverTime = CommonUtils.currentTimeSeconds();
        LoginReqOuterClass.LoginReq req = (LoginReqOuterClass.LoginReq) msg;
        String userName = req.getAccount();
        String password = req.getPassword();

        // 已登录的，踢掉
        LoginReq loginReq = new LoginReq();
        loginReq.setName(userName);
        loginReq.setPassword(password);

        try {

            BaseModel<UserEntity> userRes = loginService.login(loginReq);

            if (userRes.getCode() != LoginCmdResult.SUCCESS.getCode()) {

                IMLoginRes res = IMLoginRes.newBuilder().setServerTime(serverTime)
                        .setOnlineStatus(UserStatType.USER_STATUS_ONLINE)
                        .setResultCode(ResultType.REFUSE_REASON_DB_VALIDATE_FAILED).setResultString(userRes.getMsg()).buildPartial();
                IMHeader resHeader = header.clone();
                resHeader.setCommandId((short)LoginCmdID.CID_LOGIN_RES_USERLOGIN_VALUE);

                ctx.writeAndFlush(new IMProtoMessage<>(resHeader, res));
            } else {

                // 查询同一用户的其他Client
                Long userId = userRes.getData().getId();
                ClientUser clientUser = ClientUserManager.getUserById(userId);
                Long handleId = hazelCastInstance.getAtomicLong("message-server#HANDLE_ID").getAndIncrement();
                if (clientUser == null) {
                    logger.debug("登录成功:{}", userId);
                    clientUser = new ClientUser(ctx, userId, handleId, req.getClientType(), UserStatType.USER_STATUS_ONLINE);
                    clientUser.setLoginName(userRes.getData().getRealName());
                    clientUser.setNickName(userRes.getData().getMainName());
                    ClientUserManager.addUserById(userId, clientUser);
                } else {
                    logger.debug("登录成功，设置参数:{}", userId);

                    // 踢掉同时在线的同类型的客户端
                    clientUser.kickSameClientType(req.getClientType().getNumber(),
                            IMBaseDefine.KickReasonType.KICK_REASON_DUPLICATE_USER.getNumber(),
                            ctx);

                    // 追加新的连接
                    clientUser.addConn(handleId, ctx);
                    ctx.attr(ClientUser.HANDLE_ID).set(handleId);
                    ctx.attr(ClientUser.USER_ID).set(userId);
                    ctx.attr(ClientUser.CLIENT_TYPE).set(req.getClientType());
                    ctx.attr(ClientUser.STATUS).set(UserStatType.USER_STATUS_ONLINE);
                }
                // 更新用户状态
                // pMsgConn->SetUserId(user_id);
                // pMsgConn->SetOpen();
                // pMsgConn->SendUserStatusUpdate(IM::BaseDefine::USER_STATUS_ONLINE);
                // pUser->ValidateMsgConn(pMsgConn->GetHandle(), pMsgConn);
                // KickOutSameClientType
                clientUser.setValidate(true);

                // 广播
                // routerHandler.sendUserStatusUpdate(ctx, IMBaseDefine.UserStatType.USER_STATUS_ONLINE);
                messageServerCluster.userStatusUpdate(userId, ctx, IMBaseDefine.UserStatType.USER_STATUS_ONLINE);

                // 广播
                // routerHandler.kickOutSameClientType(ctx, IMBaseDefine.KickReasonType.KICK_REASON_DUPLICATE_USER);
                // OtherCmdID.CID_OTHER_SERVER_KICK_USER_VALUE
                IMHeader kkHeader = new DefaultIMHeader(ServiceID.SID_OTHER_VALUE, OtherCmdID.CID_OTHER_SERVER_KICK_USER_VALUE);
                IMServerKickUser kickUser = IMServerKickUser.newBuilder().setClientType(req.getClientType())
                        .setReason(IMBaseDefine.KickReasonType.KICK_REASON_DUPLICATE_USER_VALUE).setUserId(userId).build();

                messageServerCluster.send(kkHeader, kickUser);

                // 后移，防止登录后判断是否在线（PC）失败
                IMBaseDefine.UserInfo userInfo = JavaBean2ProtoBuf.getUserInfo(userRes.getData());

                IMLoginRes res = IMLoginRes.newBuilder().setOnlineStatus(UserStatType.USER_STATUS_ONLINE)
                        .setServerTime(serverTime).setUserInfo(userInfo).setResultCode(ResultType.REFUSE_REASON_NONE)
                        .build();

                IMHeader resHeader = header.clone();
                resHeader.setCommandId((short) LoginCmdID.CID_LOGIN_RES_USERLOGIN_VALUE);
                ctx.writeAndFlush(new IMProtoMessage<>(resHeader, res));
            }

        } catch (Exception e) {

            logger.error("服务器端异常", e);
            IMLoginRes res;

            res = IMLoginRes.newBuilder().setServerTime(serverTime)
                    .setOnlineStatus(UserStatType.USER_STATUS_ONLINE)
                    .setResultCode(ResultType.REFUSE_REASON_DB_VALIDATE_FAILED).setResultString("服务器端异常").buildPartial();
            IMHeader resHeader = header.clone();
            resHeader.setCommandId((short)LoginCmdID.CID_LOGIN_RES_USERLOGIN_VALUE);

            ctx.writeAndFlush(new IMProtoMessage<>(resHeader, res));
        }
    }

    @Override
    public void logOut(MsgHeader header, MessageLite body, ChannelHandlerContext ctx) {

    }
}
