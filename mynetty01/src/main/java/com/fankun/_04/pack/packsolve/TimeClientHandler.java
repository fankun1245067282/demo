package com.fankun._04.pack.packsolve;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * netty中出现粘包问题解决方案
 * @author Administrator
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	private int counter;
	private byte[] req;
	
	public TimeClientHandler(){
		String str = "QUERY TIME ORDER"+Constant.line_separator;
		req = str.getBytes();

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);	
		}

	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String)msg;//字符串中自动去掉\n符号
		System.out.println("Now is : "+body+"; the counter is : "+ ++counter);
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
