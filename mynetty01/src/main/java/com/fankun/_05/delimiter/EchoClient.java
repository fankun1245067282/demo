package com.fankun._05.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
	
	public void connect(String host,int port) throws InterruptedException {
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {

					ByteBuf delimiter = Unpooled.copiedBuffer(Constant.delimiter.getBytes());
					//1024表示单个消息的最大长度，大于这个长度没有找到分隔符就报异常TooLongFrameException
					//把下面的DelimiterBasedFrameDecoder注释掉会出现粘包的现象
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new EchoClientHandler());
				}});
			
			//发起异步链接请求
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待客户端链路关闭（这个一直挂起，知道channel关闭，手动关闭ctx.close()）
			f.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new EchoClient().connect("127.0.0.1", 8088);
	}
}
