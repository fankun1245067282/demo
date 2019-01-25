package com.fankun._02.bio._1socket_2thread;

import java.io.IOException;

import java.net.Socket;

import com.fankun._02.bio.TimeServerHandler;

public class TimeClient{
	public static void main(String[] args) throws InterruptedException {
		Socket socket = null;
		try {
			socket= new Socket("127.0.0.1",8080);
			Thread.sleep(1000);
//			for (int i = 0; i <1; i++) {
				new Mythread(socket).start();//报错。
				//说明客户端的socket不可以在其他线程使用
				//服务端的确可以，说明他们的实现类不一样
				
//				new Thread(new TimeServerHandler(socket)).start();
//			}
			
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				socket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				socket = null;
			}
		}
		
	}

}
