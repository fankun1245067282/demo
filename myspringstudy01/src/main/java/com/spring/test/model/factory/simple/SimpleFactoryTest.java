package com.spring.test.model.factory.simple;

import com.spring.test.model.factory.Car;

public class SimpleFactoryTest {
	
	public static void main(String[] args) {
		//消费者
		SimpleFactory sf = new SimpleFactory();
		Car car = sf.getCar("auodi");
		System.out.println(car==null?"":car.getName());
	}
}
