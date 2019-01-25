package fankun.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc //需要使用这个注解
public class BootWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootWebApplication.class, args);
	}

}

