package com.fankun._04.pack.packquestion;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
	public void bind(int port) throws InterruptedException {
		//配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeServerHandler());
				}});
			//绑定端口，同步等待成功
			ChannelFuture f= b.bind(port).sync();
			
			System.out.println("--------------1---------------");
			
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
			System.out.println("--------------2---------------");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("--------------3---------------");
			//优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println("--------------4---------------");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new TimeServer().bind(8088);
	}
}
