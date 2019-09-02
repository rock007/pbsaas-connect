package com.pbsaas.connect.server.app.msg.receiver;

import java.util.concurrent.CountDownLatch;

public class MsgReceiver {

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
