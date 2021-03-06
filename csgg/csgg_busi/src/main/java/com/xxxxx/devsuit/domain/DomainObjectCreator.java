package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;

import com.xxxxx.devsuit.domainobj.DomainObject;
import com.xxxxx.devsuit.event.NotifierBus;

public abstract class DomainObjectCreator {

	protected SqlSessionTemplate sqlSessionTemplate;
	
	protected BizNoCreator bizNoCreator;
	
	protected NotifierBus notifierBus;

	protected DomainFactory domainFactory;
	
	public DomainObjectCreator(SqlSessionTemplate sqlSessionTemplate, BizNoCreator bizNoCreator,
			NotifierBus notifierBus, DomainFactory domainFactory) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.bizNoCreator = bizNoCreator;
		this.notifierBus = notifierBus;
		this.domainFactory = domainFactory;
	}
	
	public abstract <C extends DomainObject> C create(Class<C> clazz);
	
	// 此方法会用得比较多
	public abstract void refresh(DomainObject domainObject) ;
}
