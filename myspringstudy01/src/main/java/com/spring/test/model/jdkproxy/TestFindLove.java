package com.spring.test.model.jdkproxy;

import java.io.FileOutputStream;

import com.spring.test.model.Person;
import com.spring.test.model.XiaoXingXing;

import sun.misc.ProxyGenerator;

public class TestFindLove {
	public static void main(String[] args) throws Throwable {
		Person p = new XiaoXingXing();
		//p.findLove();
		
		Person meipo =  (Person)new Meipo().getInstance(p);
		System.out.println("代理后的对象是："+meipo.getClass());
		meipo.findLove();
		
		//ProxyGenerator 需要引用jdk rt.jar,会成功代理类的字节码，用反编译工具可查看
		byte[] data= ProxyGenerator.generateProxyClass("$Proxy0", new Class[] {Person.class} );
		FileOutputStream os = new FileOutputStream("D:/test/$Proxy0.class");
		os.write(data);
		os.close();
	}
}
