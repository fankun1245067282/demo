package com.fankun._12.selfproto.business;

import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;

public class HeartBeatTask implements Runnable{

	private final ChannelHandlerContext ctx;
	
	public HeartBeatTask(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		NettyMessage heartBeat = Index.buildMsgUseType(MessageType.HEARTBEAT_REQ);
		
		System.out.println("Client send heart beat message to server :"+heartBeat);
		ctx.writeAndFlush(heartBeat);
	}
	


}
