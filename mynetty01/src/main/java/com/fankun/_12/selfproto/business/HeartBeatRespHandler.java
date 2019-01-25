package com.fankun._12.selfproto.business;


import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatRespHandler extends SimpleChannelInboundHandler<NettyMessage>  {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println("=========================HeartBeatRespHandler index:"+Index.index.incrementAndGet());
		Header header = msg.getHeader();
		if(header==null) {
			ctx.fireChannelRead(msg);//转发消息
			//如果是心跳请求，返回心跳应答消息
		}else if(header.getType()==MessageType.HEARTBEAT_REQ.getValue()){
			NettyMessage heartBeat = Index.buildMsgUseType(MessageType.HEARTBEAT_RESP);
			System.out.println("send heart beat response message to client :"+heartBeat);
			ctx.writeAndFlush(heartBeat);
		}else {
			ctx.fireChannelRead(msg);//转发消息，就是透传
		}

	}

}
