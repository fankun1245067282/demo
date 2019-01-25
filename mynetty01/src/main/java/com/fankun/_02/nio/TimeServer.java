package com.fankun._02.nio;

public class TimeServer {
	public static void main(String[] args) {
		MultiplexerTimerServer server = new MultiplexerTimerServer(8088);
		new Thread(server,"NIO-MultiplexerTimerServer-001").start();
	}
}
