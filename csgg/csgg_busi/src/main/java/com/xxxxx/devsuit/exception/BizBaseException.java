package com.xxxxx.devsuit.exception;

import org.springframework.beans.BeanUtils;

import com.xxxxx.devsuit.result.StandardResult;

/**
 * 属性为{@link com.xxxxx.devsuit.result.StandardResult}得属性映射。
 * 
 * 异常体系是基于业务。对于容器启动时候的异常，使用RuntimeException
 * 
 * @author wl
 *
 */
public class BizBaseException extends RuntimeException {

	/** 结果状态  {@link com.xxxxx.devsuit.enums.Status} */
	protected String status;
	
	/** 信息码 {@link com.xxxxx.devsuit.enums.Code} */
	protected String code;
	
	/** 描述 */
	protected String description;
	
	/** 具体的信息码  */
	protected String verbose;
	
	public BizBaseException(){super();}
	
	public BizBaseException(String status, String code, String description) {
		this(status, code, description, (String)null);
	}
	
	public BizBaseException(String status, String code, String description, String verbose) {
		super();
		this.status = status;
		this.code = code;
		this.description = description;
		this.verbose = verbose;
	}
	
	// 完善异常栈
	public BizBaseException(Throwable e){super(e);}
	
	public BizBaseException(String status, String code, String description, Throwable e) {
		this(status, code, description, null, e);
	}
	
	public BizBaseException(String status, String code, String description, String verbose, Throwable e) {
		super(e);
		this.status = status;
		this.code = code;
		this.description = description;
		this.verbose = verbose;
	}
	
	//属性复制给result
	public void copyToResult(StandardResult result) {
		BeanUtils.copyProperties(this, result);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVerbose() {
		return verbose;
	}

	public void setVerbose(String verbose) {
		this.verbose = verbose;
	}
	
}
