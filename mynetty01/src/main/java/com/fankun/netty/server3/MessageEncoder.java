package com.fankun.netty.server3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<RequestInfoVO> {
 
	private static final String DEFAULT_ENCODE = "utf-8";
 
	private static final int MAGIC_NUMBER = 0x0CAFFEE0;
 
	public MessageEncoder() {
	}
 
	@Override
	protected void encode(ChannelHandlerContext ctx, RequestInfoVO msg, ByteBuf out) throws Exception {
		System.out.println("============MessageEncoder: msg"+msg);
		
		@SuppressWarnings("resource")
		ByteBufOutputStream writer = new ByteBufOutputStream(out);
		byte[] body = null;
		byte[] name = null;
 
		if (null != msg && null != msg.getBody() && "" != msg.getBody()) {
			body = msg.getBody().getBytes(DEFAULT_ENCODE);
		}
		
		if (null != msg && null != msg.getName() && "" != msg.getName()) {
			name = msg.getName().getBytes(DEFAULT_ENCODE);
		}
 
//		private final int magic = 0x0CAFFEE0;//校验用固定值0x0CAFFEE0
//		private byte version  = 0;//版本号
//		private byte type ;// //类型，请求或者响应
//		private int sequence ;//序号标记一对请求响应
//		private int nameLength ;//clientName长度
//		private String name = "fankun";
//		private int bodyLength ;//body长度
//		private String body;
		
		writer.writeInt(MAGIC_NUMBER);
		writer.writeByte(msg.getVersion());
		writer.writeByte(msg.getType());
		writer.writeInt(msg.getSequence());
 
		if (null == name || 0 == name.length) {
			writer.writeInt(0);
		} else {
			writer.writeInt(name.length);
			writer.write(name);
		}
		if (null == body || 0 == body.length) {
			writer.writeInt(0);
		} else {
			writer.writeInt(body.length);
			writer.write(body);
		}
	}
 
}
