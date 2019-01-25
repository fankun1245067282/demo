package com.fankun._12.selfproto.business;

import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginAuthReqHandler extends SimpleChannelInboundHandler<NettyMessage>  {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=========================LoginAuthReqHandler channelActive index:"+Index.index.incrementAndGet());
		NettyMessage msg = Index.buildMsgUseType(MessageType.LOGIN_REQ);
		System.out.println("初始化握手信息："+msg);
		ctx.writeAndFlush(msg);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println("=========================LoginAuthReqHandler index:"+Index.index.incrementAndGet()+"    msg:"+msg);
		Header header = msg.getHeader();
		if(header==null) {
			ctx.fireChannelRead(msg);//转发消息
			//如果是握手应答消息，判断是否认证成功
		}else if(header.getType()==MessageType.LOGIN_RESP.getValue()){
			//握手成功
			if("0".equals(msg.getBody())) {
				System.out.println("Login is OK : "+msg);
				ctx.fireChannelRead(msg);//转发消息
			}else {//握手失败，关闭连接
				ctx.close();
				System.out.println("channel  被关闭了");
			}
		}else {
			ctx.fireChannelRead(msg);//转发消息，就是透传
		}

	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}
