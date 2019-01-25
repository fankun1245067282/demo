package com.fankun.io.channel;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;



public class TimerServer implements Runnable{

	private ServerSocketChannel serverChannel;
	private volatile boolean stop;
	TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,10000);//创建IO线程池
	/**
	 * 初始化多路复用器，绑定监听端口
	 * @param port
	 */
	public TimerServer (int port) {
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);//像socket同步非阻塞
			serverChannel.socket().bind(new InetSocketAddress(port),1024);
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
				SocketChannel sc = serverChannel.accept();//同行非阻塞。。。
				if(sc==null) {
					System.out.println("没有人链接，休息一下。。。。");
					Thread.sleep(1000);
				}else {
					System.out.println("Incoming connection from:"+sc.socket().getRemoteSocketAddress());
					singleExecutor.executor(new TimeServerHandler(sc));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	
	public static void main(String[] args) {
		TimerServer server = new TimerServer(8088);
		new Thread(server,"NIO-TimerServer-001").start();
	}

}
