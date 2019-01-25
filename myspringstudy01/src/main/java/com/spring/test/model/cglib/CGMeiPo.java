package com.spring.test.model.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGMeiPo implements MethodInterceptor{
	
	private Object target;
	/**
	 * 好像并没有持有被代理对象引用
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object getInstance(Object obj) throws Exception{
		this.target = obj;
		//动态字节码生成
		Enhancer enhancer = new Enhancer();
		//设置父类，把父类设置为谁
		enhancer.setSuperclass(obj.getClass());
		enhancer.setCallback(this);
		//1、生成源代码
		//2、编译成class文件
		//3、加载到JVM，创建代理对象返回		
		return enhancer.create();
	}
	
	
	/**
	 * 同样是做了字节码重组这样一件事
	 * 对于使用api的用户来说，是无感知的
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("我是CG媒婆， 得给你找个异性才行");
		System.out.println("开始海选。。。");
		System.out.println("--------------");
//		method.invoke(target, args);
		//	这个obj是动态代理类的对象，CGLib new 出来以后的对象，是被代理对象子类的对象，继承我们自己的类
		//  new 子类之前，实际上默认先调用super()方法。相当于间接调用父类的引用
		//  子类重写了父类的所有方法
		//  我们改变子类对象的某些属性，是可以间接的操作父类的属性的
		//  obj子类的引用，不能method.invoke(obj,args);自己调用自己
		proxy.invokeSuper(obj, args);
		System.out.println("--------------");
		System.out.println("如果合适的话，准备办事吧。。。");
		return null;
	}

}
