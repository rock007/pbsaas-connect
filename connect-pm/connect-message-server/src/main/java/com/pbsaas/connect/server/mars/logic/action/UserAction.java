/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.logic.action;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import io.netty.channel.ChannelHandlerContext;

public interface UserAction {

    //登录
    public void login(MsgHeader header, MessageLite msg, ChannelHandlerContext ctx) throws Exception;

    //登出
    public void logOut(MsgHeader header, MessageLite body, ChannelHandlerContext ctx);
}
