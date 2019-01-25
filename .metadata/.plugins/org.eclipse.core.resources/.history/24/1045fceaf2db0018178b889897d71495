package com.fk.test.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.server.LogFormatter;

public class TestClient implements Watcher{
	static Watcher watcher = new TestClient();
	static CountDownLatch countDownLatch = new CountDownLatch(1);
	public static void main(String[] args) {
		LogFormatter a;
		try {
			ZooKeeper client = new ZooKeeper("", 5000, watcher);
			System.out.println("链接状态1："+client.getState());
			countDownLatch.await();
			System.out.println("链接状态2："+client.getState());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		if(event.getState()==KeeperState.SyncConnected) {
			if(event.getType()==EventType.None) {
				countDownLatch.countDown();
				System.out.println("登录成功");
			}
		}
	}
}
