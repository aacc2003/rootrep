package com.xxxxx.devsuit.result;

public class StandardResult {

	/** 结果状态  {@link com.xxxxx.devsuit.enums.Status} */
	protected String status;
	
	/** 信息码 {@link com.xxxxx.devsuit.enums.Code} */
	protected String code;
	
	/** 描述 */
	protected String description;
	
	/** 具体的信息码  */
	protected String verbose;

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
