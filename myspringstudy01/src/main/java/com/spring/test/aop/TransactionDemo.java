package com.spring.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


public class TransactionDemo {
    
    //前置通知
    public void startTransaction(JoinPoint joinPoint){
        System.out.println("begin transaction "+joinPoint);
    }
    
    //后置通知
    public void commitTransaction(){
        System.out.println("commit transaction ");
    }
    
    //环绕通知
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("begin transaction");
        
        joinPoint.proceed();
        
        System.out.println("commit transaction");
    }
    
}