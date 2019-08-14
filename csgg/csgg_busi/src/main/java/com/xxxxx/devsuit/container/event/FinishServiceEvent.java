package com.xxxxx.devsuit.container.event;

import com.xxxxx.devsuit.container.DefaultContainer;
import com.xxxxx.devsuit.container.ServiceContext;

public class FinishServiceEvent implements ContainerEvent<DefaultContainer, ServiceContext<?, ?>> {
	
	private DefaultContainer container ;
	
	private ServiceContext<?, ?> context;
	
	public FinishServiceEvent(DefaultContainer container, ServiceContext<?, ?> context) {
		this.container = container;
		this.context = context;
	}

	@Override
	public DefaultContainer source() {
		
		return container;
	}

	@Override
	public ServiceContext<?, ?> value() {
		
		return context;
	}

}
