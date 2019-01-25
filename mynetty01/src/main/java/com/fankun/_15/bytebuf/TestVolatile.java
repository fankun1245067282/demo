package com.fankun._15.bytebuf;

public class TestVolatile {
	private static boolean stop = false;
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				while(!stop) {
					i++;
					System.out.println("i="+i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		t.start();
		Thread.sleep(3000);
		stop = true;
	}
}
