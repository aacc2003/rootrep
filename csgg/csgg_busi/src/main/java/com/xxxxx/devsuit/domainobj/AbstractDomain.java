package com.xxxxx.devsuit.domainobj;

import org.springframework.beans.BeanUtils;

public class AbstractDomain implements Domain {
	
	private final String[] s = new String[0];
	
	private Notifier notifier;

	@Override
	public <DTO> void convertFrom(DTO dto) {
		
		convertFrom(dto, s);
	}

	@Override
	public <DTO> void convertFrom(DTO dto, String... ignore) {
		// TODO Auto-generated method stub
		BeanUtils.copyProperties(dto, this, ignore);
	}

	@Override
	public <DTO> void convertTo(DTO dto) {
		
		convertTo(dto, s);
	}

	@Override
	public <DTO> void convertTo(DTO dto, String... ignore) {
		// TODO Auto-generated method stub
		BeanUtils.copyProperties(this, dto, ignore);
	}

	@Override
	public void publish(Object... events) {
		// TODO Auto-generated method stub

	}

}
