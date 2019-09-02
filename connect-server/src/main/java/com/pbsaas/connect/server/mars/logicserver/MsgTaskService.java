/**
 * backend_im  by sam @2017年12月7日  
 */
package com.pbsaas.connect.server.mars.logicserver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import com.pbsaas.connect.proto.PaintFriend;
import com.pbsaas.connect.server.mars.connect.SendMsgQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;

/**
 * 分发消息(通知)
 * 
 * @author sam
 *
 */
@Component
@Scope("singleton")
public class MsgTaskService {

	    private static final Logger logger = LoggerFactory.getLogger(MsgTaskService.class);

	    public static AtomicBoolean shutdown = new AtomicBoolean(false);

	    // 任务队列
	    private BlockingQueue<PaintFriend.ReqBody> taskQueue;
	    
	    // 阻塞式
	    private ExecutorService takeExecutor;
	    // 执行业务的线程池
	    private ExecutorService taskExecutor;
		
	    //@Autowired
	    //private ChatService chatService;
	    
	    //@Autowired
	    //private FriendService friendService;
	    
	    @PostConstruct
	    private void start() {
	    	
	        takeExecutor = Executors.newSingleThreadExecutor();
	        taskExecutor = Executors.newFixedThreadPool(10);
	        taskQueue = SendMsgQueue.getQueue();
	        logger.info("MsgTaskService start");
	        
	        takeExecutor.execute(new Runnable() {
	            @Override
	            public void run() {
	                while (!shutdown.get()) {
	                    try {
                            PaintFriend.ReqBody messageHolder = taskQueue.take();
	                        logger.info("MsgTaskQueue取出任务: taskQueue=" + taskQueue.size());
	                        
	                        startTask(messageHolder);
	                        
	                    } catch (InterruptedException e) {
	                        logger.warn("receiveQueue take", e);
	                    }
	                }
	            }

	            private void startTask(PaintFriend.ReqBody reqBody) {
	                taskExecutor.execute(new Runnable() {
	                    @Override
	                    public void run() {

	                        logger.info("开始执行取出的消息任务 ：" + reqBody.toString());

	                        try{

	                            String uid=reqBody.getUid();
	                            String token= reqBody.getToken();

	                            switch (reqBody.getActid()){

                                    case PaintFriend.ActID.ACT_ID_SEND_MSG_VALUE:

                                        PaintFriend.MsgBody oneMsg = reqBody.getData().unpack(PaintFriend.MsgBody.class);

                                        if(oneMsg.getChatType()==PaintFriend.ChatType.CHAT_TYPE_GROUP_VALUE){

                                            /***
                                            ChatGroupMember gm=new ChatGroupMember();

                                            gm.setIds(new ChatGroupMemberIds(new Long(oneMsg.getTarget()),""));
                                            //群组
                                            Page<ChatGroupMember> page= friendService.searchGroupMembers(gm, 0, 999);

                                            for(ChatGroupMember member:page.getContent()){

                                                String toUser= member.getIds().getUser_id();

                                                if(toUser.equals(oneMsg.getFrom())) continue;

                                                sendMsg(PaintFriend.ChatType.CHAT_TYPE_GROUP,oneMsg.getTarget(),toUser,oneMsg);
                                            }
                                            ***/

                                        }else if(oneMsg.getChatType()==PaintFriend.ChatType.CHAT_TYPE_FRIEND_VALUE){

                                            //个人
                                            sendMsg(PaintFriend.ChatType.CHAT_TYPE_FRIEND,oneMsg.getTo(),oneMsg.getTo(),oneMsg);

                                        }else{

                                            logger.warn("错误的chatType类型");
                                        }
                                        break;
                                    case PaintFriend.ActID.ACT_ID_LOGIN_VALUE:

                                        /***
                                        ChatMessage chatMessage=new ChatMessage();

                                        Page<ChatMessage> pages= chatService.search(chatMessage,1,50);

                                        List<PaintFriend.MsgBody> oldMsgs = new LinkedList<>();

                                        for(ChatMessage msg:pages.getContent()){

                                            PaintFriend.MsgBody msgBody=PaintFriend.MsgBody.newBuilder()
                                                    .setChatType(msg.getGroup_id()>0?PaintFriend.ChatType.CHAT_TYPE_GROUP_VALUE:PaintFriend.ChatType.CHAT_TYPE_FRIEND_VALUE)
                                                    .setFrom(msg.getSend_from())
                                                    .setTo(msg.getTo_user())
                                                    .setText(msg.getContent())
                                                    .setUrl(msg.getUrl())
                                                    .setMsgType(msg.getMsgType())
                                                    .setTime(Long.valueOf(msg.getSend_date().getTime()).intValue())
                                                    .build();

                                            oldMsgs.add(msgBody);
                                        }

                                        PaintFriend.ChatMessageList chatMessageList= PaintFriend.ChatMessageList.newBuilder()
                                                .addAllMessages(oldMsgs)
                                                .build();

                                        List<ConnInfo> connList= SessionPool.get(uid);

                                        for(ConnInfo conn:connList){

                                            response(conn.getChannel(), AppConstants.MESSAGE_PUSH, PaintFriend.RespBody.newBuilder()
                                                    .setActid(PaintFriend.ActID.ACT_ID_OFFLINE_MSGS_VALUE)
                                                    .setResult(1)
                                                    .setMsg("离线消息")
                                                    .setData(Any.pack(chatMessageList))
                                                    .build().toByteArray(), new Runnable() {
                                                @Override
                                                public void run() {

                                                    //执行一次就够了

                                                    for (ChatMessage rmMsg: pages.getContent()) {

                                                        chatService.deleteMessage(rmMsg.getId());
                                                        logger.debug("删除离线消息:"+rmMsg.getId());
                                                    }

                                                }
                                            });
                                        }
***/
                                        break;
                                        default:
                                }

                            }catch (Exception ex){
	                            logger.error("运行发送消息任务出错：",ex);
                            }
	                    }
	                });
	            }
	        });
	        logger.info("启动服务完成");
	    }
	    
