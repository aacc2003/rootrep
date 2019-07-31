package com.xxxxx.devsuit.domainobj;

public class AbstractDomain implements Domain {
	
	private final String[] s = new String[0];
	
//	TODO
//	private Notifier notifier;

	@Override
	public <DTO> void convertFrom(DTO dto) {
		
		convertFrom(dto, s);
	}

	@Override
	public <DTO> void convertFrom(DTO dto, String... ignore) {
		// TODO Auto-generated method stub

	}

	@Override
	public <DTO> void convertTo(DTO dto) {
		
		convertTo(dto, s);
	}

	@Override
	public <DTO> void convertTo(DTO dto, String... ignore) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publish(Object... events) {
		// TODO Auto-generated method stub

	}

}
