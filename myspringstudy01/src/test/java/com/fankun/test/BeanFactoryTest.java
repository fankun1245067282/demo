package com.fankun.test;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.spring.test.bean.User;

public class BeanFactoryTest {
	@Test
	@SuppressWarnings(value = { "deprecation" })
	public void testSimpleLoad() {
		BeanFactory bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		User user = (User)bf.getBean("user");
		System.out.println(user);
		
		
	}
	
	@Test
	public void testStringUtils() {
		System.out.println(String.valueOf(null));
		String str = "a,b,c";
		String delimiters= ",";
		String[] attr = StringUtils.tokenizeToStringArray(str, delimiters);
		//这个作用就是把str按照delimiters分开，每个元素trim
		System.out.println(Arrays.toString(attr));
	}
}
