package com.xxxxx.devsuit.container.proxy;

import java.util.concurrent.Executor;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxxxx.devsuit.container.InvokeElement;

public class AsyncProxyFilter extends BaseProxyFilter {
	
	private Executor executor;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private InvokeElement invokeElement;
	
	public AsyncProxyFilter(int order, Executor executor, InvokeElement invokeElement) {
		super(order);
		this.executor = executor;
		this.invokeElement = invokeElement;
	}

	@Override
	public Object proceed(MethodInvocation methodInvocation) {
		try {
			
			if (null != executor && invokeElement.isAsync()) {
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							methodInvocation.proceed();
						} catch (Throwable e) {
							
							logger.error("service:{},异步任务执行过程中出现错误. {}", invokeElement.getServiceName(), e);
							throw new RuntimeException(e);
						}
					}
				});
			} else {
				methodInvocation.proceed();
			} 
			
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}

}
