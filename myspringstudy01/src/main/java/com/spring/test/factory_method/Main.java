package com.spring.test.factory_method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-factory_method.xml");

	public static void h() {
		ctx = new ClassPathXmlApplicationContext("bean-factory_method.xml");

		Car car1 = (Car) ctx.getBean("car1");
		System.out.println(car1);

		Car bmwCar = (Car) ctx.getBean("bmwCar");
		System.out.println(bmwCar);
		Car audiCar = (Car) ctx.getBean("audiCar");
		System.out.println(audiCar);
		
		
        Car car4 = (Car) ctx.getBean("car4");
        System.out.println(car4);
        Car car6  = (Car) ctx.getBean("car6");
        System.out.println(car6);
	}
	
	public static void main(String[] args) {
		h();
		AbstractApplicationContext a;
	}
}
