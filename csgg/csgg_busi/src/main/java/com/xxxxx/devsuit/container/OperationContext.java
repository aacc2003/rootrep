package com.xxxxx.devsuit.container;

import java.io.Serializable;

public class OperationContext implements Serializable {
	/** 操作员ID */
	private String operationId;
	
	/** 操作员姓名 */
	private String operationName;
	
	/** 操作员IP */
	private String operationIp;
	
	/** 操作类型 */
	private String operationType;
	
	/** 业务类型 */
	private String businessType;
}
