package com.fankun.netty.server4;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestBuf02 {
	public static void main(String[] args) {
		Random random = new Random();
		ByteBuf buf = Unpooled.buffer(16);
		while (buf.writableBytes() >= 4) {
			buf.writeInt(random.nextInt());
		}
		System.out.println("buf.readableBytes():"+buf.readableBytes());
		for (int i = 0; i < buf.readableBytes(); i++) {
			System.out.println("iiiiii:"+i);
			System.out.println(buf.getByte(i));
		}
		
		for (int i = 0; 0 < buf.readableBytes(); i++) {
			System.out.println("====:"+i);
			System.out.println(buf.readByte());
		}
	}
}
