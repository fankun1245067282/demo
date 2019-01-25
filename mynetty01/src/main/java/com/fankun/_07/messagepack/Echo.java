package com.fankun._07.messagepack;

import java.io.IOException;

import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;

@Message
public class Echo {
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "Echo [str=" + str + "]";
	}
	
	public static void main(String[] args) throws IOException {
		MessagePack msgPack = new MessagePack();
		Echo echo = new Echo();
		echo.setStr("wo meng ");
		byte[] bytes = msgPack.write(echo);
		Echo echo2 = msgPack.read(bytes, Echo.class);
		System.out.println(echo2);
		
		Object echo3 = msgPack.read(bytes, Object.class);
		System.out.println((Echo)echo3);
	}
}
