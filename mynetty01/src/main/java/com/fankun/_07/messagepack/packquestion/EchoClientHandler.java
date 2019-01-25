package com.fankun._07.messagepack.packquestion;

import com.fankun._07.messagepack.Echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
	private int counter;
	private final  String ECHO_REQ = "我们都有一个家";
	
	public EchoClientHandler(){

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Echo echo = new Echo();
		for (int i = 0; i < 10; i++) {
			echo.setStr(ECHO_REQ);
			ctx.writeAndFlush(echo);	
		}

	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		Echo body = (Echo)msg;//字符串中自动去掉delimiter符号
		System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
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
