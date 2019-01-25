package com.fankun.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.test.bean.User;

public class UserTest  extends BaseJunit4Test{
    @Autowired //自动注入  
    private User user; 
    
    @Test  
//    @Transactional   //标明此方法需使用事务    
//    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚   
    public void test(){  
        System.out.println("测试Spring整合Junit4进行单元测试");  
          
        System.out.println(user);
    }  
}
