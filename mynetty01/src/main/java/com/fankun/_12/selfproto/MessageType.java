package com.fankun._12.selfproto;

public enum MessageType {
	BUSINESS_REQ((byte)0),//业务请求
	BUSINESS_RESP((byte)1),//业务响应
	ONE_WAY((byte)2),//既是请求 ，也是响应
	LOGIN_REQ((byte)3),//握手请求
	LOGIN_RESP((byte)4),//握手响应
	HEARTBEAT_REQ((byte)5),//心跳请求响应
	HEARTBEAT_RESP((byte)6),//心跳响应
	;
	
	
	private byte value;
	
	MessageType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
	public MessageType getTypeByVal(Byte val) {
		if(val==null) {
			return null;
		}
		for(MessageType type:MessageType.values()) {
			if(type.getValue()==val.intValue()) {
				return type;
			}
		}
		return null;
	}
	
	
}
