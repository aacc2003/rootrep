package com.xxxxx.devsuit.exception;

/**
 * 容器异常
 * 
 * 定义为容器启动时候发生的异常，不需要错误码信息，故直接继承RuntimeException
 * 
 * @author wl
 *
 */
public class ContainerBaseException extends RuntimeException {

	public ContainerBaseException() {super();}
	
	public ContainerBaseException(String msg) {
		super(msg);
	}
	
	public ContainerBaseException(Throwable e) {
		super(e);
	}
	
	public ContainerBaseException(String msg, Exception e) {
		super(msg, e);
	}
	
}
