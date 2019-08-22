package com.xxxxx.devsuit.container.proxy;

import org.aopalliance.intercept.MethodInvocation;

import com.xxxxx.devsuit.container.InvokeElement;
import com.xxxxx.devsuit.container.Invoker;
import com.xxxxx.devsuit.domain.DBPlugin;
import com.xxxxx.devsuit.exception.ContainerBaseException;

public class LockProxyFilter extends BaseProxyFilter {
	
	private InvokeElement invokeElement;
	
	private DBPlugin dbPlugin;
	
	public LockProxyFilter(int order, InvokeElement invokeElement, DBPlugin dbPlugin) {
		super(order);
		this.invokeElement = invokeElement;
		this.dbPlugin = dbPlugin;
	}

	@Override
	public Object proceed(MethodInvocation methodInvocation) {
		
		try {
			Invoker.SerialLock lock = invokeElement.getSerialLock();
			
			boolean isLock = lock.isLock();
			boolean isNowait = lock.isNowaiteLock();
			String lockName = lock.lockName();
			
			if (isLock) {
				if (isNowait) {
					dbPlugin.lock(lockName);
				} else {
					dbPlugin.lockNowait(lockName);
				}
			}
			
			methodInvocation.proceed(); 
		} catch(Throwable e) {
			throw new ContainerBaseException(e);
		}
		
		return null;
	}

}
