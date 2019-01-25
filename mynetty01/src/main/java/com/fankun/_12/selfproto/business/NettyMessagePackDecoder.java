package com.fankun._12.selfproto.business;

import java.util.HashMap;
import java.util.Map;

import com.fankun._10.httpnetty.JSONObject;
import com.fankun._12.selfproto.AttachmentType;
import com.fankun._12.selfproto.Header;
import com.fankun._12.selfproto.NettyMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
/**
 * 解决半包问题
 * @author Administrator
 *
 */
public final class NettyMessagePackDecoder extends LengthFieldBasedFrameDecoder{
	/**
	 * 
	 * @param maxFrameLength  消息的最大长度
	 * @param lengthFieldOffset  消息（保存消息长度）的字段偏移量
	 * @param lengthFieldLength  消息（保存消息长度）的字段长度
	 */
	public NettyMessagePackDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		System.out.println("=========================NettyMessagePackDecoder index:"+Index.index.incrementAndGet());
		try {
			
			if(in==null) {
				System.out.println("===========in=======================frame==null");
				return null;
			}
			 /**
	         * 通过源码我们能看到在读的过程中
	         * 每读一次读过的字节即被抛弃
	         * 即指针会往前跳
	         */
			//把这个注释掉就正常了，神奇了
//			ByteBuf frame = (ByteBuf)super.decode(ctx, in);
			ByteBuf frame = in;
			
			System.out.println("decoder.......in.readableBytes():"+in.readableBytes());
			NettyMessage msg = new NettyMessage();
			Header header = new Header();

			header.setCrcCode(frame.readInt());
			header.setLength(frame.readInt());
			header.setSessionID(frame.readLong());
			header.setType(frame.readByte());
			header.setPriority(frame.readByte());
			
			Map<String,Object> attachment = new HashMap<>();
			header.setAttachment(attachment);
			int attachmentSize = frame.readInt();
			if (attachmentSize > 0) {
				int byteSize  = 0;//字节码长度
				byte[] byteArr = null;//字节码数组
				String key = null;//key值
				Object value = null;//vlue值
				for(int i=0; i<attachmentSize; i++) {
					byteSize = frame.readInt();//key的字节码长度
					byteArr = new byte[byteSize];//key的字节码数组
					frame.readBytes(byteArr);
					key = new String(byteArr,"UTF-8");//key值
					
					//读取value值的类型
					byte valueType = frame.readByte();
					AttachmentType attachmentType = AttachmentType.getTypeByValue(valueType);
					
					if(attachmentType==AttachmentType.Byte) {
						value = frame.readByte();
					}else if(attachmentType==AttachmentType.Short) {
						value = frame.readShort();
					}else if(attachmentType==AttachmentType.Integer) {
						value = frame.readInt();
					}else if(attachmentType==AttachmentType.Long) {
						value = frame.readLong();
					}else if(attachmentType==AttachmentType.Float) {
						value = frame.readFloat();
					}else if(attachmentType==AttachmentType.Double) {
						value = frame.readDouble();
					}else if(attachmentType==AttachmentType.String) {
						byteSize = frame.readInt();
						byteArr = new byte[byteSize];
						frame.readBytes(byteArr);
						value = new String(byteArr,"UTF-8");//value值
					}else if(attachmentType==AttachmentType.JSONObject) {
						byteSize = frame.readInt();
						byteArr = new byte[byteSize];
						frame.readBytes(byteArr);
						value = new JSONObject(new String(byteArr,"UTF-8"));
					}
					attachment.put(key, value);
				}

			}

			//读取body
			int bodySize = frame.readInt();
			if (bodySize > 0) {
				byte[] bodyArr = new byte[bodySize];
				frame.readBytes(bodyArr);
				String body = new String(bodyArr, "UTF-8");// value值
				msg.setBody(body);
			}

			msg.setHeader(header);
			
			System.out.println("decoder.......msg:"+msg);
			return msg;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}