package com.xxxxx.devsuit.container;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.util.StringUtils;
import com.xxxxx.devsuit.container.event.BeforeServiceEvent;
import com.xxxxx.devsuit.container.event.FinishServiceEvent;
import com.xxxxx.devsuit.container.event.InitEvent;
import com.xxxxx.devsuit.container.monitor.system.LogSystemListener;
import com.xxxxx.devsuit.container.monitor.system.PreparedSystemListener;
import com.xxxxx.devsuit.container.monitor.system.ThreadHolderListner;
import com.xxxxx.devsuit.domain.DBPlugin;
import com.xxxxx.devsuit.domain.DomainFactory;
import com.xxxxx.devsuit.event.NotifierBus;
import com.xxxxx.devsuit.result.StandardResult;

public class DefaultContainer implements Container, InitializingBean {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String INVOKE_SERVICE_SUFFIX = "InvokeService";
	
	private NotifierBus notifierBus;
	
	private DBPlugin dbPlugin;
	
//	private ExceptionMonitor exceptionMonitor;
	
	private DomainFactory domainFactory;
	
	private PlatformTransactionManager transactionManager;
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	private Map<String, InvokeElement> invokeElements = new ConcurrentHashMap<String, InvokeElement>();

	@Override
	public void afterPropertiesSet() throws Exception {
		
		initCheck();
		notifierInitializer();
		registerListener();
	}

	private void notifierInitializer() {
		notifierBus.dispatcher(new Object[]{new InitEvent(this)});
	}
	
	private void registerListener() {
		notifierBus.register(new LogSystemListener());
		notifierBus.register(new PreparedSystemListener());
		notifierBus.register(new ThreadHolderListner());
	}
	
	private void initCheck() {
		
//		if (null == exceptionMonitor) {
//			throw new RuntimeException("初始化container出错:ExceptionMonitor=null");
//		}
		
		notifierBus = new NotifierBus(threadPoolTaskExecutor);
		
		if (null == dbPlugin) {
			throw new RuntimeException("初始化container出错:DBPlugin=null");
		}
	}

	@Override
	public <ORDER, RESULT extends StandardResult> RESULT execute(ORDER order, String serviceName,
			OperationContext opContext) {
		
		ServiceContext<ORDER, RESULT> context = new ServiceContext<ORDER, RESULT>(opContext, order);
		try {
			
			context.setCurrentTimestamp(dbPlugin.currentTime());
			context.setBegin(System.currentTimeMillis());
			
			InvokeElement invokeElement = invokeElements.get(serviceName);
			if (null == invokeElement) {
				throw new RuntimeException("未加载到服务，服务名："+serviceName);
			}
			
			context.setInvokeElement(invokeElement);
			notifierBus.dispatcher(new BeforeServiceEvent(this, context));
			
			invokeElement.getInvokeService().before(context);
			invokeElement.getInvokeService().invoke(context);
			invokeElement.getInvokeService().after(context);
			
		} catch(Throwable e) {
			//TODO 
		} finally {
			InvokeElement invokeElement = context.getInvokeElement();
			if (null != invokeElement) {
				try {
					invokeElement.getInvokeService().end(context);
				} catch (Throwable e) {
					Logger logger = context.getLogger();
					logger.error("执行服务：{}得end()方法出错：{}", serviceName, e);
				} finally {
					notifierBus.dispatcher(new FinishServiceEvent(this, context));
				}
			}
		}
		
		return context.getResult();
	}

//  ----------------------------------------------------------------------------------
	public String getLogName(InvokeService invokeService, Invoker invoker) {
		String logName = invoker.logName();
		if (StringUtils.isEmpty(logName)) {
			String n =  invokeService.getClass().getSimpleName();
			if (n.endsWith(INVOKE_SERVICE_SUFFIX)) {
				logName = n.substring(0, n.indexOf(INVOKE_SERVICE_SUFFIX));
			} else {
				logName = n;
			}
		}
		
		return logName;
	}
	
	private void checkInvokeService(InvokeService invokeService) {
		Class<InvokeService> invokeServiceClass = (Class<InvokeService>)invokeService.getClass();
		Invoker _invoker = invokeServiceClass.getAnnotation(Invoker.class);
		if (null == _invoker) {
			throw new RuntimeException(
					String.format("InvokeService->%s配置错误,@invok注解不可为空", invokeService.getInvockServiceName()));
		}
		
		String serviceName = _invoker.serviceName();
		InvokeElement invokeElementOri = invokeElements.get(serviceName);
		if (null != invokeElementOri) {
			throw new RuntimeException(
					String.format("InvokeService->%s配置错误,服务名冲突:(invokeServiceOri:%s - serviceNameOri:%s)", 
							invokeService.getInvockServiceName(), invokeElementOri, serviceName));
		}
	}
	
	private Class getEntityClass(Class entity) {
		Class tar = null;
		if (null!=entity && Void.class!=entity && void.class!=entity) {
			tar = entity;
		}
		return tar;
	}
	
	private String getResultTypeName(Class<InvokeService> invokeServiceClass) {
		try {
			
			do {
				
				Type genericType = invokeServiceClass.getGenericSuperclass();
				if (genericType instanceof ParameterizedType) {
					Type[] types = ((ParameterizedType)genericType).getActualTypeArguments();
					if (null == types || types.length != 2) {
						throw new RuntimeException(String.format("InvockService->%s范型配置错误，泛型参数数量不为2", invokeServiceClass.getName()));
					}
					
					Type resultType = types[1];
					String typeName = null;
					
					if (resultType instanceof Class) {
						typeName = ((Class) resultType).getName();
						if (null == typeName || typeName == Void.class.getName() || typeName == void.class.getName()) {
							typeName = null;
							logger.warn("InvockService->%s范型配置警告，result类型为空", invokeServiceClass.getName());
						} else {
							typeName = ( (Class<?>) ((ParameterizedType)resultType).getRawType() ).getName();
						}
						
						return typeName;
					}
				}
				
			} while(InvokeService.class.isAssignableFrom(invokeServiceClass)) ;
			
			throw new RuntimeException(String.format("InvockService->%s范型配置错误，未找到result", invokeServiceClass.getName()));
		} catch (Exception e) {
			throw new RuntimeException(String.format("InvockService->%s范型配置错误，异常:%s", invokeServiceClass.getName(), e));
		}
	}
	
//	-----------------------------------------------------------------------------------

	public DomainFactory getDomainFactory() {
		return domainFactory;
	}

	public void setDomainFactory(DomainFactory domainFactory) {
		this.domainFactory = domainFactory;
	}

	public void setDbPlugin(DBPlugin dbPlugin) {
		this.dbPlugin = dbPlugin;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}
	
}
