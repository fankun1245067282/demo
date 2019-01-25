package com.fankun._15.bytebuf;

public class TestJvm {
	public static void main(String[] args) throws InterruptedException {
		int i = 0;
		while(true) {
			Thread.sleep(1000);
			System.out.println("===I:"+i++);
		}
	}
}
