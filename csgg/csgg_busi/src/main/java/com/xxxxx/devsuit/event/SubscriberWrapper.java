package com.xxxxx.devsuit.event;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SubscriberWrapper implements Comparable<SubscriberWrapper> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Object listener;
	
	private boolean isAsync;
	
	private int priority;
	
	public static SubscriberWrapper newInstance(Object listner, Method method) {
		
	}
	
	public Object getListener() {
		return listener;
	}

	public void setListener(Object listener) {
		this.listener = listener;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(SubscriberWrapper o) {
		// TODO Auto-generated method stub
		return this.priority == o.priority ? 0 : (this.priority>o.priority ? 1 : -1);
	}

	public abstract void invocation(Object[] events);
}
