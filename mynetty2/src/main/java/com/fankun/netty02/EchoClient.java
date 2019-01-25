package com.fankun.netty02;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	private String host = "127.0.0.1";
	private int port = 8090;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public EchoClient() {}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)//指定EventLoopGroup 以处理客户端事件；需要适用于NIO 的实现
			.channel(NioSocketChannel.class)//适用于NIO 传输的Channel 类型
			.remoteAddress(new InetSocketAddress(host, port))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					//在创建Channel 时，向ChannelPipeline中添加一个Echo-ClientHandler 实例
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			//连接到远程节点，阻塞等待直到连接完成
			ChannelFuture f = b.connect().sync();
			
			//阻塞，直到Channel 关闭
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoClient().start();
	}
}