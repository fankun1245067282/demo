package com.spring.test.aop;
public class UserServiceImpl implements UserService {

    
    @Override
    public void addUser() {
        System.out.println("添加成功");
    }

    @Override
    public final void delUser() {
        System.out.println("删除成功");
    }

}