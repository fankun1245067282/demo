package com.fankun.io.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public class Test {
	public static void main(String[] args) throws IOException {
		File file = new File("D:\\test/timer.sql");
		FileInputStream in=  new FileInputStream(file);
		byte[] bytes = new byte[64];
		int n;
		while((n=in.read(bytes))!=-1) {
			System.out.println(new String(bytes,0,n));
		}
		in.close();
		System.out.println("======================================================");
		//这两种发方式都可以读取文件。哈哈哈
		file = new File("D:\\test/timer.sql");
		in=  new FileInputStream(file);
		FileChannel fileChannel = in.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//		while(true) {
//			fileChannel.read(byteBuffer);
//			byteBuffer.flip();
//			if(byteBuffer.remaining()>0) {
//				System.out.println(new String(bytes,0,byteBuffer.remaining()));
//			}else {
//				break;
//			}
//		}
		
		while((n=fileChannel.read(byteBuffer))!=-1) {//等于-1才是结束，0未必是的，因为拥堵，可能会是0
			//就是byteBuffer没有空间了
			
			byteBuffer.flip();
			System.out.println(new String(bytes,0,n));//+"  =-=-=-=-=-="+n
		}
		in.close();
		fileChannel.close();
		Channel channel;
		
	}
}
