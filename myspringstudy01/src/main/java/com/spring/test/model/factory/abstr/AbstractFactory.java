package com.spring.test.model.factory.abstr;

import com.spring.test.model.factory.Car;

public abstract class AbstractFactory {
	protected abstract Car getCar();
	
	
	//这段代码就是动态配置的功能
	public Car getCar(String name) {
		if("BMW".equalsIgnoreCase(name)) {
			return new BmwFactory().getCar();
		}else if("Benz".equalsIgnoreCase(name)) {
			return new BenzFactory().getCar();
		}else if("Auodi".equalsIgnoreCase(name)) {
			return new AuodiFactory().getCar();
		}else {
			System.out.println("这个产品产不出来");
		}
		return null;
	}
}
