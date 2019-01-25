package com.fankun._05.fixedlength;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
	public void bind(int port) throws InterruptedException {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.handler(new LoggingHandler(LogLevel.INFO))// 监听作用
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//按照固定长度解析，长度为20
							ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});
			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();

			System.out.println("--------------1---------------");

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
		new EchoServer().bind(8088);
	}
}
