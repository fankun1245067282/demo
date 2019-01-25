package com.fankun._02.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
		
		InputStream in = null;
		OutputStream out= null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();

			byte[] data = new byte[2048];
			int length = 0;
			while ((length = in.read(data))!=-1) {
				String result = new String(data);
				System.out.println(result);
				System.out.println("length:" + length);
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
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out = null;
			}
			
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				socket = null;
			}
		}
		
		
	}
	
	
}
