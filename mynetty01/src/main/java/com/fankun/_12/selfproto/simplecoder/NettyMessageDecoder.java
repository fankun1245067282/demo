package com.fankun._12.selfproto.simplecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fankun._10.httpnetty.JSONObject;
import com.fankun._12.selfproto.AttachmentType;
import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public final class NettyMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
	public static void main(String[] args) {
		String a = new String("a");
		String b = new String("b");
		System.out.println(a.getClass()==b.getClass());
	}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf recvBuf, List<Object> out) throws Exception {
		System.out.println("==================================");
		NettyMessage msg = new NettyMessage();
		Header header = new Header();

		header.setCrcCode(recvBuf.readInt());
		header.setLength(recvBuf.readInt());
		header.setSessionID(recvBuf.readLong());
		header.setType(recvBuf.readByte());
		header.setPriority(recvBuf.readByte());
		
		Map<String,Object> attachment = new HashMap<>();
		header.setAttachment(attachment);
		System.out.println("-----------1----2-2-----------header:"+header);
		int attachmentSize = recvBuf.readInt();
		System.out.println("-----------1----2-2-----------attachmentSize:"+attachmentSize);
		if (attachmentSize > 0) {
			int byteSize  = 0;//字节码长度
			byte[] byteArr = null;//字节码数组
			String key = null;//key值
			Object value = null;//vlue值
			for(int i=0; i<attachmentSize; i++) {
				byteSize = recvBuf.readInt();//key的字节码长度
				byteArr = new byte[byteSize];//key的字节码数组
				recvBuf.readBytes(byteArr);
				key = new String(byteArr,"UTF-8");//key值
				
				//读取value值的类型
				byte valueType = recvBuf.readByte();
				AttachmentType attachmentType = AttachmentType.getTypeByValue(valueType);
				
				if(attachmentType==AttachmentType.Byte) {
					value = recvBuf.readByte();
				}else if(attachmentType==AttachmentType.Short) {
					value = recvBuf.readShort();
				}else if(attachmentType==AttachmentType.Integer) {
					value = recvBuf.readInt();
				}else if(attachmentType==AttachmentType.Long) {
					value = recvBuf.readLong();
				}else if(attachmentType==AttachmentType.Float) {
					value = recvBuf.readFloat();
				}else if(attachmentType==AttachmentType.Double) {
					value = recvBuf.readDouble();
				}else if(attachmentType==AttachmentType.String) {
					byteSize = recvBuf.readInt();
					byteArr = new byte[byteSize];
					recvBuf.readBytes(byteArr);
					value = new String(byteArr,"UTF-8");//value值
				}else if(attachmentType==AttachmentType.JSONObject) {
					byteSize = recvBuf.readInt();
					byteArr = new byte[byteSize];
					recvBuf.readBytes(byteArr);
					value = new JSONObject(new String(byteArr,"UTF-8"));
				}
				attachment.put(key, value);
			}

		}

		//读取body
		int bodySize = recvBuf.readInt();
		if (bodySize > 0) {
			byte[] bodyArr = new byte[bodySize];
			recvBuf.readBytes(bodyArr);
			String body = new String(bodyArr, "UTF-8");// value值
			msg.setBody(body);
		}

		msg.setHeader(header);
		
		System.out.println("decoder......."+msg);
		out.add(msg);
	}
}
