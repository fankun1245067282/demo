package com.fk.test.cpu;

public class Worker implements Runnable{

	@Override
	public void run() {
		long i = 0;
		long dev = 300000000;
		while (true) {
			i++;
			if (i % dev == 0) {
				System.out.println(Thread.currentThread().getName()+"-->"+(i/dev));
			}
		}
	}

}
