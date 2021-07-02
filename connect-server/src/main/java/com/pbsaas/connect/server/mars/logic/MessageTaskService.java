package com.pbsaas.connect.server.mars.logic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import com.google.protobuf.MessageLite;
import com.pbsaas.connect.core.model.MsgHeader;
import com.pbsaas.connect.core.model.ProtoMessage;
import com.pbsaas.connect.proto.Connect;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;
import io.netty.channel.Channel;

//@Component
//@Scope("singleton")
public class MessageTaskService {

    private static final Logger logger = LoggerFactory.getLogger(MessageTaskService.class);

    public static AtomicBoolean shutdown = new AtomicBoolean(false);

    // 任务队列
    private BlockingQueue<ProtoMessage> taskQueue;

    // 阻塞式
    private ExecutorService takeExecutor;
    // 执行业务的线程池
    private ExecutorService taskExecutor;

    //@Autowired
    //private ChatService chatService;

    //@Autowired
    //private FriendService friendService;

    @Autowired
    private LogicManager logicManager;

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
                        ProtoMessage messageHolder = taskQueue.take();
                        logger.info("MsgTaskQueue取出任务: taskQueue=" + taskQueue.size());

                        startTask(messageHolder);

                    } catch (InterruptedException e) {
                        logger.warn("receiveQueue take", e);
                    }
                }
            }

            private void startTask(ProtoMessage message) {
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {

                        logger.info("开始执行取出的消息任务 ：" + message.toString());

                        try{

                            //MsgHeader header = message.getHeader();
                            final Connect.ReqBody request = Connect.ReqBody.parseFrom(message.getBody().toByteArray());
                            //PaintFriend.ParamList paramList = request.getData().unpack(PaintFriend.ParamList.class);

                        }catch (Exception ex){
                            logger.error("运行发送消息任务出错：",ex);
                        }
                    }
                });
            }
        });
        logger.info("启动服务完成");
    }

    private  void response(Channel channel,ProtoMessage message) throws Exception {

        response(channel,message,null);
    }


    /***
     * 客户端回应
     */
    private  void response(Channel channel,ProtoMessage message ,Runnable success_fn) throws Exception {

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

         ChannelFuture recChannel=channel.writeAndFlush(message);
        recChannel.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    //关闭连接
                    logger.info("回复成功 ");
                    if (success_fn != null)
                        success_fn.run();
                } else {
                    //SessionPool.remove(channel);
                    logger.info("回复失败，删除连接,当前连接数：");
                }
            }
        });

    }

}
