package com.fankun._02.nio;

public class TimeClient {
	public static void main(String[] args) {
		TimeClientHandle client = new TimeClientHandle("127.0.0.1",8088);
		new Thread(client,"TimeClient-001").start();
	}
}
