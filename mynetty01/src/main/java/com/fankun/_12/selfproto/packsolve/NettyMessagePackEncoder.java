package com.fankun._12.selfproto.packsolve;

import java.util.Map;
import java.util.Map.Entry;

import com.fankun._10.httpnetty.JSONObject;
import com.fankun._12.selfproto.AttachmentType;
import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class NettyMessagePackEncoder
extends MessageToByteEncoder<NettyMessage> {
	public static void main(String[] args) {
		String a = new String("a");
		String b = new String("b");
		System.out.println(a.getClass()==b.getClass());
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
		System.out.println("encode......."+msg);
		if(msg==null || msg.getHeader()==null) {
			throw new Exception("The encoder message is null!");
		}
		Header header = msg.getHeader();
		sendBuf.writeInt(header.getCrcCode());
		sendBuf.writeInt(header.getLength());
		sendBuf.writeLong(header.getSessionID());
		sendBuf.writeByte(header.getType());
		sendBuf.writeByte(header.getPriority());
		int attachmentSize = -1;//为null是为-1
		Map<String,Object> attacment = header.getAttachment();
		if (attacment != null) {
			attachmentSize = attacment.size();
		}
		sendBuf.writeInt(attachmentSize);
		String key = null;
		byte[] keyArray = null;
		Object value = null;
		if (attachmentSize > 0) {
			for (Entry<String, Object> param : attacment.entrySet()) {
				key = param.getKey();
				keyArray = key.getBytes("UTF-8");
				sendBuf.writeInt(keyArray.length);
				sendBuf.writeBytes(keyArray);
				value = param.getValue();
				AttachmentType type = AttachmentType.getTypeByClazz(value.getClass());
				if (type == AttachmentType.None) {
					throw new RuntimeException("数据类型不支持：" + value.getClass());
				}
				//先写入类型，解析好解析
				sendBuf.writeByte(type.getValue());
				if(type==AttachmentType.Byte) {
					sendBuf.writeByte((Byte)value);
				}else if(type==AttachmentType.Short) {
					sendBuf.writeShort((Short)value);
				}else if(type==AttachmentType.Integer) {
					sendBuf.writeInt((Integer)value);
				}else if(type==AttachmentType.Long) {
					sendBuf.writeLong((Long)value);
				}else if(type==AttachmentType.Float) {
					sendBuf.writeFloat((Float)value);
				}else if(type==AttachmentType.Double) {
					sendBuf.writeDouble((Double)value);
				}else if(type==AttachmentType.String) {
					sendBuf.writeInt(((String)value).getBytes("UTF-8").length);
					sendBuf.writeBytes(((String)value).getBytes("UTF-8"));
				}else if(type==AttachmentType.JSONObject) {
					sendBuf.writeInt(((JSONObject)value).toString().getBytes("UTF-8").length);
					sendBuf.writeBytes(((JSONObject)value).toString().getBytes("UTF-8"));
				}
			}
		}

			
		String body = msg.getBody();
		if (body == null) {
			sendBuf.writeInt(0);
		}else {
			byte[] bodyBytes = body.getBytes();
			sendBuf.writeInt(bodyBytes.length);
			sendBuf.writeBytes(bodyBytes);
		}
		
		sendBuf.setInt(4, sendBuf.readableBytes());//设置消息的长度
		System.out.println("消息长度："+sendBuf.readableBytes());
	}
//extends MessageToMessageEncoder<NettyMessage> {
//	@Override
//	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println("encode......."+msg);
//		if(msg==null || msg.getHeader()==null) {
//			System.out.println("==========The encoder message is null!");
//			throw new Exception("The encoder message is null!");
//		}
//		ByteBuf sendBuf = Unpooled.buffer();
//		Header header = msg.getHeader();
//		sendBuf.writeInt(header.getCrcCode());
//		sendBuf.writeInt(header.getLength());
//		sendBuf.writeLong(header.getSessionID());
//		sendBuf.writeByte(header.getType());
//		sendBuf.writeByte(header.getPriority());
//		int attachmentSize = -1;//为null是为-1
//		Map<String,Object> attacment = header.getAttachment();
//		if (attacment != null) {
//			attachmentSize = attacment.size();
//		}
//		sendBuf.writeInt(attachmentSize);
//		String key = null;
//		byte[] keyArray = null;
//		Object value = null;
//		if (attachmentSize > 0) {
//			for (Entry<String, Object> param : attacment.entrySet()) {
//				key = param.getKey();
//				keyArray = key.getBytes("UTF-8");
//				sendBuf.writeInt(keyArray.length);
//				sendBuf.writeBytes(keyArray);
//				value = param.getValue();
//				AttachmentType type = AttachmentType.getTypeByClazz(value.getClass());
//				if (type == AttachmentType.None) {
//					throw new RuntimeException("数据类型不支持：" + value.getClass());
//				}
//				//先写入类型，解析好解析
//				sendBuf.writeByte(type.getValue());
//				if(type==AttachmentType.Byte) {
//					sendBuf.writeByte((Byte)value);
//				}else if(type==AttachmentType.Short) {
//					sendBuf.writeShort((Short)value);
//				}else if(type==AttachmentType.Integer) {
//					sendBuf.writeInt((Integer)value);
//				}else if(type==AttachmentType.Long) {
//					sendBuf.writeLong((Long)value);
//				}else if(type==AttachmentType.Float) {
//					sendBuf.writeFloat((Float)value);
//				}else if(type==AttachmentType.Double) {
//					sendBuf.writeDouble((Double)value);
//				}else if(type==AttachmentType.String) {
//					sendBuf.writeInt(((String)value).getBytes("UTF-8").length);
//					sendBuf.writeBytes(((String)value).getBytes("UTF-8"));
//				}else if(type==AttachmentType.JSONObject) {
//					sendBuf.writeInt(((JSONObject)value).toString().getBytes("UTF-8").length);
//					sendBuf.writeBytes(((JSONObject)value).toString().getBytes("UTF-8"));
//				}
//			}
//		}
//
//			
//		String body = msg.getBody();
//		if (body == null) {
//			sendBuf.writeInt(0);
//		}else {
//			byte[] bodyBytes = body.getBytes();
//			sendBuf.writeInt(bodyBytes.length);
//			sendBuf.writeBytes(bodyBytes);
//		}
//		
//		sendBuf.setInt(4, sendBuf.readableBytes());//设置消息的长度
//		System.out.println("消息长度："+sendBuf.readableBytes());
//		out.add(sendBuf);
//	}

}
