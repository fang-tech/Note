package com.func.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@SpringBootApplication
@Controller
@ResponseBody
public class Sb3002SpringInitializrApplication {

	@Bean
	public Date getNowDate(){
		return new Date();
	}
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Sb3002SpringInitializrApplication.class, args);
		Date dateBean1 = applicationContext.getBean(Date.class);
		System.out.println("dateBean1 = " + dateBean1);
		Date dateBean2 = applicationContext.getBean("getNowDate", Date.class);
		System.out.println("dateBean2 = " + dateBean2);
	}

	@RequestMapping("/hello")
	public String hello(){
		return "hello world";
	}

}
