package com.fankun.netty.server3;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {
	private static final int MAGIC_NUMBER = 0x0CAFFEE0;
	public MessageDecoder() {
 
	}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 14) {
			return;
		}
		// 标记开始读取位置
		in.markReaderIndex();
 
		int magic_number = in.readInt();
		if (MAGIC_NUMBER != magic_number) {
			ctx.close();
			return;
		}
		@SuppressWarnings("unused")
		byte version = in.readByte();
		byte type = in.readByte();
		int sequence = in.readInt();
		
		int nameLength = in.readInt();
		if (nameLength < 0) {
			ctx.close();
			return;
		}
		if (in.readableBytes() < nameLength) {
			// 重置到开始读取位置
			in.resetReaderIndex();
			return;
		}
		byte[] name = new byte[nameLength];
		in.readBytes(name);
 
		int bodyLength = in.readInt();
		if (bodyLength < 0) {
			ctx.close();
			return;
		}
		if (in.readableBytes() < bodyLength) {
			// 重置到开始读取位置
			in.resetReaderIndex();
			return;
		}
		byte[] body = new byte[bodyLength];
		in.readBytes(body);
		
		
		RequestInfoVO req = new RequestInfoVO();
		req.setVersion(version);
		req.setType(type);
		req.setSequence(sequence);
		req.setSequence(sequence);
		req.setNameLength(nameLength);
		req.setName(new String(name, "utf-8"));
		req.setBodyLength(bodyLength);
		req.setBody(new String(body, "utf-8"));
		out.add(req);
		System.out.println("=============MessageDecoder:"+req);
//		ctx.fireChannelRead(req);
		
	}

}
