package com.xxxxx.devsuit.domain;

import org.mybatis.spring.SqlSessionTemplate;

import com.xxxxx.devsuit.domainobj.DomainObject;
import com.xxxxx.devsuit.event.NotifierBus;

public abstract class DomainObjectCreator {

	protected SqlSessionTemplate sqlSessionTemplate;
	
	protected Class domainObjectType;
	
	protected BizNoCreator bizNoCreator;
	
	protected NotifierBus notifierBus;

	protected DomainFactory domainFactory;
	
	public DomainObjectCreator(SqlSessionTemplate sqlSessionTemplate, Class domainObjectType, BizNoCreator bizNoCreator,
			NotifierBus notifierBus, DomainFactory domainFactory) {
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.domainObjectType = domainObjectType;
		this.bizNoCreator = bizNoCreator;
		this.notifierBus = notifierBus;
		this.domainFactory = domainFactory;
	}
	
	public abstract DomainObject create();
	
	// 此方法会用得比较多
	public abstract void refresh(DomainObject domainObject) ;
}
