package com.fankun._02.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient{
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out= null;
		try {
			socket= new Socket("127.0.0.1",8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			OutputStream out1 = socket.getOutputStream();
			//SocketOutputStream  继承 FileOutputStream 继承OutputStream（是一个类）
			
			out = new PrintWriter(out1);
			
			String body = null;
			while(true) {
				// Scanner取得的输入以space, tab, enter 键为结束符，
				// 要想取得包含space在内的输入，可以用java.io.BufferedReader类来实现
				// 使用BufferedReader的readLine( )方法
				// 必须要处理java.io.IOException异常
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				// java.io.InputStreamReader继承了Reader类
				String read = null;
				System.out.print("输入数据：");
				try {
					read = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("输入数据：" + read + "===");
				if ("bye".equals(read)) {
					System.out.println("ByeBye\nByeByeEveryOne");
//					out.println("ByeBye\nByeByeEveryOne");
//					out.flush();
					break;
				}
				  
				out.println(read);
				out.flush();
				body = in.readLine();
				if(body ==null) {
					System.out.println("body is null ,,, break!!!");
					break;
				}
				System.out.println("client receive:"+body);
	
			}
			
			//关闭。。。。。
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
