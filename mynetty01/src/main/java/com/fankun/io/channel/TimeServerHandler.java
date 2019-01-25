package com.fankun.io.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class TimeServerHandler implements Runnable{
	private SocketChannel socketChannel = null;
	public TimeServerHandler() {}
	public TimeServerHandler(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	@Override
	public void run() {

		try {
			while (true) {
				ByteBuffer readBuffer = ByteBuffer.allocate(128);
				int readBytes = socketChannel.read(readBuffer);
				System.out.println("========readBytes:"+readBytes);
				if(readBytes>0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("The time server receive order:" + body);
					String currentTime = null;
					if ("QUERY TIME ORDER".equals(body)) {
						currentTime = new Date().toString();
					} else {
						currentTime = "BAD ORDER";
					}
					doWrite(socketChannel,currentTime);
				}else if(readBytes<0) {
					//对端链路关闭
					socketChannel.close();
				}else {
					;//读取到0字节，忽略
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(socketChannel != null) {
				try {
					socketChannel.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				socketChannel = null;
			}
		}
		
		
	}
	private void doWrite(SocketChannel sc, String response) throws IOException {
		if(response!=null && response.trim().length()>0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			
			sc.write(writeBuffer);
			//出现半包文件，就是writeBuffer.hasRemaining()==true，还有没有写完
			//应该就是先写入写入tcp缓冲区，缓存区不够使用了，没有把整个writeBuffer写入到TCP缓冲区
			//TCP缓冲区是没有进程都独立分配的，、、、、
		}
	}
	
}
