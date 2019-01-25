package com.fankun.netty02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@Sharable // 标示一个Channel-Handler 可以被多个Channel 安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	public EchoServerHandler() {
		System.out.println("EchoServerHandler create......");
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 将消息记录到控制台
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));

		ctx.write(in);//将接收到的消息写给发送者，而不冲刷出站消息
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		//将未决消息冲刷到远程节点，并且关闭该Channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//		.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		//打印异常栈跟踪
		cause.printStackTrace();
		//关闭该Channel
		ctx.close();
	}
}