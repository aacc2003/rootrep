package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;

import com.xxxxx.devsuit.domainobj.AbstractDomain;
import com.xxxxx.devsuit.domainobj.DomainObject;
import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.event.NotifierBus;

public class DefaultDomainObjectCreator extends DomainObjectCreator {
	
	public DefaultDomainObjectCreator(SqlSessionTemplate sqlSessionTemplate, 
			BizNoCreator bizNoCreator, NotifierBus notifierBus, DomainFactory domainFactory) {
		
		super(sqlSessionTemplate, bizNoCreator, notifierBus, domainFactory);
	}

	@Override
	public <C extends DomainObject> C create(Class<C> clazz) {
		
		try {
			C domainObject = clazz.newInstance();
			domainObject.setSqlSessionTemplate(sqlSessionTemplate);
			domainObject.setDomainFactory(domainFactory);
			if(domainObject instanceof AbstractDomain){
	            ((AbstractDomain)domainObject).setNotifierBus(notifierBus);
	        }
	        if(domainObject instanceof EntityObject){
	            ((EntityObject)domainObject).setBizNoCreator(bizNoCreator);
	        }
	        return domainObject;
		} catch (InstantiationException e) {
			throw new  RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new  RuntimeException(e);
		}	
	}

	@Override
	public void refresh(DomainObject domainObject) {
		domainObject.setSqlSessionTemplate(sqlSessionTemplate);
		domainObject.setDomainFactory(domainFactory);
		if(domainObject instanceof AbstractDomain){
            ((AbstractDomain)domainObject).setNotifierBus(notifierBus);
        }
        if(domainObject instanceof EntityObject){
            ((EntityObject)domainObject).setBizNoCreator(bizNoCreator);
        }
	}

}
