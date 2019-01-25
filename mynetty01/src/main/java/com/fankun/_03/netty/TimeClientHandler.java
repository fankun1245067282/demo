package com.fankun._03.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	private final ByteBuf firstmesssage;
	public TimeClientHandler(){
		byte[] req = "QUERY TIME ORDER".getBytes();
		firstmesssage = Unpooled.buffer(req.length);
		firstmesssage.writeBytes(req);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstmesssage);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf)msg;
		byte[] req = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is : "+body);
		ctx.close();
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
