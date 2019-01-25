package com.fankun.netty02;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private int port = 8090;
	public EchoServer(int port) {this.port = port;}
	public EchoServer() {}

	public static void main(String[] args) throws Exception {
		new EchoServer().start();
	}

	public void start() throws Exception {
		final EchoServerHandler serverHandler = new EchoServerHandler();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
			.channel(NioServerSocketChannel.class)//指定所使用的NIO传输Channel
			.localAddress(new InetSocketAddress(port))//使用指定的端口设置套接字地址
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(serverHandler);//serverHandler new EchoServerHandler()
					
					//EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
					//添加一个EchoServerHandler 到子Channel的ChannelPipeline
				}
			});
			System.out.println("-----------------1-----------------");
			//异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
			ChannelFuture f = b.bind().sync();
			System.out.println("-----------------2-----------------");
			//获取Channel 的CloseFuture，并且阻塞当前线程直到它完成
			f.channel().closeFuture().sync();
			System.out.println("-----------------3-----------------");
		} finally {
			//关闭EventLoopGroup，释放所有的资源
			group.shutdownGracefully().sync();
			System.out.println("-----------------4-----------------");
		}
	}
}