package com.test.thread;

public class TaskRunnable implements Runnable{

	@Override
	public void run() {
		int i = 0;
//		try {
			while (i < 3000000) {
				i++;
				System.out.println("i=" + i);
				if (i == 20) {
					System.out.println("----------------"+1);
					Thread.currentThread().interrupt();
//					Thread.sleep(800);
				}
				 
			}
//		} catch (Exception e) {
			//Thread.currentThread().interrupt();
			System.out.println("恢复线程了");
//			run();
//		}
//		while(i<30) {
//			i++;
//			System.out.println("i="+i);
//			try {
//				Thread.sleep(800);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new TaskRunnable());
		t.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	//	System.out.println("打断线程");
		//t.interrupt();

	}

}
