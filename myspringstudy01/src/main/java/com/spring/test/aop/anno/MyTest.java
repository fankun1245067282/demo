package com.spring.test.aop.anno;
import org.springframework.context.support.ClassPathXmlApplicationContext;
  
/** 
 *  
 * @author zxf 
 * 演示aop测试类 
 */   
public class MyTest {  
  
    public static void main(String[] args) {  
    	//3.2.[56]注解会报错
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("myaop2.xml");  
  
        MyService myService = (MyService)ctx.getBean("myService");  
        myService.queryUsers();  
  
        ctx.destroy();
    }  
} 