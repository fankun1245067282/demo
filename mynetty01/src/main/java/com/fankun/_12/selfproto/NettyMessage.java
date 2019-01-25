package com.fankun._12.selfproto;


/**
 * 消息
 * @author Administrator
 *
 */
public class NettyMessage {
	private Header header;//消息投
	private String body;//消息体
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "NettyMessage [header=" + header + ", body=" + body + "]";
	}

	
	
}
