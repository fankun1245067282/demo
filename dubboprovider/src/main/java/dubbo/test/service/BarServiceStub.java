package dubbo.test.service;

import dubbo.test.interfaces.BarService;

/**
 * BarServiceStub
 */
public class BarServiceStub implements BarService {

	private final BarService barService;

	// 构造函数传入真正的远程代理对象
	public BarServiceStub(BarService barService) {
		this.barService = barService;
	}

	public String sayHello(String name) {
		// 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
		try {
			System.out.println("-----------sayHello-------------------------------");
			String str = barService.sayHello(name);
			System.out.println("BarServiceStub "+str);
			return str;
		} catch (Exception e) {
			// 你可以容错，可以做任何AOP拦截事项
			return "容错数据";
		}
	}

}