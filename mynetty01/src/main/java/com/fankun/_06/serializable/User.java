package com.fankun._06.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.msgpack.annotation.Message;

@Message
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public User buildUserId(int userId) {
		this.userId = userId;
		return this;
	}
	public User buildUserName(String userName) {
		this.userName = userName;
		return this;
	}
	
	
	public byte[] codeC() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] value = this.getUserName().getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.getUserId());
		buffer.flip();
		value = null;
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}
	
	public byte[] codeC(ByteBuffer buffer) {
		buffer.clear();
		byte[] value = this.getUserName().getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.getUserId());
		buffer.flip();
		value = null;
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}
}
