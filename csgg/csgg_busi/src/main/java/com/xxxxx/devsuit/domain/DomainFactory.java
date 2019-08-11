package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xxxxx.devsuit.event.NotifierBus;

public class DomainFactory implements ApplicationContextAware {
	
	protected static final Logger logger = LoggerFactory.getLogger(DomainFactory.class);
	
	private ApplicationContext applicationContext;
	
	private NotifierBus notifierBus;
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private BizNoCreator bizNoCreator;
	
	private DomainObjectCreator  domainObjectCreator;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.applicationContext = applicationContext;
	}
	
	public <L> void registerListener(L listener){
		notifierBus.register(listener);
	}

}
