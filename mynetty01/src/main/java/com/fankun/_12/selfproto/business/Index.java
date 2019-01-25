package com.fankun._12.selfproto.business;

import java.util.concurrent.atomic.AtomicInteger;

import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.MessageType;
import com.fankun._12.selfproto.NettyMessage;

public class Index {
	//判断各个pilpe的执行顺序
	public static AtomicInteger index= new AtomicInteger(0);
	
	public static final Integer heartBeatIntervalTime = 3000;//单位毫秒
	public static final Integer readTimeout = 10;//单位秒
	
	
	public static final String REMOTEIP = "127.0.0.1";
	public static final Integer PORT = 8088;
	public static final String LOCALIP = "127.0.0.1";
	public static final Integer LOCALPORT = 7777;
	
	/**
	 * 创建基本消息类型
	 * @param type
	 * @return
	 */
	public static NettyMessage buildMsgUseType(MessageType type) {
		NettyMessage msg = new NettyMessage();
		Header header = new Header();
		header.setType(type.getValue());
		msg.setHeader(header);
		return msg;
	}
}
