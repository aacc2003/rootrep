package com.xxxxx.devsuit.domainobj;

public interface DOBaseAction {

	DomainObject insert();
	
	int update();
	
	int delete();
	
	<T, R> R load(T key, String queryId);
	
//	TODO
//	DomainFactory domainFactory();
}
