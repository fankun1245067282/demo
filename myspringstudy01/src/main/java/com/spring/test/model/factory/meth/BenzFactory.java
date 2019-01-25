package com.spring.test.model.factory.meth;

import com.spring.test.model.factory.Benz;
import com.spring.test.model.factory.Car;

public class BenzFactory implements Factory {

	@Override
	public Car getCar() {
		return new Benz();
	}

}
