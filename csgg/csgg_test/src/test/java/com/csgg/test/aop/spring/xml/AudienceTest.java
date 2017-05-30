package com.csgg.test.aop.spring.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AudienceTest {

	public static void main(String[] args) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"D:/programmes/workspace_self/rootrep/csgg/csgg_test/src/main/resources/spring/test.aop.spring.xml");
		ApplicationContext ctx = new FileSystemXmlApplicationContext("test.aop.spring.xml");
		Performer p = ctx.getBean("performer", Performer.class);
		p.perform();
	}
}
