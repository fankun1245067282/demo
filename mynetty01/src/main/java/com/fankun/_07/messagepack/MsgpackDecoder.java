package com.fankun._07.messagepack;



import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf>{
	//和使用 ByteToMessageDecoder的效果一样

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		System.out.println("---------decode-"+msg.readableBytes());
		byte[] bytes = new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		MessagePack msgPack = new MessagePack();
		Echo obj = msgPack.read(bytes, Echo.class);//要转换为具体类型，否则会报错，后面不能转为为具体类型
		out.add(obj);
		System.out.println("---------decode-obj:"+obj);
	}


}
