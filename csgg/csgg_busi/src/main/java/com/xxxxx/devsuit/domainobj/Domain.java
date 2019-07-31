package com.xxxxx.devsuit.domainobj;

public interface Domain {

	<DTO> void convertFrom(DTO dto);
	
	<DTO> void convertFrom(DTO dto, String... ignore);
	
	<DTO> void convertTo(DTO dto);
	
	<DTO> void convertTo(DTO dto, String... ignore);
	
	void publish(Object... events);
}
