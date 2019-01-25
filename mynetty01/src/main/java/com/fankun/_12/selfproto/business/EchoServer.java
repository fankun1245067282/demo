package com.fankun._12.selfproto.business;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class EchoServer {
	public void bind(String ip,int port) throws InterruptedException {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.handler(new LoggingHandler(LogLevel.DEBUG))// 监听作用，不是很清楚！！！！
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("nettyMessageDecoder",new NettyMessagePackDecoder(1024*1024,4,4));
					ch.pipeline().addLast("nettyMessageEncoder",new NettyMessagePackEncoder());
					ch.pipeline().addLast("ReadTimeoutHandler",new ReadTimeoutHandler(Index.readTimeout));//netty自带的类
					ch.pipeline().addLast("LoginAuthRespHandler",new LoginAuthRespHandler());
					ch.pipeline().addLast("HeartBeatRespHandler",new HeartBeatRespHandler());
					ch.pipeline().addLast(new EchoServerHandler());
				}
			});
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(ip,port).sync();

			System.out.println("netty server start ok ["+ip+":"+port+"]");

			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
			System.out.println("--------------2---------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("--------------3---------------");
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println("--------------4---------------");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new EchoServer().bind(Index.REMOTEIP,Index.PORT);
	}
}
