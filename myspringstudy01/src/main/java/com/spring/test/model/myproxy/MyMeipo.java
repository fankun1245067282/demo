package com.spring.test.model.myproxy;

import java.lang.reflect.Method;

import com.spring.test.model.Person;


public class MyMeipo implements MyInvocationHandler {
	
	private Person target;
	
	//获取被代理人的质料
	public Object getInstance(Person target) throws Exception{
		this.target = target;
		Class<?> clazz = target.getClass();
		System.out.println("被代理对象的class是："+clazz);
		//创建代理对象
		return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是媒婆，你的性别是："+this.target.getSex()+" 得给你找个异性才行");
		System.out.println("开始海选。。。");
		System.out.println("--------------");
		Object obj = method.invoke(this.target, args);
		System.out.println("--------------");
		System.out.println("如果合适的话，准备办事吧。。。");
		return obj;
	}

}
