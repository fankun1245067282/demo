package com.fankun.netty.server3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientBootstrap {
	private int port;
	private String host;
	private SocketChannel socketChannel;
	public NettyClientBootstrap(int port, String host) throws Exception {
		this.host = host;
		this.port = port;
		start();
	}
	private void start() throws Exception {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.group(eventLoopGroup);
		bootstrap.remoteAddress(this.host, this.port);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(
						new MessageDecoder(),
						new NettyClientHandler(), 
						new MessageEncoder());
			}
		});
		int flag = 0;
		while(flag==0) {
			try {
				ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
				if (future.isSuccess()) {
					flag = 1;
					socketChannel = (SocketChannel) future.channel();
					System.out.println("connect server  success|");
				}else {
					System.out.println("-----isSuccess false-------");
				}	
			}catch(Exception e) {
				System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-==-=-");
				Thread.sleep(5000);
				//eventLoopGroup.shutdownGracefully().sync();  //关闭服务
			}	
		}


	}
	public int getPort() {
		return this.port;
	}
	public void setPort(int port) {
		this.port = port;
	}
 
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
