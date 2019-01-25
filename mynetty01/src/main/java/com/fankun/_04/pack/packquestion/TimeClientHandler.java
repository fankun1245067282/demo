package com.fankun._04.pack.packquestion;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
		for (int i = 0; i < 100; i++) {//一次发送多个请求，出现服务端出现粘包现象
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);	
		}

	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf byteBuf = (ByteBuf)msg;
		byte[] req = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(req);
		String body = new String(req,"UTF-8").substring(0, req.length-Constant.line_separator.length());
		System.out.println("Now is : "+body+"; the counter is : "+ ++counter);
		//ctx.close();
		
//		Now is : BAD ORDER
//		BAD ORDER; the counter is : 1
//		出现粘包的现象，就是把多个返回包合并到一个包里面 BAD ORDER\n+BAD ORDER\n
//		请求包也出现粘包现金，多个QUERY TIME ORDER\n合并到一起，当做一个请求了		
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
