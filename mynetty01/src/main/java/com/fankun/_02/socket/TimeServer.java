package com.fankun._02.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TimeServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8080);
			System.out.println("server is starting...");
			while (true) {
				Socket socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
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
