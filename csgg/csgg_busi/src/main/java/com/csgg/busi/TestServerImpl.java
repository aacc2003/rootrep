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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.csgg.service.api.TestServer;

/**
 * Hello world!
 *
 */
//@WebService(endpointInterface = "com.csgg.service.api.TestServer")
@Service(version="1.0", interfaceClass = TestServer.class)
@Component("testS")
public class TestServerImpl implements TestServer, BeanFactoryPostProcessor, BeanPostProcessor,
	InstantiationAwareBeanPostProcessor, BeanNameAware, InitializingBean, DisposableBean, 
	BeanDefinitionRegistryPostProcessor,
	ApplicationContextAware
{
	Logger logger = LoggerFactory.getLogger("CSGG");
	ApplicationContext applicationContext;
	ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public String testS(String p) {
		logger.info("xxx kaishi{}", p);
		logger.info("jieshu   {}", p);
		return p+"-test";
	}

	@Override
	public void destroy() throws Exception {
		logger.info("---------DisposableBean#destroy");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("---------InitializingBean#afterPropertiesSet");
	}

	@Override
	public void setBeanName(String name) {
		logger.info("---------BeanNameAware#setBeanName---"+name);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("---------BeanFactoryPostProcessor#postProcessBeanFactory");
		this.beanFactory = beanFactory;
	}
    
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		logger.info("---------BeanPostProcessor#postProcessBeforeInitialization---"+beanName);
		return bean;
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		logger.info("---------BeanPostProcessor#postProcessAfterInitialization---"+beanName);
		return bean;
	}
	
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		logger.info("---------InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation---"+beanName);
		return null;
	}
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		logger.info("---------InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation---"+beanName);
		return true;
	}
	@Override
	public PropertyValues postProcessPropertyValues(
			PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		logger.info("---------InstantiationAwareBeanPostProcessor#postProcessPropertyValues---"+beanName);
		return pvs;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.info("---------ApplicationContextAware#applicationContext");
		this.applicationContext = applicationContext;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		logger.info("---------BeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry");
	}

}
