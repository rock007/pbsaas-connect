package com.pbsaas.connect.server.mars.logic;

import com.pbsaas.connect.core.model.ProtoMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SendMsgQueue {

    private volatile static BlockingQueue<ProtoMessage> queue;

    public static BlockingQueue<ProtoMessage> getQueue() {
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
