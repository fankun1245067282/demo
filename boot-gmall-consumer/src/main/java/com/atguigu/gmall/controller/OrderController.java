package com.atguigu.gmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/initOrder")
	public List<UserAddress> initOrder(String userId){
		return orderService.initOrder(userId);
	}

}
