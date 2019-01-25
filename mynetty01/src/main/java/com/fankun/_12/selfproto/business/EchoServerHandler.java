package com.fankun._12.selfproto.business;



import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{

	private volatile int counter;
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("=========================EchoServerHandler index:"+Index.index.incrementAndGet());
		NettyMessage echo = (NettyMessage)msg;
		System.out.println("this is " + ++counter + " times receive client : [" + echo + "]");
		echo.getHeader().setType(MessageType.BUSINESS_RESP.getValue());
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
