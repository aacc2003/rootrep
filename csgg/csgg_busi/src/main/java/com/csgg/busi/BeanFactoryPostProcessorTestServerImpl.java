package com.csgg.busi;

import java.beans.PropertyDescriptor;

//import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.csgg.service.api.BeanFactoryPostProcessorTestServer;
import com.csgg.service.api.TestServer;

/**
 * Hello world!
 *
 */
//@WebService(endpointInterface = "com.csgg.service.api.TestServer")
@Service(version="1.0", interfaceClass = BeanFactoryPostProcessorTestServer.class)
@Component("testBeanFactoryPostProcessor2")
public class BeanFactoryPostProcessorTestServerImpl implements BeanFactoryPostProcessorTestServer, 
	BeanDefinitionRegistryPostProcessor
{
	Logger logger = LoggerFactory.getLogger("CSGG");
	ApplicationContext applicationContext;
	ConfigurableListableBeanFactory beanFactory;
	Environment environment;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String testBeanFactoryPostProcessor2(String p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// TODO Auto-generated method stub
		
	}

}
