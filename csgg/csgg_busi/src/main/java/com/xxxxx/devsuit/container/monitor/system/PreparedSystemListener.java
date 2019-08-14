package com.xxxxx.devsuit.container.monitor.system;

import com.xxxxx.devsuit.container.ServiceContext;
import com.xxxxx.devsuit.container.event.InitEvent;
import com.xxxxx.devsuit.container.event.RunServiceEvent;
import com.xxxxx.devsuit.domain.DomainFactory;
import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.enums.Code;
import com.xxxxx.devsuit.enums.Status;
import com.xxxxx.devsuit.event.Subscribe;
import com.xxxxx.devsuit.result.StandardResult;

public class PreparedSystemListener {

	@Subscribe(priority=2, isAsync=false)
	public void preparedResult(RunServiceEvent event) {
		
		try {
			ServiceContext<?, ?> context = event.value();
			StandardResult result = context.getInvokeElement().newResult();
			result.setStatus(Status.UNKOWN.getCode());
			result.setCode(Code.ERROR_CODE_UNKOWN);
			result.setDescription(Status.UNKOWN.getMessage());
			
			context.setResult(result);
		} catch (Exception e) {
			throw new RuntimeException("初始化result出错："+e);
		}
	}
	
	@Subscribe(priority=3, isAsync=false)
	public void preparedEntityObject(RunServiceEvent event) {
		try {
			ServiceContext<?, ?> context = event.value();
			EntityObject entity = context.getInvokeElement().newEntityObject();
			
			DomainFactory domainFactory = event.source().getDomainFactory();
			if (entity!=null && domainFactory !=null) {
				domainFactory.refresh(entity, context.getInvokeElement().isEntityInjectSpringBeans());
			}
			
			context.setEntityObject(entity);
		} catch (Exception e) {
			throw new RuntimeException("构建entity对象出错："+e);
		}
	}
	
	@Subscribe(isAsync = false)
	public void initialize(InitEvent event) {
		
	}
}
