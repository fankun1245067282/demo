package com.fankun._06.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.msgpack.MessagePack;

public class TestUser {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		User user = new User();
		user.buildUserId(100).buildUserName("Welcome to Netty");
		
		//java序列化（实体类要实现Serializable接口）
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(user);
		os.flush();
		os.close();
		byte[] b = bos.toByteArray();
		bos.close();
		//java序列号的码流太大
		System.out.println("the jdk serializable length is : "+b.length);
		System.out.println("-----------------------------------");
		System.out.println("the byte array serializable length is : "+user.codeC().length);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(bis);
		User ruser = (User)is.readObject();
		System.out.println("userId:"+ruser.getUserId());
		System.out.println("userName:"+ruser.getUserName());
		
		
		MessagePack msgPack = new MessagePack();
		byte[] bytes = msgPack.write(user);//要在类上添加注解
		System.out.println("the MessagePack serializable length is : "+bytes.length);
		User msgPackUser = msgPack.read(bytes, User.class);
		System.out.println("userId:"+msgPackUser.getUserId());
		System.out.println("userName:"+msgPackUser.getUserName());
	}
}
