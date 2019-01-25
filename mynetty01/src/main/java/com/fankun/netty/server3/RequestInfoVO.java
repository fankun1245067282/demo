package com.fankun.netty.server3;

import java.io.Serializable;

public class RequestInfoVO implements Serializable {

//协议分为header和body两部分，都是用网络字节序（BIG ENDIAN）
//header{
//	magic 32bit;  //校验用固定值0x0CAFFEE0
//	version 8bit;  //版本号
//	type 8bit;     //类型，请求或者响应
//	seq 32bit;     //序号标记一对请求响应
//	length 32bit;  //body长度
//}
//body{
// 
//}
	private static final long serialVersionUID = 1L;
	private  final int magic = 0x0CAFFEE0;//校验用固定值0x0CAFFEE0
	private byte version  = 0;//版本号
	private byte type ;// //类型，请求或者响应
	private int sequence ;//序号标记一对请求响应
	private int nameLength ;//clientName长度
	private String name = "fankun";
	private int bodyLength ;//body长度
	private String body;
	


	public byte getVersion() {
		return version;
	}



	public void setVersion(byte version) {
		this.version = version;
	}



	public byte getType() {
		return type;
	}



	public void setType(byte type) {
		this.type = type;
	}



	public int getSequence() {
		return sequence;
	}



	public void setSequence(int sequence) {
		this.sequence = sequence;
	}



	public int getNameLength() {
		return nameLength;
	}



	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getBodyLength() {
		return bodyLength;
	}



	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public int getMagic() {
		return magic;
	}



	@Override
	public String toString() {
		return "RequestInfoVO [magic=" + magic + ", version=" + version + ", type=" + type + ", sequence=" + sequence
				+ ", nameLength=" + nameLength + ", name=" + name + ", bodyLength=" + bodyLength + ", body=" + body
				+ "]";
	}



	
	
}
