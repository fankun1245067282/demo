package com.fankun.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


public class EchoClient {

	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static final EchoClientHandler  echoClient = new EchoClientHandler();
	public static  Channel staticChannel = null;
	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(echoClient);
						}
					});
			System.out.println("----------------------1--------------");
			ChannelFuture f = b.connect().sync();
			System.out.println("----------------------2--------------");
			//f.channel().closeFuture().sync(); //关闭通道
			staticChannel= f.channel();
			System.out.println("----------------------333--------------");
		} finally {
			//group.shutdownGracefully().sync();  //关闭服务
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoClient("localhost", 65535).start();
//		while(true) {
//			Thread.currentThread().sleep(6000);
//			System.out.println("-------------while(true)-------------");
//			staticChannel.writeAndFlush("i am a good boy");
//		}
	}
}
