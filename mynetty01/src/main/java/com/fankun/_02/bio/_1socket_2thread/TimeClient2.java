package com.fankun._02.bio._1socket_2thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fankun._02.bio.TimeServerHandler;

public class TimeClient2{
	
	public static void main(String[] args) throws InterruptedException {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out= null;
		try {
			socket= new Socket("127.0.0.1",8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());	
			for (int i = 0; i <2; i++) {
				new Mythread(socket,in,out).start();//报错。这样也会报错。。。。。。
			}
//				new Mythread(socket).start();//报错。
				//说明客户端的socket不可以在其他线程使用
				//服务端的确可以，说明他们的实现类不一样
				//复制服务端的代码也报错
//				new Thread(new TimeServerHandler(socket)).start();	
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
