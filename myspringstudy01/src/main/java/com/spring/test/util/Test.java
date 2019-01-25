package com.spring.test.util;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
    	System.out.println("输入参数为："+args==null?"":Arrays.toString(args));
        if(args==null || args.length<2){
            System.out.println("请输入手机号和盐值");
            return;
        }
        String phone = args[0];
        String salt = args[1];
        String key = "1EfmYOu8BN2XS9l8";
        String sin = ZsEncryptUtil.DynamicEncrypt(phone, key, salt);
        System.out.println("phone:"+phone);
        System.out.println("salt:"+salt);
        System.out.println("sin:"+sin);
    }
}
