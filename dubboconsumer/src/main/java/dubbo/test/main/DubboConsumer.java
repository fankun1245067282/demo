package dubbo.test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;

import dubbo.test.bean.TestTB;
import dubbo.test.bean.ValidationParameter;
import dubbo.test.interfaces.BarService;
import dubbo.test.interfaces.CacheService;
import dubbo.test.interfaces.TestTbService;
import dubbo.test.interfaces.ValidationService;

public class DubboConsumer {
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		System.out.println("DubboConsumer start");

		EchoService echoService = (EchoService) context.getBean("cacheService");
		// 回声测试可用性
		String status = (String) echoService.$echo("OK");
		assert (status.equals("OK"));
		System.out.println("status==" + status);
		System.out.println("===---===---===---===---===---===---===---===---===---===---===---===---");

		// 本端是否为消费端，这里会返回true
		boolean isConsumerSide = RpcContext.getContext().isConsumerSide();
		// 获取最后一次调用的提供方IP地址
		String serverIP = RpcContext.getContext().getRemoteHost();
		// 获取当前服务配置信息，所有配置信息都将转换为URL的参数
		String application = RpcContext.getContext().getUrl().getParameter("application");
		// 注意：每发起RPC调用，上下文状态会变化
		// yyyService.yyy();
		System.out.println("----isConsumerSide:" + isConsumerSide);
		System.out.println("----serverIP:" + serverIP);
		System.out.println("----application:" + application);
		System.out.println("===---===---===---===---===---===---===---===---===---===---===---===---");

		TestTbService testTbService = context.getBean("testTbService", TestTbService.class); // 获取远程服务代理
		System.out.println("testTbService==null: " + (testTbService == null));
		TestTB testTb = new TestTB();
		testTb.setName("钱学森");
		// System.out.println(testTb);
		System.out.println("======DubboConsumer");

		// 这个没有传递过去，是不是和分组有关？？？
		// setAttachment 设置的 KV 对，在完成下面一次远程调用会被清空，即多次远程调用要多次设置。
		RpcContext.getContext().setAttachment("index", "1"); // 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用
		testTbService.insertTestTb(testTb);
		// 用于观察zookeeper上节点信息的变化

		ValidationService calidationService = context.getBean("validationService", ValidationService.class); // 获取远程服务代理

		ValidationParameter validationParameter = new ValidationParameter();
		validationParameter.setAge(18);
		validationParameter.setName("fankun");

		try {
			// 这个传递过去了，常规的
			// setAttachment 设置的 KV 对，在完成下面一次远程调用会被清空，即多次远程调用要多次设置。
			RpcContext.getContext().setAttachment("index", "2"); // 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用

			calidationService.update(validationParameter);
			System.out.println("update OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			validationParameter.setEmail("12450@qq.com");
			// 这个传递过去了，常规的
			// setAttachment 设置的 KV 对，在完成下面一次远程调用会被清空，即多次远程调用要多次设置。
			RpcContext.getContext().setAttachment("index", "3"); // 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用

			calidationService.save(validationParameter);
			System.out.println("save OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end======================================");

		cache(context);
		// Thread.sleep(115000);

		
		System.out.println("barService======================================start");
		BarService barService = context.getBean("barService", BarService.class); // 获取远程服务代理
		barService.sayHello("fankun");
		System.out.println("barService======================================end");
	}

	/**
	 * 缓存测试
	 * 
	 * @param context
	 */
	private static void cache(ApplicationContext context) {
		System.out.println("cache===========================start");

		CacheService cacheService = (CacheService) context.getBean("cacheService");

		// verify cache, same result is returned for different invocations (in fact, the
		// return value increases
		// on every invocation on the server side)
		String fix = null;
		for (int i = 0; i < 5; i++) {
			String result = cacheService.findCache("0");
			if (fix == null || fix.equals(result)) {
				System.out.println("OK: " + result);
			} else {
				System.err.println("ERROR: " + result);
			}
			fix = result;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// default cache.size is 1000 for LRU, should have cache expired if invoke more
		// than 1001 times
		for (int n = 0; n < 5; n++) {
			String pre = null;
			for (int i = 0; i < 3; i++) {
				String result = cacheService.findCache(String.valueOf(n));
				if (pre != null && !pre.equals(result)) {
					System.err.println("ERROR: " + result);
				}
				pre = result;
			}
		}

		// verify if the first cache item is expired in LRU cache
		String result = cacheService.findCache("0");
		if (fix != null && !fix.equals(result)) {
			System.out.println("OK: " + result);
		} else {
			System.err.println("ERROR: " + result);
		}
		System.out.println("cache===========================end");
	}
}