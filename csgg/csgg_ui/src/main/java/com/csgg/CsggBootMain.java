package com.csgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class CsggBootMain {
	
	public static void main(final String[] args) {
		ConfigurableApplicationContext  context = SpringApplication.run(CsggBootMain.class, args);
		 String str1=context.getEnvironment().getProperty("spring.dubbo.server");
		 Object o = context.getBean("TestServer");
			System.out.println(str1+"--"+o);
			
	}
	
}
