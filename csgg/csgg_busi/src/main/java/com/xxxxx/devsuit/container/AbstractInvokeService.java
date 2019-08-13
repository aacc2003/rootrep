package com.xxxxx.devsuit.container;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xxxxx.devsuit.result.StandardResult;

public abstract class AbstractInvokeService<ORDER, RESULT extends StandardResult> implements 
																							InvokeService<ORDER, RESULT> , 
																							InitializingBean, 
																							ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	protected String serviceName;

	@Override
	public void setBeanName(String name) {
		this.serviceName = name;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
