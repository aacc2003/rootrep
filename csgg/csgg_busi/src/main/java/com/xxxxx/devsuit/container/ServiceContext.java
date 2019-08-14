package com.xxxxx.devsuit.container;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.result.StandardResult;

public class ServiceContext<ORDER, RESULT extends StandardResult> implements Serializable{

	private Timestamp currentTimestamp;
	
	private long begin;
	
	private InvokeElement invokeElement;
	
	private ORDER order;
	
	private EntityObject entityObject;
	
	private RESULT result;
	
	private Map<String, Object> attributes = new HashMap<String, Object>(); 
	
	private Map<String, String> activeTrace = new HashMap<>();
	
	private Logger logger;
	
	private Exception exception;
	
	private OperationContext opContext;
	
	public ServiceContext(OperationContext context, ORDER param) {
		this.opContext = context;
		this.order = param;
	}

	public Timestamp getCurrentTimestamp() {
		return currentTimestamp;
	}

	public void setCurrentTimestamp(Timestamp currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public InvokeElement getInvokeElement() {
		return invokeElement;
	}

	public void setInvokeElement(InvokeElement invokeElement) {
		this.invokeElement = invokeElement;
	}

	public ORDER getOrder() {
		return order;
	}

	public void setOrder(ORDER order) {
		this.order = order;
	}

	public EntityObject getEntityObject() {
		return entityObject;
	}

	public void setEntityObject(EntityObject entityObject) {
		this.entityObject = entityObject;
	}

	public RESULT getResult() {
		return result;
	}

	public void setResult(StandardResult result) {
		this.result = (RESULT) result;
	}

	public void putAttributes(String key, Object object) {
		Assert.notNull(object); 	
		Assert.hasText(key);
		
		attributes.put(key, object);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public <C> C getAttributesValue(String key) {
		return (C) this.attributes.get(key);
	}

	public Logger getLogger() {
		if (null == logger){
			logger = LoggerFactory.getLogger(this.getInvokeElement().getLogName());
		}
		return logger;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public OperationContext getOpContext() {
		return opContext;
	}

	public void setOpContext(OperationContext opContext) {
		this.opContext = opContext;
	}
	
	public <T extends EntityObject> T convert(String... ignore) {
		if (entityObject == null) {
			return null;
		}
		
		entityObject.convertFrom(order, ignore);
		return (T) entityObject;
	}

	public String getActiveTraceValue(String key) {
		return activeTrace.get(key);
	}
	
	public Map getActiveTrace() {
		return activeTrace;
	}

	public void putActiveTrace(String key, String value) {
		Assert.hasText(value); 	
		Assert.hasText(key);
		
		activeTrace.put(key, value);
	}
	
}
