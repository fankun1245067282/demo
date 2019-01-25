package dubbo.test.bean;

import dubbo.test.interfaces.BarService;

public class StubBarServiceProxy implements BarService {
	private BarService barService;
	public StubBarServiceProxy(BarService barService){
		this.barService = barService;
	}

	@Override
	public String sayHello(String name) {
		// 此代码在客户端执行, 你可以在客户端做ThreadLocal本地缓存，或预先验证参数是否合法，等等
		try {
			System.out.println("-----------sayHello-------------StubBarServiceProxy------------------");
			String str = barService.sayHello(name);
			System.out.println("StubBarServiceProxy "+str);
			return str;
		} catch (Exception e) {
			// 你可以容错，可以做任何AOP拦截事项
			return "容错数据";
		}
	}

}
