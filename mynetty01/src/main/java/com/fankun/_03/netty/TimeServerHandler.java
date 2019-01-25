package com.fankun._03.netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf)msg;
		byte[] req = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("The time server receive order : " + body);
		String currentTime = null;
		if("QUERY TIME ORDER".equals(body)) {
			currentTime = new Date().toString();
		}else {
			currentTime = "BAD ORDER";
		}
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
