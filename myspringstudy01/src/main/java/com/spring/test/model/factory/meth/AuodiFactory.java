package com.spring.test.model.factory.meth;

import com.spring.test.model.factory.Auodi;
import com.spring.test.model.factory.Car;

public class AuodiFactory implements Factory {

	@Override
	public Car getCar() {
		return new Auodi();
	}

}
