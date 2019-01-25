package com.fankun._12.selfproto.business;

import java.util.concurrent.TimeUnit;

import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

public class HeartBeatReqHandler extends SimpleChannelInboundHandler<NettyMessage>  {

	private volatile ScheduledFuture<?> headerBeat;
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println("=========================HeartBeatReqHandler index:"+Index.index.incrementAndGet());
		Header header = msg.getHeader();
		if(header==null) {
			ctx.fireChannelRead(msg);//转发消息
			//如果是握手成功，主动发送心跳,返回结果应该是成功的，否则就关闭了
		}else if(header.getType()==MessageType.LOGIN_RESP.getValue()){
			headerBeat = ctx.executor().scheduleAtFixedRate(
					new HeartBeatTask(ctx), 0, Index.heartBeatIntervalTime, TimeUnit.MILLISECONDS);
			ctx.fireChannelRead(msg);//转发消息，就是透传,否则消息就结束了
		}else if(header.getType()==MessageType.HEARTBEAT_RESP.getValue()){
			
			System.out.println("Client receive heart beat message to server :"+msg);
			//这样就结束了。。。
		}else {
			ctx.fireChannelRead(msg);//转发消息，就是透传
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(headerBeat!=null){
			headerBeat.cancel(true);
			headerBeat = null;
		}
		System.out.println("心跳监测定时器关闭。。。。。。。");
		ctx.fireExceptionCaught(cause);
	}
}
