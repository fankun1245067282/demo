package dubbo.test.bean;

import com.alibaba.dubbo.rpc.cluster.Merger;

import dubbo.test.interfaces.TestTbService;

public class MyMerger implements Merger<TestTbService>{

	@Override
	public TestTbService merge(TestTbService... items) {
		System.out.println("这是我的合并策略呀、、、、、、");
		for(TestTbService i:items) {
			i.insertTestTb(new TestTB());
		}
		return null;
	}



}
