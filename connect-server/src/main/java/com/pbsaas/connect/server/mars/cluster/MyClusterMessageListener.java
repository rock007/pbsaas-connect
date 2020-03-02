package com.pbsaas.connect.server.mars.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pbsaas.connect.proto.Connect;
import com.pbsaas.connect.server.mars.model.MsgHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.hazelcast.core.Member;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;


@Component
public class MyClusterMessageListener implements MessageListener<MyClusterMessage> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //!!@Autowired
    //private GroupService groupService;

    //!!@Autowired
    //private IphonePushService iphonePushService;

    @Override
    public void onMessage(Message<MyClusterMessage> message) {

        MyClusterMessage clusterMessage = message.getMessageObject();
        Member member = message.getPublishingMember();
        
        logger.debug("Length:{}, ServiceID:{}, CommandID:{}", clusterMessage.getLength(),
                clusterMessage.getServiceId(), clusterMessage.getCmdId());

        // 根据不同的消息，做不同的处理
        // 处理请求分发
        switch (clusterMessage.getCmdId()) {
            case  Connect.ActID.ACT_ID_SEND_MSG_VALUE:
                //发送消息
                break;
            /***
            case IMBaseDefine.ServiceID.SID_BUDDY_LIST_VALUE:
                this.doBuddyList(clusterMessage.getCommandId(), clusterMessage, member);
                break;
            case IMBaseDefine.ServiceID.SID_MSG_VALUE:
                if (!member.localMember()) {
                    // 不处理当前node的消息
                    this.doMessage(clusterMessage.getCommandId(), clusterMessage);
                }
                break;
            case IMBaseDefine.ServiceID.SID_OTHER_VALUE:
                this.doOther(clusterMessage.getCommandId(), clusterMessage, member);
                break;
            case IMBaseDefine.ServiceID.SID_SWITCH_SERVICE_VALUE:
                if (!member.localMember()) {
                    this.doSwitch(clusterMessage.getCommandId(), clusterMessage);
                }
                break;
            case IMBaseDefine.ServiceID.SID_FILE_VALUE:
                this.doFile(clusterMessage.getCommandId(), clusterMessage);
                break;
            case IMBaseDefine.ServiceID.SID_GROUP_VALUE:
                if (!member.localMember()) {
                    this.doGroup(clusterMessage.getCommandId(), clusterMessage);
                }
                break;
            case IMBaseDefine.ServiceID.SID_AVCALL_VALUE:
                this.doWebrtc(clusterMessage.getCommandId(), clusterMessage, member);
                break;
            ***/
            default:
                logger.warn("暂不支持的服务ID{}", clusterMessage.getServiceId());
                break;
        }
    }

    private void doGroup(short commandId, MyClusterMessage clusterMessage) {
        logger.debug("MyClusterMessageListener#doSwitch");
        /****
        MsgHeader header = clusterMessage.getHeader();
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case GroupCmdID.CID_GROUP_CHANGE_MEMBER_NOTIFY_VALUE:// todebug
                    groupChangeMemberNotify(header, body);
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        ***/
    }

    private void doFile(short commandId, MyClusterMessage clusterMessage) {
        logger.debug("MyClusterMessageListener#doSwitch");
        MsgHeader header = clusterMessage.getHeader();
        /***
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case FileCmdID.CID_FILE_NOTIFY_VALUE:// todebug
                    fileNotify(header, body);
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        ***/
    }

    private void doSwitch(short commandId, MyClusterMessage clusterMessage) {
        logger.debug("MyClusterMessageListener#doSwitch");
        /***
        MsgHeader header = clusterMessage.getHeader();
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case SwitchServiceCmdID.CID_SWITCH_P2P_CMD_VALUE:// todebug
                    switchP2p(header, body);
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        ****/

    }

    private void doOther(short commandId, MyClusterMessage clusterMessage, Member member) {
        logger.debug("MyClusterMessageListener#doOther");
        MsgHeader header = clusterMessage.getHeader();
        /***
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case OtherCmdID.CID_OTHER_SERVER_KICK_USER_VALUE:
                    if (!member.localMember()) {
                        handleKickUser(body);
                    }
                    break;
                case OtherCmdID.CID_OTHER_LOGIN_STATUS_NOTIFY_VALUE:
                    if (!member.localMember()) {
                        handlePCLoginStatusNotify(header, body);
                    }
                    break;
                case OtherCmdID.CID_OTHER_HEARTBEAT_VALUE:// 无需实现
                    break;
                case OtherCmdID.CID_OTHER_ROLE_SET_VALUE:// 目前不需要实现
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        ****/
    }

    private void doMessage(short commandId, MyClusterMessage clusterMessage) {
        logger.debug("MyClusterMessageListener#doMessage");
        MsgHeader header = clusterMessage.getHeader();
       /***
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case MessageCmdID.CID_MSG_READ_NOTIFY_VALUE:
                    handleMsgReadNotify(header, body);
                    break;
                case MessageCmdID.CID_MSG_DATA_VALUE:
                    handleMsgData(header, body);
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        ****/
    }

    /**
     * @param commandId
     * @param clusterMessage
     * @since 1.0
     */
    private void doBuddyList(short commandId, MyClusterMessage clusterMessage, Member member) {
        logger.debug("doBuddyList");
        MsgHeader header = clusterMessage.getHeader();
        /***
        try {
            MessageLite body = clusterMessage.getMessage();
            switch (commandId) {
                case BuddyListCmdID.CID_BUDDY_LIST_STATUS_NOTIFY_VALUE:
                    // send friend online message to client
                    handleStatusNotify(header, body);
                    break;
//                case BuddyListCmdID.CID_BUDDY_LIST_USERS_STATUS_RESPONSE_VALUE:
//                    // send back to user
//                    handleUsersStatus(header, body);
//                    break;
                case BuddyListCmdID.CID_BUDDY_LIST_REMOVE_SESSION_NOTIFY_VALUE:
                    if (!member.localMember()) {
                        removeSessionNotify(header, body);
                    }
                    break;
                case BuddyListCmdID.CID_BUDDY_LIST_AVATAR_CHANGED_NOTIFY_VALUE:
                    handleAvatarChangedNotify(header, body);
                    break;
                case BuddyListCmdID.CID_BUDDY_LIST_SIGN_INFO_CHANGED_NOTIFY_VALUE:
                    signInfoChangedNotify(header, body);
                    break;
                case BuddyListCmdID.CID_BUDDY_LIST_USER_INFO_CHANGED_NOTIFY_VALUE:
                    userInfoChangedNotify(header, body);
                    break;

                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
         ****/
    }

    /**
     * 处理webrtc
     * @param commandId 命令ID
     * @param clusterMessage 消息
     * @param member hazelcast成员
     * @since  1.3
     */
    private void doWebrtc(short commandId, MyClusterMessage clusterMessage, Member member) {
        // FIXME webrtc
        logger.debug("doWebrtc");
        MsgHeader header = clusterMessage.getHeader();
        try {
            MessageLite body = clusterMessage.getMessage();
            /***
            switch (commandId) {
                case AVCallCmdId.CID_AVCALL_CANCEL_REQ_VALUE:
                    if (!member.localMember()) {
                        handleInitateCallReq(header, body);
                    }
                    break;
                case AVCallCmdId.CID_AVCALL_CANCEL_RES_VALUE:
                    handleCallCancelReq(header, body);
                    break;
                case AVCallCmdId.CID_AVCALL_HUNGUP_REQ_VALUE:
                    
                    break;
                default:
                    logger.warn("Unsupport command id {}", commandId);
                    break;
            }
            ****/
        } catch (IOException e) {
            logger.error("decode failed.", e);
        }
        
    }

    private void handleInitateCallReq(MsgHeader header, MessageLite body) {
        // TODO Auto-generated method stub
        
    }
    private void handleCallCancelReq(MsgHeader header, MessageLite body) {
        // 如果handleId(attachData)存在，则直接取消所对应的连接
        // 否则通知所有对应的客户端
    }

    private void switchP2p(MsgHeader header, MessageLite body) {

        /***
        IMSwitchService.IMP2PCmdMsg switchP2p = (IMSwitchService.IMP2PCmdMsg) body;

        ClientUser fromUser = ClientUserManager.getUserById(switchP2p.getFromUserId());
        ClientUser toUser = ClientUserManager.getUserById(switchP2p.getToUserId());

        // 这样处理是否合理，需要检查？
        if (fromUser != null) {
            fromUser.broadcast(new IMProtoMessage<MessageLite>(header, body), null);
        }

        if (toUser != null) {
            toUser.broadcast(new IMProtoMessage<MessageLite>(header, body), null);
        }
        ****/
    }

    private void fileNotify(MsgHeader header, MessageLite body) {
        /****
        IMFile.IMFileNotify fileNotify = (IMFile.IMFileNotify) body;

        ClientUser clientUser = ClientUserManager.getUserById(fileNotify.getToUserId());

        if (clientUser != null) {
            clientUser.broadcast(new IMProtoMessage<MessageLite>(header, body), null);
        }
        ****/
    }

    /**
     * 群消息发送（群组不存在时创建）
     */
    private void handleGroupMessageBroadcast(MsgHeader header/**!!!, IMMsgData msgdata***/) {

        // 服务器没有群的信息，向DB服务器请求群信息，并带上消息作为附件，返回时在发送该消息给其他群成员
        // 查询群员，然后推送消息
        /***
        List<Long> groupIdList = new ArrayList<>();
        groupIdList.add(msgdata.getToSessionId());
        BaseModel<List<GroupEntity>> groupListRes = groupService.groupInfoList(groupIdList);
        if (groupListRes.getCode() == GroupCmdResult.SUCCESS.getCode()) {
            if (groupListRes.getData() != null && !groupListRes.getData().isEmpty()) {
                List<Long> memberList = groupListRes.getData().get(0).getUserList();

                if (memberList.contains(msgdata.getFromUserId())) {
                    // 用户在群中
                    IMProtoMessage<MessageLite> message = new IMProtoMessage<>(header, msgdata);
                    for (long userId : memberList) {
                        ClientUser clientUser = ClientUserManager.getUserById(userId);
                        if (clientUser == null) {
                            continue;
                        }

                        clientUser.broadcast(message, null);
                    }
                }
            }
        }
        ***/

    }

    /**
     * 发送当前踢人消息
     */
    private void handleKickUser(MessageLite body) {
        /****
        // 转换body中的数据,判断是否是真正的kickUser消息,如果是,则进行下面的操作,不是抛出异常
        IMServerKickUser kickUser = (IMServerKickUser) body;

        long userId = kickUser.getUserId();
        int clientType = kickUser.getClientType().getNumber();
        int reason = kickUser.getReason();
        logger.debug("HandleKickUser, userId={}, clientType={}, reason={}", userId, clientType,
                reason);

        ClientUser clientUser = ClientUserManager.getUserById(userId);
        if (clientUser != null) {
            // 踢掉用户,根据ClientType进行判断
            clientUser.kickSameClientType(clientType, reason, null);
        }
        ****/
    }


    /**
     * Message数据消息
     */
    private void handleMsgData(MsgHeader header, MessageLite body) {
        /***
        IMMsgData msg = (IMMsgData) body;

        if (CommonUtils.isGroup(msg.getMsgType())) {
            // 团队消息
            handleGroupMessageBroadcast(header, msg);
            return;
        }

        long fromUserId = msg.getFromUserId();
        long toUserId = msg.getToSessionId();

        IMProtoMessage<MessageLite> message = new IMProtoMessage<>(header, msg);

        ClientUser fromClientUser = ClientUserManager.getUserById(fromUserId);
        ClientUser toClientUser = ClientUserManager.getUserById(toUserId);
        if (fromClientUser != null) {
            // 应该不会再把消息发送至最初的发出设备
            fromClientUser.broadcast(message, null);
        }
        if (toClientUser != null) {
            // 应该不会再把消息发送至最初的发出设备
            toClientUser.broadcast(message, null);
        }
        ****/
    }

    /**
     * Message已读消息
     */
    private void handleMsgReadNotify(MsgHeader header, MessageLite body) {

        /****
        IMMsgDataReadNotify msg = (IMMsgDataReadNotify) body;

        long reqId = msg.getUserId();

        ClientUser pUser = ClientUserManager.getUserById(reqId);
        if (pUser != null) {

            pUser.broadcast(new IMProtoMessage<MessageLite>(header, msg), null);
        }
        ****/

    }

    /**
     * PC登陆状态消息
     */
    private void handlePCLoginStatusNotify(MsgHeader header, MessageLite body) {
        /***
        IMServerPCLoginStatusNotify msg = (IMServerPCLoginStatusNotify) body;

        long userId = msg.getUserId();
        int loginStatus = msg.getLoginStatus();
        logger.debug("HandlePCLoginStatusNotify, user_id={}, login_status={}", userId, loginStatus);

        ClientUser pUser = ClientUserManager.getUserById(userId);
        if (pUser != null) {
            pUser.setStatus(loginStatus);
            IMPCLoginStatusNotify.Builder loginStatusBuilder = IMPCLoginStatusNotify.newBuilder();
            loginStatusBuilder.setUserId(userId);

            if (1 == loginStatus) {
                loginStatusBuilder.setLoginStat(UserStatType.USER_STATUS_ONLINE);

            } else {
                loginStatusBuilder.setLoginStat(UserStatType.USER_STATUS_OFFLINE);

            }
            IMPCLoginStatusNotify msg2 = loginStatusBuilder.build();

            header.setServiceId((short) IMBaseDefine.ServiceID.SID_BUDDY_LIST_VALUE);
            header.setCommandId(
                    (short) IMBaseDefine.BuddyListCmdID.CID_BUDDY_LIST_PC_LOGIN_STATUS_NOTIFY_VALUE);
            pUser.broadcastToMobile(new IMProtoMessage<MessageLite>(header, msg2), null);

        }
        ****/
    }

    private void handleStatusNotify(MsgHeader header, MessageLite body) {
        /**
        ClientUserManager.broadCast(new IMProtoMessage<>(header, body),
                SysConstant.CLIENT_TYPE_FLAG_PC);
        ***/
    }

    private void handleAvatarChangedNotify(MsgHeader header, MessageLite body) {
        //ClientUserManager.broadCast(new IMProtoMessage<>(header, body), SysConstant.CLIENT_TYPE_FLAG_BOTH);

    }

    private void signInfoChangedNotify(MsgHeader header, MessageLite body) {
        /**
        ClientUserManager.broadCast(new IMProtoMessage<>(header, body),
                SysConstant.CLIENT_TYPE_FLAG_BOTH);
        ***/
    }
    
    /**
     * 用户信息修改通知
     */
    private void userInfoChangedNotify(MsgHeader header, MessageLite body) {
        /**??
        ClientUserManager.broadCast(new IMProtoMessage<>(header, body),
                SysConstant.CLIENT_TYPE_FLAG_BOTH);
        ***/
    }

    private void removeSessionNotify(MsgHeader header, MessageLite body) {
        /**
        IMBuddy.IMRemoveSessionNotify removeSessionNotify = (IMBuddy.IMRemoveSessionNotify) body;
        ClientUser clientUser = ClientUserManager.getUserById(removeSessionNotify.getUserId());

        // 这样处理是否合理，需要检查？
        if (clientUser != null) {
            clientUser.broadcast(new IMProtoMessage<>(header, body), null);
        }
        ****/
    }

    /**
     *组员（新增、删除）修改
     */
    private void groupChangeMemberNotify(MsgHeader header, MessageLite body) {
        /**
        IMGroupChangeMemberNotify groupChangeMemberNotify = (IMGroupChangeMemberNotify) body;
        ClientUser clientUser = null;

        for (long chgUserId : groupChangeMemberNotify.getChgUserIdListList()) {
            clientUser = ClientUserManager.getUserById(chgUserId);
            if (clientUser != null) {
                clientUser.broadcast(new IMProtoMessage<MessageLite>(header, body), null);
            }
        }

        for (long curUserId : groupChangeMemberNotify.getCurUserIdListList()) {
            clientUser = ClientUserManager.getUserById(curUserId);
            if (clientUser != null) {
                clientUser.broadcast(new IMProtoMessage<MessageLite>(header, body), null);
            }
        }
        ****/
    }
}
