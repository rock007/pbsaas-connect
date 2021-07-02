/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.logic.action.impl;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.core.utils.CommonUtils;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.proto.user.LoginReq;
import com.pbsaas.connect.server.mars.logic.ClientUser;
import com.pbsaas.connect.server.mars.logic.ClientUserManager;
import com.pbsaas.connect.server.mars.logic.action.ActionBase;
import com.pbsaas.connect.server.mars.logic.action.UserAction;
import com.pbsaas.connect.server.mars.model.TokenInfo;
import io.jsonwebtoken.*;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserActionImpl extends ActionBase implements UserAction {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static   AtomicLong mAtoLong = new AtomicLong();

    @Override
    public void login(MsgHeader header, MessageLite data, ChannelHandlerContext ctx) throws Exception {

        ProtoMessage respMsg=new ProtoMessage();
        int serverTime = CommonUtils.currentTimeSeconds();

        LoginReq loginReq = (LoginReq)data;

        try {

            //checkToken
            String token= loginReq.getToken();

            //throw exception
            Claims claims = Jwts.parser().setSigningKey("test_key".getBytes()).parseClaimsJws(token)
                    .getBody();

            TokenInfo tokenInfo = new TokenInfo(claims);

            // 查询同一用户的其他Client
            String userId = tokenInfo.getUserId();
            ClientUser clientUser = ClientUserManager.getUserById(userId);
            Long handleId = mAtoLong.getAndIncrement();

            if (clientUser == null) {
                logger.debug("登录成功:{}", userId);
                clientUser = new ClientUser(ctx, userId, handleId, Connect.ClientType.CLIENT_TYPE_WEB, Connect.StateType.STATE_ONLINE);
                clientUser.setLoginName(tokenInfo.getUserId());
                clientUser.setNickName(tokenInfo.getUserName());
                ClientUserManager.addUserById(userId, clientUser);
            } else {
                logger.debug("登录成功，设置参数:{}", userId);

                // 踢掉同时在线的同类型的客户端

                // 追加新的连接
                clientUser.addConn(handleId, ctx);
                ctx.channel().attr(ClientUser.HANDLE_ID).set(handleId);
                ctx.channel().attr(ClientUser.USER_ID).set(userId);
                ctx.channel().attr(ClientUser.CLIENT_TYPE).set(Connect.ClientType.values()[tokenInfo.getClientType()]);
                ctx.channel().attr(ClientUser.STATUS).set(Connect.StateType.STATE_ONLINE);
            }
            // 更新用户状态
            clientUser.setValidate(true);

            Connect.RespBody respBody= Connect.RespBody.newBuilder()
                    .setResult(1)
                    .setMsg("登录成功")
                    .build();
            this.response(ctx,Connect.CmdID.CMD_LOGIN_VALUE,Connect.ActID.ACT_LOGIN_RSP_VALUE,respBody,null);

        }catch (SignatureException e) {
            // 验证错误
            logger.warn("jwt token parse error: {}", e.getMessage());

            Connect.RespBody respBody= Connect.RespBody.newBuilder()
                    .setResult(-1)
                    .setMsg("token验证失败")
                    .build();
            this.response(ctx,Connect.CmdID.CMD_LOGIN_VALUE,Connect.ActID.ACT_LOGIN_RSP_VALUE,respBody,null);

        } catch (ExpiredJwtException e) {
            // token 超时
            logger.warn("jwt token is expired:{}",e.getMessage());

            Connect.RespBody respBody= Connect.RespBody.newBuilder()
                    .setResult(-1)
                    .setMsg("token已过期，请重新获取")
                    .build();
            this.response(ctx,Connect.CmdID.CMD_LOGIN_VALUE,Connect.ActID.ACT_LOGIN_RSP_VALUE,respBody,null);

        } catch (MalformedJwtException e) {
            // token Malformed
            logger.warn("jwt token is malformed,err:"+e.getMessage());

            Connect.RespBody respBody= Connect.RespBody.newBuilder()
                    .setResult(-1)
                    .setMsg("token格式不正确")
                    .build();
            this.response(ctx,Connect.CmdID.CMD_LOGIN_VALUE,Connect.ActID.ACT_LOGIN_RSP_VALUE,respBody,null);

        } catch (Exception e) {

            logger.error("服务器端异常", e);

            Connect.RespBody respBody= Connect.RespBody.newBuilder()
                    .setResult(-1)
                    .setMsg("服务器端异常"+ e.getMessage())
                    .build();
            this.response(ctx,Connect.CmdID.CMD_LOGIN_VALUE,Connect.ActID.ACT_LOGIN_RSP_VALUE,respBody,null);
        }
    }

    @Override
    public void logOut(MsgHeader header, MessageLite body, ChannelHandlerContext ctx) {

        /**
        LogoutReq logoutReq = (LogoutReq) body;
        long userId = super.getUserId(ctx);

        IMHeader resHeader = null;
        IMLogoutRsp logoutRsp = null;
        try {

            UserToken userToken = new UserToken();
            userToken.setUserId(userId);
            userToken.setUserToken("");

            //先发给给数据库
            //BaseModel<Long> deviceTokenRes =  loginService.setdeviceToken(userToken);
            loginService.setDeviceToken(userToken);

            resHeader = header.clone();
            resHeader.setCommandId((short)LoginCmdID.CID_LOGIN_REQ_LOGINOUT_VALUE);

            logoutRsp = IMLogoutRsp.newBuilder()
                    .setResultCode(0)
                    .build();
            ctx.writeAndFlush(new IMProtoMessage<>(resHeader, logoutRsp));
        } catch (Exception e) {
            logger.error("服务器端异常", e);
            ctx.writeAndFlush(new IMProtoMessage<>(resHeader, logoutRsp));

        } finally {
            ctx.close().addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    ClientUser clientUser = ClientUserManager.getUserById(userId);
                    if (clientUser == null) {

                        logger.debug("用户没有登录过:{}", userId);
                    } else {
                        logger.debug("用户已登录过，设置参数:{}", userId);

                        for (ChannelHandlerContext chc :clientUser.getUnValidateConnSet() ){
                            if (chc == ctx){

                                clientUser.getUnValidateConnSet().remove(ctx);
                            }
                        }

                        for ( ChannelHandlerContext chc :  clientUser.getConnMap().values()){
                            if ( chc == ctx){
                                long handle = chc.attr(ClientUser.HANDLE_ID).get();
                                clientUser.getConnMap().remove(handle);
                            }
                        }

                        //...
                        // 还需要其他处理，需要进一步添加
                        //...

                        ctx.attr(ClientUser.STATUS).set(UserStatType.USER_STATUS_OFFLINE);
                    }


                }
            });
        }
        ****/
    }
}
