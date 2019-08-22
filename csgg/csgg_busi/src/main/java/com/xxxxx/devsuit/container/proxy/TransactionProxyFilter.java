package com.xxxxx.devsuit.container.proxy;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.xxxxx.devsuit.container.InvokeElement;
import com.xxxxx.devsuit.container.Invoker;
import com.xxxxx.devsuit.exception.ContainerBaseException;
import com.xxxxx.devsuit.exception.SuspendException;
import com.xxxxx.devsuit.exception.UnkownException;

public class TransactionProxyFilter extends BaseProxyFilter {
	
	private InvokeElement invokeElement;
	
	private PlatformTransactionManager transactionManager;
	
	public TransactionProxyFilter(int order, InvokeElement invokeElement, PlatformTransactionManager transactionManager) {
		super(order);
		this.invokeElement = invokeElement;
		this.transactionManager = transactionManager;
	}

	@Override
	public Object proceed(MethodInvocation methodInvocation) {
		Invoker.TransactionAttribute transactionAttribute = invokeElement.getTransactionAttribute();
		Invoker.SerialLock lock = invokeElement.getSerialLock();
		
		if (null == transactionAttribute && (null != lock && lock.isLock()) ) {
			throw new ContainerBaseException(String.format("TransactionAttribute->(%s)与SerialLock->(%s)配置冲突", transactionAttribute, lock));
		}
		
		if (null != transactionManager && ( (null!=lock && lock.isLock()) || transactionAttribute.isTx() ) ) {
			TransactionStatus status = null;
			Throwable throwable = null;
			
			try {
				TransactionDefinition definition = createTransactionDefinition(invokeElement);
				status = transactionManager.getTransaction(definition);
				methodInvocation.proceed();
			} catch(Throwable e) {
				
				throwable = e;
			} finally {
				// null未抛异常；  SuspendException挂起、处理中； UnkownException系统代码bug类
				if (null == throwable || throwable instanceof SuspendException || throwable instanceof UnkownException ) {
					transactionManager.commit(status);
				}
				
				// 忽略回滚的异常列表
				Class<? extends Exception>[] classes = transactionAttribute.notRollbackFor();
				if (null != classes) {
					for (int i=0; i<classes.length; i++) {
						if (throwable.getClass().equals(classes[i])) {
							transactionManager.commit(status);
							return null;
						}
					}
				}
			}
			
			transactionManager.rollback(status);
		}
		
		return null;
	}

	private TransactionDefinition createTransactionDefinition(InvokeElement invokeElement) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		Invoker.TransactionAttribute attribute = invokeElement.getTransactionAttribute();
		
		definition.setIsolationLevel(attribute.isolation());
		definition.setPropagationBehavior(attribute.propagation());
		definition.setReadOnly(attribute.isReadOnly());
		definition.setTimeout(attribute.timeout());
		
		return definition;
	}
}
