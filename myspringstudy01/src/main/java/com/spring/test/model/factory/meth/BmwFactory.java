package com.spring.test.model.factory.meth;

import com.spring.test.model.factory.Bmw;
import com.spring.test.model.factory.Car;

public class BmwFactory implements Factory {

	@Override
	public Car getCar() {
		return new Bmw();
	}

}
