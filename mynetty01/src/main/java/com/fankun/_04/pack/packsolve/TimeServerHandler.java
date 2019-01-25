package com.fankun._04.pack.packsolve;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * netty中出现粘包问题解决方案
 * @author Administrator
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter{

	private volatile int counter;
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;//这个直接是字符串，不是ByteBuf了，字符串中自动去掉\n符号
		System.out.println("The time server receive order : " + body+"; the counter is : " + ++counter);
		
		String currentTime = null;
		if("QUERY TIME ORDER".equals(body)) {
			currentTime = new Date().toString();
		}else {
			currentTime = "BAD ORDER";
		}
		currentTime+=Constant.line_separator;
		
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
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
