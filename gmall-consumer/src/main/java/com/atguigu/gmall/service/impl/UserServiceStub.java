package com.atguigu.gmall.service.impl;

import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserService;

public class UserServiceStub implements UserService {

	private final UserService userService;
	
	/**
	 * 传入的是userService是远程代理对象
	 * @param userService
	 */
	public UserServiceStub(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public List<UserAddress> getUserAddressList(String userId) {
		System.out.println("UserServiceStub....");
		if(!StringUtils.isEmpty(userId)) {
			return userService.getUserAddressList(userId);
		}
		return null;
	}

}
