package com.fankun.netty02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;

@Sharable //标记该类的实例可以被多个Channel 共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	/**在到服务器的连接已经建立之后将被调用*/
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		//当被通知Channel是活跃的时候，发送一条消息
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}

	/**当从服务器接收到一条消息时被调*/
	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
		//记录已接收消息的转储
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		//在发生异常时，记录错误并关闭Channel
		cause.printStackTrace(); 
		System.out.println("--------------------------client Throwable");
		ctx.close();//有没有都关闭
	}
}