package com.xxxxx.devsuit.exception;

/**
 * 业务异常
 * 
 * 用户自定义业务场景异常
 * 
 * @author wl
 *
 */
public class BusinessException extends BizBaseException {

	public BusinessException() {super();}
	
	public BusinessException(String status, String code, String description) {
		super(status, code, description);
	}
	
	public BusinessException(String status, String code, String description, String verbose) {
		super(status, code, description, verbose);
	}
	
	public BusinessException(Throwable e) {super(e);}
	
	public BusinessException(String status, String code, String description, Throwable e) {
		super(status, code, description, e);
	}
	
	public BusinessException(String status, String code, String description, String verbose, Throwable e) {
		super(status, code, description, verbose, e);
	}
}
