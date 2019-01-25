package com.fankun._12.selfproto.business;

import java.util.HashMap;
import java.util.Map;

import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
	private int counter;
	private final  String ECHO_REQ = "我们都有一个家";
	
	public EchoClientHandler(){

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=========================EchoClientHandler channelActive index:"+Index.index.incrementAndGet());
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		//字段不填写,会导致保存，要给一个不为null的值
//		header.setCrcCode();  //有默认值了
		header.setLength(-1);//自动填充,先填写-1
		header.setSessionID(-1);//服务端返回，先填写-1
		header.setType((byte)1);
		header.setPriority((byte)1);
		
		Map<String,Object> attachment = new HashMap<>();
		
		attachment.put("name", "fankun");
		attachment.put("age", 28);
		attachment.put("sprots", "pppp");
		
		header.setAttachment(attachment);
		msg.setHeader(header);
		msg.setBody(ECHO_REQ);
		System.out.println("开始写了。。。。。。。");
		for(int i=0; i<10; i++) {
			msg.getHeader().setSessionID(i);
			ctx.writeAndFlush(msg);
		}
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
		NettyMessage message = (NettyMessage)obj;
		System.out.println("=========================EchoClientHandler index:"+Index.index.incrementAndGet());
		System.out.println("This is " + ++counter + " times receive server : [" + message + "]");
//		if(counter==2) {
//			System.out.println("客户端自动关闭流。。。");
//			ctx.close();
//		}
		if(message.getHeader().getType()==MessageType.LOGIN_RESP.getValue()) {//登录成功,发送几条消息
			NettyMessage msg = new NettyMessage();
			Header header = new Header();
			//字段不填写,会导致保存，要给一个不为null的值
//			header.setCrcCode();  //有默认值了
			header.setLength(-1);//自动填充,先填写-1
			header.setSessionID(-1);//服务端返回，先填写-1
			header.setType(MessageType.BUSINESS_REQ.getValue());
			header.setPriority((byte)1);
			
			Map<String,Object> attachment = new HashMap<>();
			
			attachment.put("name", "fankun");
			attachment.put("age", 28);
			attachment.put("sprots", "pppp");
			
			header.setAttachment(attachment);
			msg.setHeader(header);
			msg.setBody(ECHO_REQ);
			System.out.println("开始写了。。。。。。。");
			for(int i=0; i<3; i++) {
				msg.getHeader().setSessionID(i);
				ctx.writeAndFlush(msg);
			}
		}
		
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
