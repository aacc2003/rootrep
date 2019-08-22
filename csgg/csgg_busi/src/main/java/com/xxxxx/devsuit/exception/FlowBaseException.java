package com.xxxxx.devsuit.exception;

/**
 * 流程容器异常基类
 * 
 * 定义为容器启动时候发生的异常，不需要错误码信息，故直接继承RuntimeException
 * 
 * @author wl
 *
 */
public class FlowBaseException extends RuntimeException {

	public FlowBaseException() {super();}
	
	public FlowBaseException(String msg) {
		super(msg);
	}
	
	public FlowBaseException(Throwable e) {
		super(e);
	}
	
	public FlowBaseException(String msg, Exception e) {
		super(msg, e);
	}
	
}
