package com.fk.test.zk;

import org.apache.zookeeper.server.quorum.QuorumPeerMain;

public class TestServer {
	public static void main(String[] args) {
		System.out.println("打印所有的参数:");
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				System.out.println("第" + i + "个参数为:" + args[i]);
			}
		}
		System.out.println("打印系统变量:");
		String env = System.getProperty("zookeeper.log.dir");
		System.out.println("zookeeper.log.dir:" + env);
		String logpath = System.getProperty("zookeeper.root.logger");
		System.out.println("zookeeper.root.logger:" + logpath);

		QuorumPeerMain.main(args);
	}
}
