package com.xxxxx.devsuit.container.proxy;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.transaction.PlatformTransactionManager;

import com.xxxxx.devsuit.container.InvokeElement;
import com.xxxxx.devsuit.container.InvokeService;
import com.xxxxx.devsuit.domain.DBPlugin;
import com.xxxxx.devsuit.exception.ContainerBaseException;

public class InvokeServiceProxyFactory {

	private PlatformTransactionManager transactionManager;
	
	private DBPlugin dbplugin;
	
	private Executor executor;
	
	private InvokeElement targetInvokeElement;
	
	private Set<BaseProxyFilter> proxyFilters = new HashSet<BaseProxyFilter>();
	
	private MethodInterceptor invokeServiceIntercetor = new MethodInterceptor() {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			
			return interceptor(invocation);
		}

	};
	
	public InvokeServiceProxyFactory(PlatformTransactionManager transactionManager, DBPlugin dbplugin, 
			Executor executor, InvokeElement targetInvokeElement) {
		
		this.transactionManager = transactionManager;
		this.dbplugin = dbplugin;
		this.executor = executor;
		this.targetInvokeElement = targetInvokeElement;
		
		initProxyFilters();
	}
	
	public void initProxyFilters() {
		proxyFilters.add(new AsyncProxyFilter(1, executor, targetInvokeElement));
		proxyFilters.add(new TransactionProxyFilter(2, targetInvokeElement, transactionManager));
		proxyFilters.add(new LockProxyFilter(3, targetInvokeElement, dbplugin ));
	}
	
	public InvokeService createInvokeServiceProxy() {
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(targetInvokeElement.getInvokeService());
		NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(invokeServiceIntercetor);
		advisor.setMappedName("invoke");
		factory.addAdvisor(advisor);
		
		return (InvokeService)factory.getProxy();
	}
	
	private Object interceptor(MethodInvocation invocation) {
		Object obj = null;
		try {
			MethodInvocation last = invocation;
			for (BaseProxyFilter filter : proxyFilters) {
				MethodInvocation next = last;
				last = new MethodInvocation(){

					@Override
					public Object[] getArguments() {
						
						return invocation.getArguments();
					}

					@Override
					public Object proceed() throws Throwable {
						
						return filter.proceed(next);
					}

					@Override
					public Object getThis() {
						
						return invocation.getThis();
					}

					@Override
					public AccessibleObject getStaticPart() {
						
						return invocation.getStaticPart();
					}

					@Override
					public Method getMethod() {
						
						return invocation.getMethod();
					}
					
				};
			}
			
			obj = last.proceed();
		} catch(Throwable e) {
			throw new ContainerBaseException(e);
		}
		
		return obj;
	}
}
