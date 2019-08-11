package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xxxxx.devsuit.domainobj.DomainObject;
import com.xxxxx.devsuit.event.NotifierBus;

public class DomainFactory implements ApplicationContextAware {
	
	protected static final Logger logger = LoggerFactory.getLogger(DomainFactory.class);
	
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	private NotifierBus notifierBus;
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private BizNoCreator bizNoCreator;
	
	private DomainObjectCreator  domainObjectCreator;
	
	public DomainFactory(NotifierBus notifierBus, SqlSessionTemplate sqlSessionTemplate, 
			BizNoCreator bizNoCreator) {
		if (null == sqlSessionTemplate) {
			throw new RuntimeException("初始化DomainFactory出错SqlSessionTemplate不可为空");
		}
		if (null == bizNoCreator) {
			throw new RuntimeException("初始化DomainFactory出错BizNoCreator不可为空");
		}
		this.notifierBus = notifierBus;
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.bizNoCreator = bizNoCreator;
		this.domainObjectCreator = create();
	}
	
	private DomainObjectCreator create() {
		DomainObjectCreator domainObjectCreator = new DefaultDomainObjectCreator(sqlSessionTemplate,
				bizNoCreator, notifierBus, this);
		return domainObjectCreator;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
	}
	
	public <L> void registerListener(L listener){
		notifierBus.register(listener);
	}

	public <C extends DomainObject> C newInstance(Class<C> clazz) {
		return newInstance(clazz, false);
	}
	
	public <C extends DomainObject> C newInstance(Class<C> clazz, boolean isAutowire) {
		DomainObject obj = domainObjectCreator.create(clazz);
		if (isAutowire) {
			autowireCapableBeanFactory.autowireBeanProperties(obj, 
					AutowireCapableBeanFactory.AUTOWIRE_NO, false);
			autowireCapableBeanFactory.initializeBean(obj, "domainObject");
		}
		
		return (C) obj;
	}
	
	public void refresh(DomainObject domainObject, boolean isAutowire) {
		domainObjectCreator.refresh(domainObject);
		if (isAutowire) {
			autowireCapableBeanFactory.autowireBeanProperties(domainObject, 
					AutowireCapableBeanFactory.AUTOWIRE_NO, false);
			autowireCapableBeanFactory.initializeBean(domainObject, "domainObject");
		}
	}
}
