package com.xxxxx.devsuit.container.monitor.system;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;

import com.xxxxx.devsuit.container.ServiceContext;
import com.xxxxx.devsuit.container.event.FinishServiceEvent;
import com.xxxxx.devsuit.container.event.InitEvent;
import com.xxxxx.devsuit.container.event.BeforeServiceEvent;
import com.xxxxx.devsuit.event.Subscribe;

public class LogSystemListener {

	@Subscribe(priority=1, isAsync=false)
	public void startLog(BeforeServiceEvent event) {
		
		ServiceContext<?, ?> serviceContext = event.value();
		Logger logger = serviceContext.getLogger();
		
		if (logger.isInfoEnabled()) {
			logger.info("收到{},系统时间(数据库){},操作[request={},operationContext={}", serviceContext.getInvokeElement()
					.getServiceName(), serviceContext.getCurrentTimestamp(), serviceContext.getOrder(),
					serviceContext.getOpContext());
		}
	}
	
	@Subscribe(priority=2, isAsync=false)
	public void finishLog(FinishServiceEvent event) {
		
		ServiceContext<?, ?> serviceContext = event.value();
		Logger logger = serviceContext.getLogger();
		
		if (logger.isInfoEnabled()) {

			logger.info("处理【{}】完成![param={}###result={}]，服务耗时：{}",new Object[] { serviceContext.getInvokeElement().getServiceName(), 
					serviceContext.getOrder(), serviceContext.getResult() ,System.currentTimeMillis() - serviceContext.getBegin()});
		}
	}
	
	@Subscribe(priority=3, isAsync=false)
	public void traceLog(FinishServiceEvent event) {
		
		ServiceContext<?, ?> serviceContext = event.value();
		Logger logger = serviceContext.getLogger();
		Map<String, String> runTrace = serviceContext.getActiveTrace();
		if (logger.isInfoEnabled()) {
			logger.info("\t 系统轨迹");
				if (null!=runTrace && runTrace.size()>0) {
					Iterator<Entry<String, String>> it = runTrace.entrySet().iterator();
					for (;;it.hasNext()) {
						Entry<String, String> runTraceItem = it.next();
						logger.info("\t{} --- {}", runTraceItem.getKey(), runTraceItem.getValue());
					}
				}
		}
	}
	
	@Subscribe(isAsync = false)
	public void initialize(InitEvent event) {
		
	}
}
