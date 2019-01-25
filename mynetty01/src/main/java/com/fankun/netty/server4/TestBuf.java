package com.fankun.netty.server4;

import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class TestBuf {
	public static void main(String[] args) {
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		ByteBuf heapBuf = Unpooled.buffer(8);
		for (int i = 0; i < heapBuf.capacity(); i++) {
			heapBuf.writeByte(i + 1);
			System.out.println("======222=========");
		}
		
		ByteBuf directBuf = Unpooled.directBuffer(16);
		for (int i = 0; i < directBuf.capacity(); i++) {
			directBuf.writeByte(i + 1);
			
		}
		System.out.println("======111========="+directBuf.readableBytes());
		
		// 添加ByteBuf到CompositeByteBuf
		compBuf.addComponents(heapBuf, directBuf);
		// 删除第一个ByteBuf
		compBuf.removeComponent(0);
		System.out.println("======111========="+compBuf.readableBytes());
		for (int i = 0; i < compBuf.capacity(); i++) {
			directBuf.writeByte(i + 1);
			System.out.println(compBuf.getByte(i));
		}
		
		System.out.println("======================:::capacity:"+compBuf.capacity());
		Iterator<ByteBuf> iter = compBuf.iterator();
		while (iter.hasNext()) {
			System.out.println("===============vvv:"+iter.next().capacity());
		}
		// 使用数组访问数据
		if (!compBuf.hasArray()) {
			int len = compBuf.readableBytes();
			System.out.println("======================::::"+len);
			byte[] arr = new byte[len];
			compBuf.getBytes(0, arr);
			for(byte a:arr) {
				System.out.println("======a=========:"+a);
			}
		}
		
		
		
		// create a ByteBuf of capacity is 16
		ByteBuf buf = Unpooled.buffer(16);
		// write data to buf
		for (int i = 0; i < 16; i++) {
			buf.writeByte(i + 1);
		}
		// read data from buf
		for (int i = 0; i < buf.capacity(); i++) {
			System.out.println(buf.getByte(i));
		}
	}
}
