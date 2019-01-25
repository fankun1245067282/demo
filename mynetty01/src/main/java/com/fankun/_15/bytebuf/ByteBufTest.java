package com.fankun._15.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufTest {
	public static void main(String[] args) {
		ByteBuf buf = Unpooled.buffer(4);
		buf.writeBytes("avcdd234".getBytes());
		System.out.println(buf.readableBytes());
		byte b = buf.readByte();
		byte[] bytes = new byte[4];
		buf.readBytes(bytes);
		System.out.println(new String(new byte[] {b}));
		System.out.println(new String(bytes));
		buf.discardReadBytes();
		
//		PooledDirectByteBuf a;
		
	}
}
