package com.fk.test.references;

import java.nio.ByteBuffer;

public class TestDirectByteBuffer {
	public static void main(String[] args) {
//		DirectByteBuffer a;
		ByteBuffer b = ByteBuffer.allocateDirect(1024*1024);
		System.out.println(new int[1].getClass().getName());
		System.out.println(new boolean[1].getClass().getName());
		
	}
}
