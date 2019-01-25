package com.spring.test.model.factory.abstr;

public class AbstractFactoryTest {
	public static void main(String[] args) {
		DefaultFactory defaultFactory = new DefaultFactory();
		System.out.println(defaultFactory.getCar().getName());
		System.out.println(defaultFactory.getCar("Benz").getName());
		//设计模式的经典之处，就在于解决了编写代码的人和调用代码的人双方的痛处
		//解放双手
		
	}
}
