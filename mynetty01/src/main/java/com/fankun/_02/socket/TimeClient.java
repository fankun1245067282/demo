package com.fankun._02.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient{
	public static void main(String[] args) throws UnknownHostException, IOException {
		String msg = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

		byte[] byteMsg = msg.getBytes();

		Socket socket = new Socket("127.0.0.1", 8080);
		OutputStream out = socket.getOutputStream();
//		BufferedOutputStream bw = new BufferedOutputStream(out);
		for (int i = 0;i<10000;i++) {
			out.write(byteMsg);
//			bw.write(byteMsg);
//			bw.flush();
//			服务端读取： 说明有一个缓冲区
//server is starting...
//			abababab
//			length:8
//			abababab
//			length:8
//			abababab
//			length:2


		}
//		System.in.read();
		
	}
	
	
}
