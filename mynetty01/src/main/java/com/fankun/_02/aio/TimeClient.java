package com.fankun._02.aio;


public class TimeClient {
	public static void main(String[] args) {
		AsyncTimeClientHandler client = new AsyncTimeClientHandler("127.0.0.1",8088);
		new Thread(client,"AIO-AsyncTimeClientHandler-001").start();
	}
}
