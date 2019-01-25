package com.fankun._12.selfproto.business;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class EchoClient {
	private  ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	EventLoopGroup group = new NioEventLoopGroup();
	
	public void connect(String host,int port) throws InterruptedException {
		//配置客户端NIO线程组
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>(){
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//各个pipeline的读取顺序
					//ChannelInboundHandler写在前面的先执行
					//ChannelOutboundHandler的执行顺序相反，顺序倒过来
					//channelActive的顺序也一样
					//当其中一个执行了ctx.writeAndFlush后面的ChannelInboundHandler就不走了，继续走后面的ChannelOutboundHandler
					ch.pipeline().addLast("nettyMessageDecoder",new NettyMessagePackDecoder(1024*1024,4,4));
					ch.pipeline().addLast("nettyMessageEncoder",new NettyMessagePackEncoder());
					ch.pipeline().addLast("ReadTimeoutHandler",new ReadTimeoutHandler(Index.readTimeout));//netty自带的类
					ch.pipeline().addLast("LoginAuthReqHandler",new LoginAuthReqHandler());
					ch.pipeline().addLast("HeartBeatReqHandler",new HeartBeatReqHandler());
					ch.pipeline().addLast(new EchoClientHandler());
					
				}});
			
			//发起异步链接请求
			ChannelFuture f = b.connect(
					new InetSocketAddress(host, port),
					new InetSocketAddress(Index.LOCALIP, Index.LOCALPORT)).sync();
			
			//等待客户端链路关闭（这个一直挂起，知道channel关闭，手动关闭ctx.close()）
			f.channel().closeFuture().sync();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			//优雅退出，释放NIO线程组
			//group.shutdownGracefully();
			
			System.out.println("客户的结束了。。。。。。");
			Thread.sleep(5000);
			//所有资源释放完成以后，清空资源，再次发起重连操作
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						
						System.out.println("调用。。。connect....");
						connect(host, port);//发起重新链接
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new EchoClient().connect(Index.REMOTEIP, Index.PORT);
	}
}
