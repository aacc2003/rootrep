package com.csgg.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class CsggBootMain {
	
	public static void main(final String[] args) {
		 SpringApplication.run(CsggBootMain.class, args);
	}
	
}
