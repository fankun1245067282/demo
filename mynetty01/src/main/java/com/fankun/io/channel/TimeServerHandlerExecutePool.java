package com.fankun.io.channel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool{
	
	private ExecutorService executorService;
	public TimeServerHandlerExecutePool(int maxPoolSize,int quenceSize) {
		executorService = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),maxPoolSize,120L,
				TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(quenceSize));
	}
	
	public void executor(Runnable task) {
		executorService.execute(task);
	}
}
