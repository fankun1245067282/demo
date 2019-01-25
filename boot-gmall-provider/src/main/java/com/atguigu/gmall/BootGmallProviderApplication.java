package com.atguigu.gmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication
public class BootGmallProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootGmallProviderApplication.class, args);
	}
}
