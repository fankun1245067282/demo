package com.spring.test.factorybean;

import java.util.Date;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Date>,BeanNameAware {  
    private String name;  
    public Date getObject() throws Exception {  
        return new Date();  
    }  
    @Override  
    public Class<?> getObjectType() {  
        return Date.class;  
    }  
    @Override  
    public boolean isSingleton() {  
        return false;  
    }  
    public void sayName() {  
        System.out.println("My name is " + this.name);  
    }  
    @Override  
    public void setBeanName(String name) {  
        this.name = name;  
    }  
}  