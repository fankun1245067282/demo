package com.spring.test.model.singleton;

public class Singleton4 {
	private static final Singleton4 INSTANCE = new Singleton4("Singleton4");
	static {
		System.out.println("Singleton4 static{} ...... ");
	}
	
	
	//private static final Singleton4 INSTANCE = new Singleton4("Singleton4");
	//相当于
//	private static final Singleton4 INSTANCE;
//	static {
//		INSTANCE = new Singleton4("Singleton4");
//		System.out.println("Singleton4 static{} ...... ");
//	}
	//static 保证唯一
	private static class LazyHolder{
		static {
			System.out.println("LazyHolder static{} ...... ");
		}
		private static final Singleton4 INSTANCE = new Singleton4("LazyHolder");
	}
	
	private Singleton4(String str) {
		System.out.println("Singleton4 Constructor ...... "+str);
	}
	
	public static final Singleton4 getInstance() {
		System.out.println("--------------------------------");
//		return null;
		return LazyHolder.INSTANCE;
	}
	//静态代码块，静态属性赋值的执行先后顺序和他们的上下位置有关
	//类的静态代码块只有在使用类的时候才会被加载
	
	//类装在
	//从上往下
	//先属性，后方法，
	//先静态，后动态
}
