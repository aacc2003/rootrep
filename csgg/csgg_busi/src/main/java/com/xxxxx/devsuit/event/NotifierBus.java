package com.xxxxx.devsuit.event;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.tools.ant.taskdefs.Execute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class NotifierBus implements Notifier {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
//	private Dispatcher dispacher;
	
	private Map<String, List<SubscriberWrapper>> cache = new ConcurrentHashMap<>();
	
	public NotifierBus() {
		this(null);
	}
	
	public NotifierBus(ThreadPoolTaskExecutor threadPool) {
		Executor executor = threadPool;
		if (executor == null) {
			executor = new Executor() {
				
				@Override
				public void execute(Runnable command) {

					command.run();
				}
			};
			logger.info("通知总线线程池初始化为空，忽略异步执行..");
		} else {
			logger.info("通知总线初始化完成threadPool[core_size={},keep_alive={},max_size={}]", threadPool.getCorePoolSize(),
					threadPool.getKeepAliveSeconds(), threadPool.getMaxPoolSize());
		}
		
//		dispacher = new Dispatcher(executor);
	} 

	@Override
	public <L> void register(L listner) {
		// TODO Auto-generated method stub

	}
	
	private <L> void proceed(L listener, ProceedCallback proceedCallback) {
		try {
			lock.writeLock().lock();
			Class<L> listenerType = (Class<L>) listener.getClass();
			Method[] methods = listenerType.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Subscribe.class)) {
					checkMethod(method);
					Class<?>[] eventTypes = extraEventType(method);
					proceedCallback.doProceed(eventTypes, method);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("注册监听器过程中出现错误，target=%s.", listener), e);
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public <L> void unregister(L listner) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dispatcher(Object... events) {
		// TODO Auto-generated method stub
		return false;
	}

	private static abstract class ProceedCallback {
		
		abstract void doProceed(Class<?>[] eventTypes, Method method);
	}
}
