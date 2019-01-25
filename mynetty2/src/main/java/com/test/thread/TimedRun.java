package com.test.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TimedRun {

	public static void timerun(Runnable r,long timeout,TimeUnit unit) {
		ExecutorService executor = Executors.newCachedThreadPool();
		FutureTask<?> task = (FutureTask<?>) executor.submit(r);
		try {
			task.get(timeout,unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (TimeoutException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally {
			System.out.println("------------------------");
			task.cancel(true);
		}
	}
	
	public static void main(String[] args) {
		timerun(new Runnable() {
			@Override
			public void run() {
				System.out.println("I am a good boy!");
				
			}},2,TimeUnit.SECONDS);
	}
}
