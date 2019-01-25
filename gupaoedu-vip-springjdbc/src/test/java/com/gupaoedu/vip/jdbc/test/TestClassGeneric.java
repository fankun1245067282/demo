package com.gupaoedu.vip.jdbc.test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.core.common.utils.BeanUtils;
import javax.core.common.utils.DataUtils;

import com.gupaoedu.vip.jdbc.demo.entity.Member;

public class TestClassGeneric {
	public static class MyClass extends ArrayList<Integer>{}
	public static class MyClass2 extends ArrayList{}
	
	public static void main(String[] args) {
		Class c = BeanUtils.getSuperClassGenricType(MyClass.class);
		System.out.println(c);
		
		Class c2 = BeanUtils.getSuperClassGenricType(MyClass2.class);
		System.out.println(c2);
		
		System.out.println(BeanUtils.beanToString(new Member()));
		
		DecimalFormat df= new DecimalFormat(".##");
		System.out.println(df.format(3.155001));
		System.out.println(df.format(3.155));
		System.out.println(DataUtils.round(3.155001, 2));
		System.out.println(DataUtils.round(3.155, 2));
		System.out.println(DataUtils.round(3.154999, 2));
		System.out.println(DataUtils.getPlainNumber(121));
		System.out.println(DataUtils.getPlainNumber(123423421.235));
	}
}