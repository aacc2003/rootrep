package com.csgg.appconfig;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ImportResource("classpath*:spring/rmq-context.xml")
public class MainConfiguration {

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory c = new TomcatServletWebServerFactory();
		c.setPort(9703);
		return c;
	}
}
