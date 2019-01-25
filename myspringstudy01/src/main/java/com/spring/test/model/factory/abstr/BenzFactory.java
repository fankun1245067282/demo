package com.spring.test.model.factory.abstr;

import com.spring.test.model.factory.Benz;
import com.spring.test.model.factory.Car;

public class BenzFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Benz();
	}

}
