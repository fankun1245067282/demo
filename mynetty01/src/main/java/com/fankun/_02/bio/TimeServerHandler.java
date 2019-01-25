package com.fankun._02.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable{
	private Socket socket = null;
	public TimeServerHandler() {}
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		BufferedReader in = null;
		PrintWriter out= null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			String currentTime = null;
			String body = null;
			while (true) {
				body = in.readLine();
				if (body == null) {
					System.out.println("body is null ,,, break!!!");
					break;
				}
				
				System.out.println("The time server receive order:" + body+"  "+"".equals(body));
				if ("QUERY TIME ORDER".equals(body)) {
					currentTime = new Date().toString();
				} else {
					currentTime = "BAD ORDER";
				}
				out.println(currentTime);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				in = null;
			}
			if(out != null) {
				out.close();
				out = null;
			}
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
