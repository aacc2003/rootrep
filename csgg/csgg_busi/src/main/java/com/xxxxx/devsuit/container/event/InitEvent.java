package com.xxxxx.devsuit.container.event;

import com.xxxxx.devsuit.container.DefaultContainer;

public class InitEvent implements ContainerEvent<DefaultContainer, Void> {

	private DefaultContainer defaultContainer;
	
	public InitEvent(DefaultContainer defaultContainer) {
		this.defaultContainer = defaultContainer;
	}
	
	@Override
	public DefaultContainer source() {
		
		return defaultContainer;
	}

	@Override
	public Void value() {
		
		return null;
	}

}
