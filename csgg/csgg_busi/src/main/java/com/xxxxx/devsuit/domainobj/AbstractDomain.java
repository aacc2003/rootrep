package com.xxxxx.devsuit.domainobj;

import org.springframework.beans.BeanUtils;

import com.xxxxx.devsuit.event.NotifierBus;

public class AbstractDomain implements Domain {
	
	private final String[] empty = new String[0];
	
	private NotifierBus notifierBus;

	@Override
	public <DTO> void convertFrom(DTO dto) {
		
		convertFrom(dto, empty);
	}

	@Override
	public <DTO> void convertFrom(DTO dto, String... ignore) {
		// TODO Auto-generated method stub
		BeanUtils.copyProperties(dto, this, ignore);
	}

	@Override
	public <DTO> void convertTo(DTO dto) {
		
		convertTo(dto, empty);
	}

	@Override
	public <DTO> void convertTo(DTO dto, String... ignore) {
		// TODO Auto-generated method stub
		BeanUtils.copyProperties(this, dto, ignore);
	}

	@Override
	public void publish(Object... events) {
		
		notifierBus.dispatcher(events);
	}

	public NotifierBus getNotifierBus() {
		return notifierBus;
	}

	public void setNotifierBus(NotifierBus notifierBus) {
		this.notifierBus = notifierBus;
	}

}
