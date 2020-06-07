package com.csgg.busi;

//import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.csgg.service.api.TestServer;

/**
 * Hello world!
 *
 */
//@WebService(endpointInterface = "com.csgg.service.api.TestServer")
@Service(version="1.0", interfaceClass = TestServer.class)
//@Component("testnohingtS")
public class TestServerNothingImpl implements TestServer
	
{
	Logger logger = LoggerFactory.getLogger("CSGG");
	ApplicationContext applicationContext;
	ConfigurableListableBeanFactory beanFactory;
	Environment environment;
	
	@Override
	public String testS(String p) {
		logger.info("testnothing xxx kaishi{}", p);
		logger.info("testnothing jieshu   {}", p);
		return p+"-testnothing";
	}

}
