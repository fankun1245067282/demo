package com.fankun._07.messagepack.packquestion;

import com.fankun._07.messagepack.MsgpackDecoder;
import com.fankun._07.messagepack.MsgpackEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
/**
 * 解决messaagePack的半包问题
 * @author Administrator
 *
 */
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
					ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
					ch.pipeline().addLast("MsgpackDecoder",new MsgpackDecoder());
					ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
					ch.pipeline().addLast("MsgpackEncoder",new MsgpackEncoder());
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
