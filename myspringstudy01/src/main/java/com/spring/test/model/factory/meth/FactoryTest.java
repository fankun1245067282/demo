package com.spring.test.model.factory.meth;

public class FactoryTest {
	public static void main(String[] args) {
		//工厂方法模式
		//每个产品的生产商，都拥有各自的工厂
		//生产工艺，高科技程度也是不一样的
		//需要用户关系，这个产品的生产商
		Factory factory = new AuodiFactory();
		System.out.println(factory.getCar().getName());
		factory = new BenzFactory();
		System.out.println(factory.getCar().getName());
		factory = new BmwFactory();
		System.out.println(factory.getCar().getName());
		
		//增加的代码的使用复杂度
		
		//抽象工厂模式
	}
}
