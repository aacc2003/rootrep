package com.xxxxx.devsuit.container.proxy;

import org.aopalliance.intercept.MethodInvocation;

public interface ProxyFilter extends Comparable<ProxyFilter> {

	Object proceed(MethodInvocation methodInvocation);
	
	int getOrder();
}
