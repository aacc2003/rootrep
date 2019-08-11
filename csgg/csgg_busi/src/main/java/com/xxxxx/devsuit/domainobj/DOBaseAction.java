package com.xxxxx.devsuit.domainobj;

import com.xxxxx.devsuit.domain.DomainFactory;

public interface DOBaseAction {

	DomainObject insert();
	
	int update();
	
	int delete();
	
	<T, R> R load(T key, String queryId);
	
	DomainFactory domainFactory();
}
