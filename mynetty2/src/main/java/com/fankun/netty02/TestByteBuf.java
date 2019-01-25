package com.fankun.netty02;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestByteBuf {
	
	public static void testBufSlice() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		ByteBuf sliced = buf.slice(0, 15);
		System.out.println(sliced.toString(utf8));
		buf.setByte(0, (byte)'J');
		System.out.println(buf.getByte(0) == sliced.getByte(0));
		assert buf.getByte(0) == sliced.getByte(0);
		System.out.println("testBufSlice--------------------------------------");
	}
	
	
	public static void testBufCopy() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		ByteBuf copy = buf.copy(0, 15);
		System.out.println(copy.toString(utf8));
		buf.setByte(0, (byte) 'J');
		assert buf.getByte(0) != copy.getByte(0);
		System.out.println(buf.getByte(0) != copy.getByte(0));
		System.out.println("testBufCopy--------------------------------------");
	}
	
	public static void testGetSet() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		System.out.println((char)buf.getByte(0));
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		
		buf.setByte(0, (byte)'B');
		System.out.println((char)buf.getByte(0));
		assert readerIndex == buf.readerIndex();
		assert writerIndex == buf.writerIndex();
		System.out.println("readerIndex == buf.readerIndex():"+(readerIndex == buf.readerIndex()));
		System.out.println("writerIndex == buf.writerIndex():"+(writerIndex == buf.writerIndex()));
		System.out.println("testGetSet--------------------------------------");
	}
	
	public static void testWrite() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
		System.out.println((char)buf.readByte());
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.writeByte((byte)'?');
		assert readerIndex == buf.readerIndex();
		assert writerIndex == buf.writerIndex();
		System.out.println("readerIndex == buf.readerIndex():"+(readerIndex == buf.readerIndex()));
		System.out.println("writerIndex == buf.writerIndex():"+(writerIndex == buf.writerIndex()));
		System.out.println("testWrite--------------------------------------");
	}


	public static void main(String[] args) {
		testBufSlice();//视图
		testBufCopy();
		testGetSet();
		testWrite();
	}
}