	    private void sendMsg(PaintFriend.ChatType chatType,String target,String toUser, PaintFriend.MsgBody msg){

	        /***
	    	List<ConnInfo> connInfos= SessionPool.get(toUser);

        	//判断是否在线
        	if(connInfos.size()==0){

        		//写入数据库
        		ChatMessage m=new ChatMessage();

        		m.setContent(msg.getText());
        		m.setMsgType(msg.getMsgType());
        		m.setUrl(msg.getUrl());
        		m.setGroup_id(chatType==PaintFriend.ChatType.CHAT_TYPE_GROUP? Long.valueOf(target):0L);
        		m.setRec_date(new Date());
        		m.setSend_date(new Date());
        		m.setSend_from(msg.getFrom());
        		m.setStatus(0);
        		m.setTo_user(toUser);

				chatService.saveMessage(m);

        	}else{

                for(ConnInfo conn:connInfos){

                    try {
                        response(conn.getChannel(), AppConstants.MESSAGE_PUSH,  PaintFriend.RespBody.newBuilder()
                                .setActid( PaintFriend.ActID.ACT_ID_SEND_MSG_VALUE)
                                .setResult(1)
                                .setMsg("发送成功")
                                .setData(Any.pack(msg))
                                .build().toByteArray());
                    }catch (Exception ex){

                        logger.error("回复客户出错：",ex);
                    }

                }

        	}
	    	**/
	    }

    private  void response(Channel channel, int cmdId, byte[] bytes) throws Exception {

	        response(channel,cmdId,bytes,null);
    }


        /***
         * 客户端回应
         *
         * @param channel
         * @param cmdId
         * @param bytes
         */
		private  void response(Channel channel, int cmdId, byte[] bytes,Runnable success_fn) throws Exception {

		    /****
			MessageHolder messageHolder = new MessageHolder();

			messageHolder.setCmdId(cmdId);
            messageHolder.setSeq(0);
			messageHolder.setBody(bytes);

            messageHolder.setChannel(channel);

			logger.debug("resp msg :"+messageHolder.toString());
            byte[] respBuf = messageHolder.encode();

            ChannelFuture recChannel=channel.writeAndFlush(channel.alloc().buffer().writeBytes(respBuf));

            recChannel.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (future.isSuccess()) {
                        //关闭连接
                        logger.info("回复成功 ");

                        if(success_fn!=null)
                            success_fn.run();

                    } else {

                        SessionPool.remove(channel);
                        logger.info("回复失败，删除连接,当前连接数："+SessionPool.size());
                    }

                }
            });
***/
		}
}
