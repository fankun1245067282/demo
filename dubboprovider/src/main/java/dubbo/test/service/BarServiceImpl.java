package dubbo.test.service;


import org.springframework.stereotype.Service;

import dubbo.test.interfaces.BarService;

/**
 * BarServiceImpl
 */
@Service("barService")
public class BarServiceImpl implements BarService {

	@Override
	public String sayHello(String str) {
		String result = "hello "+str;
		System.out.println("BarServiceImpl "+result);
		return result;
	}


}