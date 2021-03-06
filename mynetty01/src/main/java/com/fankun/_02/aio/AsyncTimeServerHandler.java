package com.fankun._02.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable{

	private CountDownLatch latch;
	private AsynchronousServerSocketChannel asynchronousServerSocketChannel;
	
	
	public AsyncTimeServerHandler(int port) {
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("the time server is start  in port: "+port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();//阻塞作用
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doAccept() {
		asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
	}
	//获取AsynchronousServerSocketChannel
	public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
		return asynchronousServerSocketChannel;
	}
	public CountDownLatch getLatch() {
		return latch;
	}

}
