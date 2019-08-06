package com.xxxxx.devsuit.event;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.CtClass;

public abstract class SubscriberWrapper implements Comparable<SubscriberWrapper> {

	private final static Logger logger = LoggerFactory.getLogger(SubscriberWrapper.class);
	
	private Object listener;
	
	private boolean isAsync;
	
	private int priority;
	
	// 组装invocation方法
	public static SubscriberWrapper newInstance(Object listener, Method method) {
		try {
			String listenerClassName = listener.getClass().getName();
			
			Class<?>[] paramTypes = method.getParameterTypes();
			StringBuilder methodDefinition = new StringBuilder();
			methodDefinition.append("public void invocation(Object[] events) {\n\t").append(listenerClassName)
				.append("listener = (").append(listenerClassName).append(") getListener(); \n\t").append("listener.")
				.append(method.getName()).append("(");
			
			//参数
			for (int i=0, j=paramTypes.length; i<j; i++) {
				if (0!=i) {
					methodDefinition.append(",");
				}
				
				Class<?> paramType = paramTypes[i];
				if (paramType.isPrimitive()) {
					if (paramType == int.class) {
						methodDefinition.append("Integer.parseInt(events[").append(i).append("].toString())");
					} else if (paramType == long.class) {
						methodDefinition.append("Long.parseLong(events[").append(i).append("].toString())");
					} else if (paramType == short.class) {
						methodDefinition.append("Short.parseShort(events[").append(i).append("].toString())");
					} else if (paramType == double.class) {
						methodDefinition.append("Double.parseDouble(events[").append(i).append("].toString())");
					} else if (paramType == char.class) {
						methodDefinition.append("((Character)events[").append(i).append("]).charValue()");
					} else if (paramType == byte.class) {
						methodDefinition.append("Byte.parseByte(events[").append(i).append("].toString())");
					} else if (paramType == float.class) {
						methodDefinition.append("Float.parseFloat(events[").append(i).append("].toString())");
					} else if (paramType == boolean.class) {
						methodDefinition.append("Boolean.parseBoolean(events[").append(i).append("].toString())");
					}
				} else {
					methodDefinition.append("(").append(paramTypes[i].getName()).append(")events[").append(i)
						.append("]");
				}
			}
			methodDefinition.append(");\n\t}");
			
			if (logger.isDebugEnabled()) {
				logger.debug("监听器[{}#{}]对应生存代码\n{}", listener.getClass().toString(), method.toString(),
						methodDefinition.toString());
			}
			
			CtClass ct = com.xxxxx.devsuit.common.Compiler.getInstance().newClass(SubscriberWrapper.class);
			return com.xxxxx.devsuit.common.Compiler.getInstance().methodWeave(ct, SubscriberWrapper.class, methodDefinition.toString())
				.newInstance(ct, null, null);
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object getListener() {
		return listener;
	}

	public void setListener(Object listener) {
		this.listener = listener;
	}

	public boolean isAsync() {
		return isAsync;
	}

	public void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(SubscriberWrapper o) {
		// TODO Auto-generated method stub
		return this.priority == o.priority ? 0 : (this.priority>o.priority ? 1 : -1);
	}

	public abstract void invocation(Object[] events);
}
