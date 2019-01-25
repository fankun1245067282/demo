package com.fankun._02.bio.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.fankun._02.bio.TimeServerHandler;

public class TimeServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8080);
			System.out.println("server is starting...");
			TimeServerHandlerExecutePool singleExecutor = new 
					TimeServerHandlerExecutePool(50,10000);//创建IO线程池
			while (true) {
				Socket socket = server.accept();
				//new Thread(new TimeServerHandler(socket)).start();
				singleExecutor.executor(new TimeServerHandler(socket));
			}
		} finally {
			if (server != null) {
				server.close();
				server = null;
				System.out.println("server is closed!");
			}
		}
	}
}
