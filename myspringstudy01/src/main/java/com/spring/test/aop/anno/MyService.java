package com.spring.test.aop.anno;

import org.springframework.stereotype.Service;

/** 
 *  
 * @author zxf 
 * 演示aop测试类 
 */  
@Service
public class MyService {  
    public void queryUsers(){  
        System.out.println("查询所有用户【all users list】");  
    }  
} 