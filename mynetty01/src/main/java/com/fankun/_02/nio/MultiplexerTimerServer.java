package com.fankun._02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimerServer implements Runnable{

	private Selector selector;
	private ServerSocketChannel serverChannel;
	private volatile boolean stop;
	
	/**
	 * 初始化多路复用器，绑定监听端口
	 * @param port
	 */
	public MultiplexerTimerServer (int port) {
		try {
			selector = Selector.open();
			System.out.println("-----selector-------------------------------");
			System.out.println(selector==Selector.open());
			System.out.println("-----selector-------------------------------");
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);//像socket同步非阻塞
			serverChannel.socket().bind(new InetSocketAddress(port),1024);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("the time server is start in port:"+port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void stop(){
		this.stop = true;
	}
	@Override
	public void run() {
		while(!stop) {
			try {
				selector.select(1000);//休眠1秒钟
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				System.out.println("------------------==");
				while(it.hasNext()) {
					System.out.println("------------------");
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
//						if (key != null) {
//							key.cancel();
//							if (key.channel() != null) {
//								key.channel().close();
//							}
//						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会自动去注册并关闭，所有不需要重新释放资源
		if(selector!=null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
//		System.out.println("key.isValid():"+key.isValid());
//		System.out.println("key.isAcceptable():"+key.isAcceptable());
//		System.out.println("key.isReadable():"+key.isReadable());
		if(key.isValid()) {
			//处理新接入的请求消息
			if(key.isAcceptable()) {
				//accept the new connection
				ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				//Add the new connection to the selector
				sc.register(selector,SelectionKey.OP_READ);
			}
			System.out.println("key.isReadable():"+key.isReadable());
			System.out.println("key.isReadable():"+key.isReadable());
			if(key.isReadable()) {
				//read the data
				SocketChannel sc = (SocketChannel)key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
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
					doWrite(sc,currentTime);
				}else if(readBytes<0) {
					//对端链路关闭
					key.cancel();
					sc.close();
				}else {
					;//读取到0字节，忽略
				}
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
