package com.xxxxx.devsuit.container;

import org.springframework.transaction.TransactionDefinition;

public @interface Invoker {

	//服务名
	String serviceName();
	
	//日志
	String logName();
	
	//实体类型
	Class<?> entityType() default Void.class;
	
	//是否使用spring注入bean
	boolean isEntityInjectSpringBeans();
	
	//是否异步
	boolean isAsync() default false;
	
	TransactionAttribute transactionAttribute() default @TransactionAttribute();
	public @interface TransactionAttribute {
		boolean isTx() default true;
		
		int propagation() default TransactionDefinition.PROPAGATION_REQUIRED;
		
		int isolation() default  TransactionDefinition.ISOLATION_DEFAULT;
		
		int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;
		
		boolean isReadOnly() default false;
		
		Class<? extends Exception>[] notRollbackFor() default {} ;
	}
	
	SerialLock lock() default @SerialLock();
	public @interface SerialLock {
		boolean isLock() default false;
		
		boolean isNowaiteLock() default false;
//		
//		String policy() default "";
//		
//		String module() default "";
		
		String lockName() default "";
	}
}
