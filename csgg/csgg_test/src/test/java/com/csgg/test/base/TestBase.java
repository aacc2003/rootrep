
package com.csgg.test.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 */
@SuppressWarnings("deprecation")
//@ContextConfiguration(locations = { "classpath:/spring/applicationContext-test-remote.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBase implements ApplicationContextAware, EnvironmentAware {
	
	protected final Logger			logger								= LoggerFactory
																			.getLogger(getClass());
	
	protected ApplicationContext applicationContext;
	protected Environment environment;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;		
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;		
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public Environment getEnvironment() {
		return environment;
	}

		
}
