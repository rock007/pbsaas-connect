package com.pbsaas.connect.server.mars.cluster.task;

import java.io.Serializable;
import java.util.concurrent.Callable;

import com.google.protobuf.MessageLite;

import com.pbsaas.connect.server.mars.cluster.MyClusterMessage;
import io.netty.channel.ChannelHandlerContext;

public class MessageToUserTask implements Callable<Long>, Serializable {


    private final Long userId;
    private final Long netId;

    private final MyClusterMessage clusterMessage;
    
    public MessageToUserTask(Long userId, Long netId, MyClusterMessage clusterMessage) {
        this.userId = userId;
        this.netId = netId;
        this.clusterMessage = clusterMessage;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Long call() throws Exception {
        // 发送指定到用户连接的消息
        if (netId > 0) {
            ChannelHandlerContext ctx = ClientUserManager.getConnByHandle(userId, netId);
            if (ctx != null) {
                ctx.writeAndFlush(new IMProtoMessage<MessageLite>(clusterMessage.getHeader(),
                        clusterMessage.getMessage()));
            }
        } else {
            // 所有端
            ClientUser clientUser = ClientUserManager.getUserById(userId);
            clientUser.broadcast(new IMProtoMessage<MessageLite>(clusterMessage.getHeader(),
                        clusterMessage.getMessage()), null);
        }
        return null;
    }

}
