package com.fankun._02.aio;

public class TimeServer {
	public static void main(String[] args) {
		AsyncTimeServerHandler server = new AsyncTimeServerHandler(8088);
		new Thread(server,"AIO-AsyncTimeServerHandler-001").start();
	}
}
