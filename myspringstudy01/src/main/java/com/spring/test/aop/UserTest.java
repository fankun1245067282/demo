package com.spring.test.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
	/**
	 * aop作用
	 * 权限 authentication
	 * 缓存 caching
	 * debuging 调试
	 * logging 日志
	 * transaction manager 事务管理
	 * listener（moniter） 监听
	 * intercepter 拦截器（过滤器）
	 * context passing 内容传递
	 */
    public static void main(String[] args) {  
    	  
    	Class clazz = Object.class;
    	System.out.println(clazz);
    	System.out.println(clazz.getClass().getName());
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("myaop.xml");
  
        UserService userService = (UserService)ctx.getBean("userService");  
        userService.addUser();
        userService.delUser();//接口不是final,实现是final,被重写了
        FinalService finalService = (FinalService)ctx.getBean("finalService");  
        finalService.testfinal();//final方法没有被重写
        
        ctx.destroy();  
    }
}
