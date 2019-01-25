package com.spring.test.model.myproxy;

import com.spring.test.model.Person;
import com.spring.test.model.XiaoXingXing;

public class MyTestFindLove {
	public static void main(String[] args) throws Throwable {
		Person p = new XiaoXingXing();
		//p.findLove();
		
		Person mymeipo =  (Person)new MyMeipo().getInstance(p);
	//	System.out.println("代理后的对象是："+mymeipo.getClass());
		mymeipo.findLove();
		String name  = mymeipo.getName();
		System.out.println("name:"+name);
	}
}
