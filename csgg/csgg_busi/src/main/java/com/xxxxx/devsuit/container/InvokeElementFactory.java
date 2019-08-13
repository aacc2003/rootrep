package com.xxxxx.devsuit.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;

import javassist.CtClass;

public class InvokeElementFactory {

	private InvokeElementFactory() {}
	
	private static class InvokeElementFactoryHolder {
		private static InvokeElementFactory instance = new InvokeElementFactory();
	}
	
	public static InvokeElementFactory getInvokeElementFactory () {
		return InvokeElementFactoryHolder.instance;
	}
	
	public InvokeElement newInvokeElement(String serviceName, String logName,
			InvokeService invokeService, Class entityClass, String resultClass,
			boolean isAsync, Invoker.SerialLock serialLock,
			Invoker.TransactionAttribute transactionAttribute,
			boolean isEntityInjectSpringBeans) {
		
		Logger logger = LoggerFactory.getLogger(logName);
		
		com.xxxxx.devsuit.common.Compiler compiler = com.xxxxx.devsuit.common.Compiler.getInstance();
		
		CtClass ctClass = compiler.newClass(InvokeElement.class);
		
		String constructor = constructor(ctClass.getSimpleName());
		String entity = newEntityObject(entityClass, serviceName);
		String result = newResult(resultClass, serviceName);
		
		if (logger.isDebugEnabled()) {
			logger.info("{}->script \nconstruct :\n{}\nnewResult:\n{}\nnewEntity:\n{}", invokeService.getClass()
					.getName(), constructor, entity, result);
		}
		
		compiler.constructImplement(ctClass, InvokeElement.class, constructor);
		Class<?>[] paramTypes = new Class[] { String.class, String.class, InvokeService.class,
				Class.class, String.class, boolean.class, Invoker.SerialLock.class,
				Invoker.TransactionAttribute.class, boolean.class };
		Object[] params = new Object[] { serviceName, logName, invokeService, entityClass,
				resultClass, isAsync, serialLock, transactionAttribute,
				isEntityInjectSpringBeans };
		return compiler.methodWeave(ctClass, InvokeElement.class, entity).methodWeave(ctClass, InvokeElement.class, result)
				.newInstance(ctClass, paramTypes, params);
	}
	
	private String constructor(String className) {
		StringBuilder sb = new StringBuilder();
		sb.append("public ").append(className).append("(String serviceName, String logName, Class entityClass, String resultClass, boolean isAsync, boolean isEntityInjectSpringBeans, InvokeService invokeService, Invoker.SerialLock serialLock, Invoker.TransactionAttribute transactionAttribute) {\n\t")
		.append("\t supper(serviceName, logName, entityClass, resultClass, isAsync, isEntityInjectSpringBeans, invokeService, serialLock, transactionAttribute); \n\t").append("}");
		
		return sb.toString();
	}
	
	private String newEntityObject(Class entityClass, String serviceName) {
		StringBuilder sb = new StringBuilder();
		sb.append("public EntityObject newEntityObject() {\n\t ").append("\t return ");
		if (null == entityClass || "".equals(entityClass)) {
			sb.append("null; \n}");
		} else {
			sb.append("new ").append(entityClass.getName()).append("(); \n}");
		}
		
		return sb.toString();
	}
	
	private String newResult(String resultClass, String serviceName) {
		StringBuilder sb = new StringBuilder();
		sb.append("public StandardResult newResult() {\r\t").append("return ");
		if (StringUtils.isEmpty(resultClass)) {
			sb.append("null; \n}");
		} else {
			sb.append("new ").append(resultClass).append("(); \n}");
		}
		
		return sb.toString();
	}
}
