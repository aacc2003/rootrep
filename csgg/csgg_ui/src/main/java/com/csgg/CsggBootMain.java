package com.csgg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@EnableDubboConfiguration
@SpringBootApplication
public class CsggBootMain {
	
	public static void main(final String[] args) {
		ConfigurableApplicationContext  context = SpringApplication.run(CsggBootMain.class, args);
		 String str1=context.getEnvironment().getProperty("spring.application.name");
//		 Object o = context.getBean("testS");
		 Object o = context.getBean("tvProductFactory");
			System.out.println(o.getClass().getName()+"--------"+str1);  //结果com.csgg.busi.factorybean.TV--------csgg
			
		context.close(); //触发DisposableBean接口
			
//			context.destroy();  
	}
	
}
