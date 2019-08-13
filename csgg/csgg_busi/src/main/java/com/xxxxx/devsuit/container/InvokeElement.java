package com.xxxxx.devsuit.container;

import com.xxxxx.devsuit.container.Invoker.SerialLock;
import com.xxxxx.devsuit.container.Invoker.TransactionAttribute;
import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.result.StandardResult;

public abstract class InvokeElement {

	private String serviceName;
	
	private String logName;
	
	private Class entity;
	
	private String result;
	
	private boolean isAsync = false;
	
	private boolean isEntityInjectSpringBeans = false;
	
	private InvokeService invokeService;
	
	private Invoker.SerialLock serialLock;
	
	private Invoker.TransactionAttribute transactionAttribute;
	
	public InvokeElement(String serviceName, String logName, Class entity, String result, boolean isAsync,
			boolean isEntityInjectSpringBeans, InvokeService invokeService, SerialLock serialLock,
			TransactionAttribute transactionAttribute) {

		this.serviceName = serviceName;
		this.logName = logName;
		this.entity = entity;
		this.result = result;
		this.isAsync = isAsync;
		this.isEntityInjectSpringBeans = isEntityInjectSpringBeans;
		this.invokeService = invokeService;
		this.serialLock = serialLock;
		this.transactionAttribute = transactionAttribute;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public Class getEntity() {
		return entity;
	}

	public void setEntity(Class entity) {
		this.entity = entity;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public boolean isEntityInjectSpringBeans() {
		return isEntityInjectSpringBeans;
	}

	public void setEntityInjectSpringBeans(boolean isEntityInjectSpringBeans) {
		this.isEntityInjectSpringBeans = isEntityInjectSpringBeans;
	}

	public InvokeService getInvokeService() {
		return invokeService;
	}

	public void setInvokeService(InvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public Invoker.SerialLock getSerialLock() {
		return serialLock;
	}

	public void setSerialLock(Invoker.SerialLock serialLock) {
		this.serialLock = serialLock;
	}

	public Invoker.TransactionAttribute getTransactionAttribute() {
		return transactionAttribute;
	}

	public void setTransactionAttribute(Invoker.TransactionAttribute transactionAttribute) {
		this.transactionAttribute = transactionAttribute;
	}
	
	public abstract EntityObject newEntityObject();
	
	public abstract StandardResult newResult();
	
}
