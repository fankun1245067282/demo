package com.fankun._05.fixedlength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
	private int counter;
	private final  String ECHO_REQ = "Hi,fankun. Welcome to Netty."+Constant.delimiter;
	
	public EchoClientHandler(){

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 10; i++) {
			message = Unpooled.copiedBuffer(ECHO_REQ.getBytes());
			ctx.writeAndFlush(message);	
		}

	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;//字符串中自动去掉delimiter符号
		System.out.println("This is " + ++counter + " times receive server : [" + body + "]");
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("unexpected exception from downstream : "+ cause.getMessage());
		ctx.close();
	}
}
