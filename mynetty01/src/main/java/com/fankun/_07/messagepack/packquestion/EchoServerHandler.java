package com.fankun._07.messagepack.packquestion;



import com.fankun._07.messagepack.Echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{

	private volatile int counter;
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Echo echo = (Echo)msg;
		System.out.println("this is " + ++counter + " times receive client : [" + echo + "]");
		ctx.write(echo);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("有一个channel挂了");
	}
	
}
