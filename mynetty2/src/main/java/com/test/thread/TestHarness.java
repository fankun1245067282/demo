package com.test.thread;

import java.util.concurrent.CountDownLatch;

public class TestHarness {

	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						System.out.println("等待运行");
						startGate.await();
						System.out.println("开始运行");
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		
		
		long start = System.nanoTime();
		Thread.sleep(1000);
		startGate.countDown();
		System.out.println("------1-------");
		endGate.await();//所有的线程结束才会运行。。。。。
		System.out.println("------2-------");
		long end = System.nanoTime();
		return end-start;
	}
	public static void main(String[] args) throws InterruptedException {
		TestHarness s = new TestHarness();
		long n = s.timeTasks(2,new Runnable() {
			
			@Override
			public void run() {
				System.out.println("jjjjjjjjjj");
			}
		});
		System.out.println("n......"+n);
	}
}
