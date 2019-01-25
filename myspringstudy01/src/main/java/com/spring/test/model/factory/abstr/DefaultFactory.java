package com.spring.test.model.factory.abstr;

import com.spring.test.model.factory.Car;

public class DefaultFactory extends AbstractFactory {

	private AbstractFactory defaultFactory = new AuodiFactory(); 
	@Override
	protected Car getCar() {
		return defaultFactory.getCar();
	}
	
}
