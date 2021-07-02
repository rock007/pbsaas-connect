/**
 * sam@here 2019/9/30
 **/
package com.pbsaas.connect.server.mars.logic;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.ProtoMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理连接
 */
public class ClientUserManager {

    private static Map<String, ClientUser> userMap = new HashMap<>();

    public static ClientUser getUserById(String userId) {
        return userMap.get(userId);
    }

    public static ChannelHandlerContext getConnByHandle(String userId, long handle) {
        ClientUser client = userMap.get(userId);
        if (client == null) {
            return null;
        }
        return client.getConn(handle);
    }

    public static boolean addUserById(String userId, ClientUser clientUser) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, clientUser);
            return true;
        }
        return false;
    }
    public static void removeUserById(String userId) {
        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
        }
    }

    public static void removeUser(ClientUser clientUser) {
        removeUserById(clientUser.getUserId());
//        removeUserByName(clientUser.getLoginName());
    }

    public static void removeAll() {
        userMap.clear();
//        userNameMap.clear();
    }

    public static List<String> getOnlineUserList() {
        List<String> userList = new ArrayList<>();
        for (ClientUser user : userMap.values()) {
            if (user.isValidate()) {
                userList.add(user.getUserId());
            }
        }
        return userList;
    }
    /**!!!
     * 获取用户连接状态

    public static List<IMBaseDefine.ServerUserStat> getOnlineUser() {
        List<IMBaseDefine.ServerUserStat> userStats = new ArrayList<>();
        for (ClientUser user : userMap.values()) {
            if (user.isValidate()) {
                Map<Long, ChannelHandlerContext> connMap = user.getConnMap();
                for (ChannelHandlerContext cxt: connMap.values()) {

                    if (cxt.channel().isOpen()) {
                        IMBaseDefine.ServerUserStat.Builder userStatBuilder = IMBaseDefine.ServerUserStat.newBuilder();
                        userStatBuilder.setUserId(user.getUserId());
                        userStatBuilder.setClientType(cxt.attr(ClientUser.CLIENT_TYPE).get());
                        userStatBuilder.setStatus(cxt.attr(ClientUser.STATUS).get());

                        userStats.add(userStatBuilder.build());
                    }
                }
            }
        }
        return userStats;
    }
     */
    public static ClientUserConn getUserConn() {
        ClientUserConn clientUserConn = new ClientUserConn();
        for (ClientUser user : userMap.values()) {
            if (user.isValidate()) {
                ClientUser.UserConn userConn = user.getUserConn();
                clientUserConn.addCount(userConn.getConnCount());
                clientUserConn.getUserConnList().add(userConn);
            }
        }
        return clientUserConn;
    }

    /**
     * 对客户端进行广播
     */
    public static void broadCast(ProtoMessage<MessageLite> message, int clientFlag) {
        for (ClientUser user : userMap.values()) {
            if (user.isValidate()) {
                /***!!!
                if (clientFlag == SysConstant.CLIENT_TYPE_FLAG_PC) {
                    user.broadcastWithOutMobile(message, null);
                } else if ((clientFlag == SysConstant.CLIENT_TYPE_FLAG_MOBILE)) {
                    user.broadcastToMobile(message, null);
                } else if ((clientFlag == SysConstant.CLIENT_TYPE_FLAG_BOTH)) {
                    user.broadcast(message, null);
                } else {
                    // 参数不正，不处理
                }
                **/
            }
        }
    }

    public static class ClientUserConn {
        private int totalCount = 0;
        private List<ClientUser.UserConn> userConnList = new ArrayList<>();
        /**
         * @return the totalCount
         */
        public int getTotalCount() {
            return totalCount;
        }
        /**
         * @param connCount
         * @since  1.0
         */
        public void addCount(int connCount) {
            this.totalCount += connCount;
        }
        /**
         * @param totalCount the totalCount to set
         */
        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
        /**
         * @return the userConnList
         */
        public List<ClientUser.UserConn> getUserConnList() {
            return userConnList;
        }
        /**
         * @param userConnList the userConnList to set
         */
        public void setUserConnList(List<ClientUser.UserConn> userConnList) {
            this.userConnList = userConnList;
        }
    }
}
