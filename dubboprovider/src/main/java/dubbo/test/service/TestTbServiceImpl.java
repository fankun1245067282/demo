package dubbo.test.service;



import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;

import dubbo.test.bean.TestTB;
import dubbo.test.interfaces.TestTbService;

@Service("testTbService")
public class TestTbServiceImpl implements TestTbService {

	@Override
	public void insertTestTb(TestTB testTB) {
		String index = RpcContext.getContext().getAttachment("index");
		System.out.println("TestTbServiceImpl index:::"+index);
		System.out.println("TestTbServiceImpl====:"+testTB);
		
	}

}