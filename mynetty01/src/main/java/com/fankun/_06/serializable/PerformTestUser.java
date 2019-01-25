package com.fankun._06.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 编码性能测试
 * @author Administrator
 * 
 * java序列号的缺点：
 * 1、无法跨语言
 * 2、序列化后的码流太大
 * 3、序列化性能太低
 *
 */
public class PerformTestUser {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		User user = new User();
		user.buildUserId(100).buildUserName("Welcome to Netty");
		int loop = 1000000;
		long startTime = System.currentTimeMillis();
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		for (int i = 0; i < loop; i++) {
			//java序列化（实体类要实现Serializable接口）
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(user);
			os.flush();
			os.close();
			//byte[] b = bos.toByteArray();
			bos.close();		
		}
		long endTime = System.currentTimeMillis();
		//java序列号的码流太大
		System.out.println("the jdk serializable cost time is "+(endTime-startTime)+" ms");
		System.out.println("-----------------------------------");
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		startTime = System.currentTimeMillis();
		byte[] b = null;
		for (int i = 0; i < loop; i++) {
			b = user.codeC(buffer);
		}
		endTime = System.currentTimeMillis();
		System.out.println("the byte array  cost time is "+(endTime-startTime)+" ms");
		
	}
}
