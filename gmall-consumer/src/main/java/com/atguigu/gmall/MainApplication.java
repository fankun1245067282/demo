package com.atguigu.gmall;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.gmall.service.OrderService;
import com.atguigu.gmall.service.UserService;

public class MainApplication {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
//		OrderService orderService = (OrderService)applicationContext.getBean("orderService");
//		orderService.initOrder("fankun");
		
		UserService userService = (UserService)applicationContext.getBean("userService");
		System.out.println("------------------");
		userService.getUserAddressList("fankun");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
