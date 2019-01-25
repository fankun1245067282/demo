package com.fankun.netty.server3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<RequestInfoVO> {
 
	protected void messageReceived(ChannelHandlerContext ctx, RequestInfoVO msg) throws Exception {

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestInfoVO msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("NettyClientHandler:::"+msg.getBody());
		RequestInfoVO req = new RequestInfoVO();
		req.setSequence(msg.getSequence());
		req.setType(msg.getType());
		if (2 == msg.getType()) {
			req.setBody("client");
			ctx.channel().writeAndFlush(req);
		} else if (3 == msg.getType()) {
			req.setBody("zpksb");
			ctx.channel().writeAndFlush(req);
		}
	}
 
}
