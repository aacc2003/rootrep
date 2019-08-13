package com.xxxxx.devsuit.container;

public class InvokeElement {

	private String serviceName;
	
	private String logName;
	
	private Class entity;
	
	private String result;
	
	private boolean isAsync = false;
	
	private boolean isEntityInjectSpringBeans = false;
	
	private InvokeService invokeService;

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
}
