package com.xxxxx.devsuit.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.xxxxx.devsuit.container.event.InitEvent;
import com.xxxxx.devsuit.container.monitor.system.LogSystemListener;
import com.xxxxx.devsuit.container.monitor.system.PreparedSystemListener;
import com.xxxxx.devsuit.container.monitor.system.ThreadHolderListner;
import com.xxxxx.devsuit.domain.DBPlugin;
import com.xxxxx.devsuit.domain.DomainFactory;
import com.xxxxx.devsuit.event.NotifierBus;
import com.xxxxx.devsuit.result.StandardResult;

public class DefaultContainer implements Container, InitializingBean {
	
	private static final String INVOKE_SERVICE_SUFFIX = "InvokeService";
	
	private NotifierBus notifierBus;
	
	private DBPlugin dbPlugin;
	
//	private ExceptionMonitor exceptionMonitor;
	
	private DomainFactory domainFactory;
	
	private PlatformTransactionManager transactionManager;
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	private Map<String, InvokeElement> invokeElements = new ConcurrentHashMap<String, InvokeElement>();

	@Override
	public <ORDER, RESULT extends StandardResult> RESULT execute(ORDER order, String serviceName,
			OperationContext opContext) {
		
		return null;
	}

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

	public DomainFactory getDomainFactory() {
		return domainFactory;
	}

	public void setDomainFactory(DomainFactory domainFactory) {
		this.domainFactory = domainFactory;
	}
	
}
