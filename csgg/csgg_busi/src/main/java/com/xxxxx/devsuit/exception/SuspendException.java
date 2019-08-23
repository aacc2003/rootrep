package com.xxxxx.devsuit.exception;

import com.xxxxx.devsuit.enums.Code;
import com.xxxxx.devsuit.enums.Status;

/**
 * 业务异常
 * 
 * 作用于程序明确结果得中间状态--“处理中”状态。 
 * 
 * 通常这类场景，需要将之前执行过的业务提交（数据库commit），等待补偿流程拉起。
 * 
 * @author wl
 *
 */
public class SuspendException extends BizBaseException {

	public SuspendException() {super();}
	
	public SuspendException(String description) {
		super(Status.PROCESSING.code(), Code.ERROR_CODE_SUPPEND, description);
	}
	
	public SuspendException(String description, String verbose) {
		super(Status.PROCESSING.code(), Code.ERROR_CODE_SUPPEND, description, verbose);
	}
	
	public SuspendException(Throwable e) {super(e);}
	
	public SuspendException(String description, Throwable e) {
		super(Status.PROCESSING.code(), Code.ERROR_CODE_SUPPEND, description, e);
	}
	
	public SuspendException(String description, String verbose, Throwable e) {
		super(Status.PROCESSING.code(), Code.ERROR_CODE_SUPPEND, description, verbose, e);
	}
}
