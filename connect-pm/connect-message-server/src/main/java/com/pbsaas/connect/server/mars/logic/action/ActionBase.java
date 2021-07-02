/**
 * sam@here 2020/3/2
 **/
package com.pbsaas.connect.server.mars.logic.action;

import com.pbsaas.connect.server.mars.logic.ClientUser;
import io.netty.channel.ChannelHandlerContext;

public abstract class ActionBase {

    protected long getUserId(ChannelHandlerContext ctx) {
        Long userId = ctx.channel().attr(ClientUser.USER_ID).get();
        if (userId != null) {
            return userId;
        }
        return 0;
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
}
