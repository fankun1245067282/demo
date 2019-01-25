package com.fankun._15.bytebuf;

import java.util.Arrays;

import io.netty.handler.codec.MessageToByteEncoder;

public class TestGeneric {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		MyGenericClass<Integer> a = new MyGenericClass<Integer>(1);
		System.out.println(a.getClass().getTypeParameters()[0]);
		System.out.println(Arrays.toString(a.getClass().getTypeParameters()));
		System.out.println(Arrays.toString(Integer.class.getTypeParameters()));
		
		MessageToByteEncoder.class.newInstance();
	}
}
