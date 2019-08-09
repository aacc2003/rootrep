package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;

import com.xxxxx.devsuit.domainobj.AbstractDomain;
import com.xxxxx.devsuit.domainobj.AggregateRoot;
import com.xxxxx.devsuit.domainobj.DomainObject;
import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.event.NotifierBus;

public class DefaultDomainObjectCreator extends DomainObjectCreator {
	
	public DefaultDomainObjectCreator(SqlSessionTemplate sqlSessionTemplate, Class domainObjectType,
			BizNoCreator bizNoCreator, NotifierBus notifierBus, DomainFactory domainFactory) {
		
		super(sqlSessionTemplate, domainObjectType, bizNoCreator, notifierBus, domainFactory);
	}

	@Override
	public DomainObject create() {
		EntityObject domainObject = new EntityObject(){

			@Override
			public void referenceTo(AggregateRoot ref) {
				
			}};
			
			domainObject.setSqlSessionTemplate(sqlSessionTemplate);
			domainObject.setDomainFactory(domainFactory);
			if(domainObject instanceof AbstractDomain){
	            ((AbstractDomain)domainObject).setNotifierBus(notifierBus);
	        }
	        if(domainObject instanceof EntityObject){
	            ((EntityObject)domainObject).setBizNoCreator(bizNoCreator);
	        }
		return domainObject;
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
