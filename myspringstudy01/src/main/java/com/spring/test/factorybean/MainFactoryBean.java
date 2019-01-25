package com.spring.test.factorybean;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainFactoryBean {  
  
	//日期相减，嘚多少天
	public static void minusday() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, 2026);
    	c.set(2026, 6, 7, 0, 0, 0);;
    	Calendar c2 = Calendar.getInstance();
    	c2.set(2018, 6, 7, 0, 0, 0);
    	long l= c.getTimeInMillis()-c2.getTimeInMillis();
    	int days=new Long(l/(1000*60*60*24)).intValue();
    	System.out.println("2个日期之间相差："+days+"天。");
	}
    @SuppressWarnings("resource")  
    public static void main(String [] args){  
//    	minusday();
    	
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("factory_bean.xml");  
        Date now = (Date) appCtx.getBean("myFactoryBean");    
        System.out.println(now);    
        MyFactoryBean factoryBean = (MyFactoryBean) appCtx.getBean("&myFactoryBean");    
        factoryBean.sayName(); 
        try {
			System.out.println(factoryBean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }    
} 