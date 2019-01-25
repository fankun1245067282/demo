package com.fankun._12.selfproto.business;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginAuthRespHandler extends SimpleChannelInboundHandler<NettyMessage>  {

	private ConcurrentMap<String,Boolean> nodeCheck =
	new ConcurrentHashMap<String,Boolean>();
	
	private Set<String> whiteList = new HashSet<String>();
	{
		whiteList.add("127.0.0.1");
		whiteList.add("192.168.4.63");
	}
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
		System.out.println("=========================LoginAuthRespHandler index:"+Index.index.incrementAndGet());
		Header header = msg.getHeader();
		if(header==null) {
			ctx.fireChannelRead(msg);//转发消息，就是透传
			//如果是握手请求消息，是否在白名单中
		}else if(header.getType()==MessageType.LOGIN_REQ.getValue()){
			String nodeIndex = ctx.channel().remoteAddress().toString();
			System.out.println("nodeIndex:"+nodeIndex);
			NettyMessage loginResp = Index.buildMsgUseType(MessageType.LOGIN_RESP);
			//重复登录，拒绝
			if(nodeCheck.containsKey(nodeIndex)) {
				loginResp.setBody("-1");
			}else {
				InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				System.out.println("ip:"+ip);
				if(whiteList.contains(ip)) {
					loginResp.setBody("0");
					nodeCheck.put(nodeIndex, true);
				}else {
					loginResp.setBody("-1");
				}
				System.out.println("The login response is"+loginResp);
			}
			ctx.writeAndFlush(loginResp);
		}else {
			ctx.fireChannelRead(msg);//转发消息，就是透传
		}

	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		nodeCheck.remove(ctx.channel().remoteAddress().toString());//删除缓存
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}

}
