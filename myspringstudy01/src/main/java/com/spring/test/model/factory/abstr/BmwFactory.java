package com.spring.test.model.factory.abstr;

import com.spring.test.model.factory.Bmw;
import com.spring.test.model.factory.Car;

public class BmwFactory extends AbstractFactory {

	@Override
	public Car getCar() {
		return new Bmw();
	}

}
