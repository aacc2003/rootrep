package com.xxxxx.devsuit.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.xxxxx.devsuit.exception.ContainerBaseException;

public class NotifierBus implements Notifier {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	private Dispatcher dispacher;
	
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
		
		dispacher = new Dispatcher(executor);
	} 

	@Override
	public <L> void register(L listener) {
		
		if (null == listener) {
			return;
		}
		
		proceed(listener, new ProceedCallback(){

			@Override
			void doProceed(Class<?>[] eventTypes, Method method) {
				SubscriberWrapper subscriberWrapper = SubscriberWrapper.newInstance(listener, method); 
				Subscribe subscribe = method.getAnnotation(Subscribe.class);
				
				subscriberWrapper.setAsync(subscribe.isAsync());
				subscriberWrapper.setListener(listener);
				subscriberWrapper.setPriority(subscribe.priority());
				
				List<SubscriberWrapper> subscribersTmp = new ArrayList<>();
				subscribersTmp.add(subscriberWrapper);
				
				String key = getKey(eventTypes);
				List<SubscriberWrapper> subscribers = cache.get(key);
				if (null != subscribers) {
					subscribers.addAll(subscribersTmp);
				} else {
					cache.put(key, subscribersTmp);
				}
				
				Collections.sort(cache.get(key));
			}
			
		}) ;
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
			throw new ContainerBaseException(String.format("注册监听器过程中出现错误，target=%s.", listener), e);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private Class<?>[] checkMethod(Method method) {
		Class<?>[] paramTypes = method.getParameterTypes();
		for (Class<?> paramType : paramTypes) {
			if (paramType.isArray()) {
				throw new ContainerBaseException(String.format("监听事件不支持数组类型,请使用集合,method=%s",
						method.getDeclaringClass().getName() + "#" + method.getName()));
			}
		}
		
		Class<?> returnType = method.getReturnType();
		if (returnType != void.class) {
			throw new ContainerBaseException(String.format("监听事件非法返回值returnType=%s", returnType.getName()));
		}
		
		return paramTypes;
	}
	
	private Class<?>[] extraEventType(Method method) {
		Class<?>[] paramTypes = method.getParameterTypes();
		Class<?>[] eventTypes ;
		if (paramTypes.length == 0) {
			eventTypes = new Class[] {NoneEvent.class};
		} else {
			eventTypes = paramTypes;
		}
		
		eventTypes = typeConvert(eventTypes);
		
		return eventTypes;
	}
	
	private Class<?>[] typeConvert(Class<?>[] eventTypes) {
		
		Class<?>[] types = new Class<?>[eventTypes.length];
		
		for (int i = 0, j = types.length; i < j; i++) {
			Class<?> eventType = eventTypes[i];
			if (eventType.isPrimitive()) {
				if (eventType == int.class) {
					types[i] = Integer.class;
				} else if (eventType == byte.class) {
					types[i] = Byte.class;
				} else if (eventType == short.class) {
					types[i] = Short.class;
				} else if (eventType == long.class) {
					types[i] = Long.class;
				} else if (eventType == char.class) {
					types[i] = Character.class;
				} else if (eventType == float.class) {
					types[i] = Float.class;
				} else if (eventType == double.class) {
					types[i] = Double.class;
				} else if (eventType == boolean.class) {
					types[i] = Boolean.class;
				}
			} else {
				types[i] = eventType;
			}
		}
		
		return types;
	}
	
	private String getKey(Class<?>[] eventTypes) {
		
		StringBuilder key = new StringBuilder();
		for (int i = 0, j = eventTypes.length; i < j; i++) {
			if (i != 0) {
				key.append(",");
			}
			key.append(eventTypes[i].getName());
		}
		return key.toString();
	}
	
	private String getKey(Object[] events) {
		StringBuilder key = new StringBuilder();
		for (int i = 0, j = events.length; i < j; i++) {
			if (i != 0) {
				key.append(",");
			}
			key.append(events[i].getClass().getName());
		}
		return key.toString();
	}

	@Override
	public <L> void unregister(L listener) {
		
		if (listener == null) {
			return;
		}
		
		proceed(listener, new ProceedCallback(){

			@Override
			void doProceed(Class<?>[] eventTypes, Method method) {
				
				String key = getKey(eventTypes);
				List<SubscriberWrapper> subscribers = cache.get(key);
				if (!CollectionUtils.isEmpty(subscribers)) {
					Iterator<SubscriberWrapper>  it = subscribers.iterator();
					while (it.hasNext()) {
						SubscriberWrapper subscriberWrapper = it.next();
						if (subscriberWrapper.equals(listener)) {
							it.remove();
						}
					}
				}
			}
			
		});
	}

	@Override
	public boolean dispatcher(Object... events) {
		try {
			lock.readLock().lock();
			String key = getKey(events);
			List<SubscriberWrapper> subscribers = cache.get(key);
			if (CollectionUtils.isEmpty(subscribers)) {
				logger.warn("不存在的事件注册eventTypes = {}", key);
				return false;
			}
			
			for (SubscriberWrapper subscriber : subscribers) {
				dispacher.equals(new Dispatcher.DispatcherTask(subscriber, events));
			}
		} catch (Exception e) {
			throw new ContainerBaseException(e);
		} finally {
			lock.readLock().unlock();
		}
		return false;
	}

	private static abstract class ProceedCallback {
		
		abstract void doProceed(Class<?>[] eventTypes, Method method);
	}
}
