/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.logic.action;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.logic.ClientUser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public abstract class ActionBase {

    protected String getUserId(ChannelHandlerContext ctx) {
        String userId = ctx.channel().attr(ClientUser.USER_ID).get();
        if (userId != null) {
            return userId;
        }
        return "";
    }

    /**
     * 取连接ID
     */
    protected long getHandleId(ChannelHandlerContext ctx) {
        Long handleId = ctx.channel().attr(ClientUser.HANDLE_ID).get();
        if (handleId != null) {
            return handleId;
        }
        return 0;
    }

    protected   void response(ChannelHandlerContext ctx, int cmdId, int actId, MessageLite data, Runnable success_fn) throws Exception {

        MsgHeader header1=new MsgHeader();
        header1.setCmdId(Connect.CmdID.CMD_LOGIN_VALUE);
        header1.setActId(Connect.ActID.ACT_LOGIN_RSP_VALUE);
        header1.setSeq(100);

        header1.setBody(data.toByteArray());

        response(ctx,new ProtoMessage(header1,data),success_fn);
    }

    /***
     * 客户端回应
     */
    protected   void response(ChannelHandlerContext ctx, ProtoMessage message , Runnable success_fn) throws Exception {

        /***
         ProtoMessage messageHolder = new ProtoMessage();

         MsgHeader header=new MsgHeader();

         messageHolder.setCmdId(cmdId);
         messageHolder.setSeq(0);
         messageHolder.setBody(bytes);
         messageHolder.setChannel(channel);
         ***/

        //logger.debug("resp msg :"+messageHolder.toString());
        //byte[] respBuf = message.getHeader() messageHolder.encode();

        ChannelFuture recChannel=ctx.writeAndFlush(message);
        recChannel.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    //关闭连接
                    //logger.info("回复成功 ");
                    if (success_fn != null)
                        success_fn.run();
                } else {
                    //SessionPool.remove(channel);
                    //logger.info("回复失败，删除连接,当前连接数：");
                }
            }
        });

    }
}
