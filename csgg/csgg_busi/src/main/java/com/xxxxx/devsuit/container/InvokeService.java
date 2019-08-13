package com.xxxxx.devsuit.container;

import org.springframework.beans.factory.BeanNameAware;

import com.xxxxx.devsuit.result.StandardResult;

public interface InvokeService<ORDER, RESULT extends StandardResult> extends BeanNameAware {

	public void invoke(ServiceContext<ORDER, RESULT> serviceContext) ;
	
	public void before(ServiceContext<ORDER, RESULT> serviceContext);
	
	public void after(ServiceContext<ORDER, RESULT> serviceContext);
	
	public void end(ServiceContext<ORDER, RESULT> serviceContext);
}
