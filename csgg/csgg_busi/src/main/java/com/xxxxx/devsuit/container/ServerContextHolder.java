package com.xxxxx.devsuit.container;

import com.xxxxx.devsuit.result.StandardResult;

public class ServerContextHolder {

	public static ThreadLocal<ServiceContext> threadLocal = new ThreadLocal();
	
	public static <ORDER, RESULT extends StandardResult> ServiceContext<ORDER, RESULT> get() {
		return threadLocal.get();
	}
	
	public static void set(ServiceContext context) {
		threadLocal.set(context);
	}
	
	public static void clean() {
		threadLocal.remove();
	}
}
