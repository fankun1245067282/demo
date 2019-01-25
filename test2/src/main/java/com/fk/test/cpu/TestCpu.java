package com.fk.test.cpu;

public class TestCpu {
	public static void main(String[] args) {
		Worker worker = new Worker();
		Thread t = new Thread(worker,"vv-1");
		t.start();
		Thread.currentThread().getThreadGroup().list();
	}
}
