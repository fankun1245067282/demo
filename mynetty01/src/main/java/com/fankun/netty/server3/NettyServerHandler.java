package com.fankun.netty.server3;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<RequestInfoVO> {
 
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestInfoVO msg) throws Exception {
		//
		System.out.println("NettyServerHandler:::"+msg);
		//ctx.channel().writeAndFlush(msg);
		NettyChannelMap.add(msg.getName(), (SocketChannel)ctx.channel());
		
	}
 

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
	}
 
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("============"+NettyChannelMap.getName((SocketChannel)ctx.channel())+" is died");
	}
 
 
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		
	}
 
}

