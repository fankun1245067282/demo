package com.fankun.io.channel;

public class TestSystemClassLoader {
	public static void main(String[] args) {
		System.out.println(ClassLoader.getSystemClassLoader());
		System.out.println(new TestSystemClassLoader().getClass().getClassLoader());
	}
}
