package com.fankun.io.channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
	public static void main(String[] args) throws IOException {
		File file = new File("D:/test/a.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		FileChannel channel = raf.getChannel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(128);
		putdata(0,buffer,channel);
		putdata(5000000,buffer,channel);
		putdata(50000,buffer,channel);
		System.out.println("path:"+file.getAbsolutePath()+"  size:"+channel.size());
		
		//文件raf和channel两个的position是可以相互看到的
		
		System.out.println("position:"+raf.getFilePointer());
		System.out.println("position:"+channel.position());
		raf.seek(100);
		System.out.println("position:"+raf.getFilePointer());
		System.out.println("position:"+channel.position());
		channel.position(200);
		System.out.println("position:"+raf.getFilePointer());
		System.out.println("position:"+channel.position());
		channel.close();
		raf.close();
	}
	private static void putdata(int position,ByteBuffer buffer,FileChannel channel) throws IOException {
		String str = "*<-----------position:"+position;
		buffer.clear();
		buffer.put(str.getBytes("US-ASCII"));
		buffer.flip();
		channel.position(position);
		channel.write(buffer);
	}
	
}
