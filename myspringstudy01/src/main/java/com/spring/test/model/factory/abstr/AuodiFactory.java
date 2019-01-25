package com.spring.test.model.factory.abstr;

import com.spring.test.model.factory.Auodi;
import com.spring.test.model.factory.Car;

public class AuodiFactory extends AbstractFactory {

	/**
	 * 具体的业务逻辑的封装
	 */
	@Override
	public Car getCar() {
		return new Auodi();
	}

}
