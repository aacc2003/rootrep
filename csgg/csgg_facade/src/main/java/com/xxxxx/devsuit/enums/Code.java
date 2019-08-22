package com.xxxxx.devsuit.enums;

public interface Code {

	/** 内部系统异常错误 == 未知错误 */
	public static final String ERROR_CODE_NEST = "comm_03_0000";
	
	/** 未知错误 */
	public static final String ERROR_CODE_UNKOWN = "comm_04_0000";
	
	/** 系统挂起 */
	public static final String ERROR_CODE_SUPPEND = "comm_02_0012";
	
	/** 请求参数检查出错 */
	public static final String ERROR_CODE_ILLEGA_PARAMETER = "comm_04_0003";
	
	/** 执行成功 */
	public static final String SUCCESS_CODE = "comm_04_0001";
	
	/** 重复请求 */
	public static final String REQUEST_REPEATED = "comm_04_0004";
}
