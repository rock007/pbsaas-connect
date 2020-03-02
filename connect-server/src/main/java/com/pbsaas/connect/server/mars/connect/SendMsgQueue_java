/**
 * paintFriend_backend  by sam @2018年2月5日  
 */
package com.pbsaas.connect.server.mars.connect;

import com.pbsaas.connect.proto.PaintFriend;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;



/**
 * 发送消息队列
 * 
 * @author sam
 *
 */
public class SendMsgQueue {

    private volatile static BlockingQueue<PaintFriend.ReqBody> queue;

    public static BlockingQueue<PaintFriend.ReqBody> getQueue() {
        if (queue == null) {
            synchronized (SendMsgQueue.class) {
                if (queue == null) {
                    queue = new LinkedBlockingDeque<>(1024);
                }
            }
        }
        return queue;
    }
}
