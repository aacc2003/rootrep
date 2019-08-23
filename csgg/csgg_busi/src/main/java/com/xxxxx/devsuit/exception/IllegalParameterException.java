package com.xxxxx.devsuit.exception;

import com.xxxxx.devsuit.enums.Code;
import com.xxxxx.devsuit.enums.Status;

/**
 * 业务异常
 * 
 * 参数检查失败，直接返回失败
 * @author wl
 *
 */
public class IllegalParameterException extends BizBaseException {

	public IllegalParameterException() {super();}
	
	public IllegalParameterException(String description) {
		super(Status.FAIL.getCode(), Code.ERROR_CODE_ILLEGA_PARAMETER, description);
	}
	
	public IllegalParameterException(Throwable e) {super(e);}
	
	public IllegalParameterException(String description, Throwable e) {
		super(Status.FAIL.getCode(), Code.ERROR_CODE_ILLEGA_PARAMETER, description, e);
	}
}
