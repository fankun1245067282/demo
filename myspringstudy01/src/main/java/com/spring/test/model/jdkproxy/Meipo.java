package com.spring.test.model.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.spring.test.model.Person;

/**
 * 媒婆
 * @author Administrator
 *
 */
public class Meipo implements InvocationHandler {

	private Person target;//被代理对象的引用作为一个成员变量被保存下来了
	
	//获取被代理人的质料
	public Object getInstance(Person target) throws Exception{
		this.target = target;
		Class<?> clazz = target.getClass();
		System.out.println("被代理对象的class是："+clazz);
		//创建代理对象
		return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("我是媒婆，你的性别是："+this.target.getSex()+" 得给你找个异性才行");
		System.out.println("开始海选。。。");
		System.out.println("--------------");
	//	this.target.findLove();
		method.invoke(this.target, args);
	//	method.invoke(proxy, args); //这样不行
		System.out.println("--------------");
		System.out.println("如果合适的话，准备办事吧。。。");
		return null;
	}

}
