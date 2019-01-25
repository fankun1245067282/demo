package com.spring.test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.test.bean.TestA;
import com.spring.test.bean.User;

public class StartMain {
	/**
	 * debugger
	 * F5单步调试进入函数内部。
	 * F6单步调试不进入函数内部
	 * F7由函数内部返回到调用处。
	 * F8一直执行到下一个断点。
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
//		TestA testa = (TestA)context.getBean("testa");
		User user = (User)context.getBean("user");
		System.out.println(user);
		System.out.println(context);
		
//		StringBuilder sb = new StringBuilder("ANCDEF");
	}
	
	
}
