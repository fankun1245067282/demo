package com.fankun._12.selfproto.simplecoder;

import java.util.HashMap;
import java.util.Map;

import com.fankun._12.selfproto.Header;
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
			ctx.writeAndFlush(msg);
		}
		
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		Echo body = (Echo)msg;//字符串中自动去掉delimiter符号
		System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
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
