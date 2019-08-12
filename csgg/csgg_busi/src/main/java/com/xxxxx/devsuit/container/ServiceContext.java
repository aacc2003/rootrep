package com.xxxxx.devsuit.container;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.xxxxx.devsuit.domainobj.EntityObject;
import com.xxxxx.devsuit.result.StandardResult;

public class ServiceContext<ORDER, RESULT extends StandardResult> implements Serializable{

	private Timestamp currentTimestamp;
	
	private long begin;
	
	private InvokeElement invokeElement;
	
	private ORDER order;
	
	private EntityObject entityObject;
	
	private RESULT result;
	
	private Map<String, Object> attributes = new HashMap<String, Object>(); 
	
	private Logger logger;
	
	private Exception exception;
}
