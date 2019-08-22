package com.xxxxx.devsuit.exception;

import com.xxxxx.devsuit.enums.Code;
import com.xxxxx.devsuit.enums.Status;

/**
 * 业务异常
 * 
 * 作用于程序bug。 | 调用远程节点“超时”。
 *  这些场景不能返回给调用者失败，“对于超时，业务远程系统已经成功了”，“对于bug，修改后调用者再发起也会成功”
 * 
 * 通常这类场景，需要将之前执行过的业务提交（数据库commit），等待补偿流程拉起、调用者重试。
 * 
 * @author wl
 *
 */
public class UnkownException extends BizBaseException {

	public UnkownException() {super();}
	
	public UnkownException(String status, String code, String description) {
		super(Status.UNKOWN.code(), Code.ERROR_CODE_UNKOWN, description);
	}
	
	public UnkownException(String status, String code, String description, String verbose) {
		super(Status.UNKOWN.code(), Code.ERROR_CODE_UNKOWN, description, verbose);
	}
}
