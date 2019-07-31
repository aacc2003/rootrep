package com.xxxxx.devsuit.domainobj;

public interface DOBaseAction {

	Object insert();
	
	int update();
	
	int delete();
	
	<T, R> R load(T key, String queryId);
	
//	TODO
//	DomainFactory domainFactory();
}
