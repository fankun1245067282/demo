package com.fankun._15.bytebuf;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.concurrent.ForkJoinTask;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel.Unsafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.nio.NioEventLoop;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.internal.StringUtil;

public class Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		ByteBuf buf = Unpooled.buffer(40);
		buf.writeBytes("123456789012345678901234567890".getBytes("utf-8"));
		System.out.println(buf.writableBytes());
		 ByteBuf b = buf.readBytes(30);//length不能大于buf的可读长度
		 byte[] bytes = new byte[30];
		 
		 b.readBytes(bytes);//bytes length不能大于b.可读长度
		 System.out.println(new String(bytes,"utf-8"));
		 
		 
		 buf = Unpooled.buffer(40);
		 buf.writeBytes("123456789012345678901234567890".getBytes("utf-8"));
		 ByteBuf buf1 = Unpooled.buffer(30);
		 buf.readBytes(buf1);//buf1不能大于buf的可读长度
		 testBuf_Buffer();
		 AbstractNioByteChannel a;
		 AdaptiveRecvByteBufAllocator adaptiveRecvByteBufAllocator = null;
		 ByteBufAllocator byteBufAllocator = null;
		 Unsafe unsafe;
		 ChannelPipeline p;
		 System.out.println(StringUtil.simpleClassName(Test.class) + "#0");
		 ByteToMessageDecoder s;
		 MessageToMessageDecoder<Object> v1;
		 LengthFieldBasedFrameDecoder v2;
		 MessageToByteEncoder<Object> v3;
		 LengthFieldPrepender v4;
		 NioEventLoop v5;
		 ChannelFuture v6;
		 DefaultPromise<Object> v7;
		 ForkJoinTask<Object> v8;
		 
		 
		 
		 
		 // sdfdsf
	}
	
	public static void testBuf_Buffer() throws UnsupportedEncodingException {
		System.out.println("============testBuf_Buffer-----------------------------");
		ByteBuf buf = Unpooled.buffer(40);
		buf.writeBytes("123456789012345678901234567890".getBytes("utf-8"));
		System.out.println("   buf.writableBytes():"+buf.writableBytes()+"   buf.readableBytes():"+buf.readableBytes());
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.put("abc".getBytes());
		buffer.flip();
		System.out.println("   buffer.limit():"+buffer.limit()+"   buffer.position():"+buffer.position());
		buf.readBytes(buffer);
		System.out.println("   buf.writableBytes():"+buf.writableBytes()+"   buf.readableBytes():"+buf.readableBytes());
		System.out.println("   buffer.limit():"+buffer.limit()+"   buffer.position():"+buffer.position());

	}
	
	
}
