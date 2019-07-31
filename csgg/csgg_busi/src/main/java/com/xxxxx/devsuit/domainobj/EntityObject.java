package com.xxxxx.devsuit.domainobj;

import java.util.Date;

public abstract class EntityObject extends DomainObjectValidator {

	private long identity;
	
	private String bizNo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
//	TODO
//	private InternalSeqCreator internalSeqCreator;
	
	public void generateIdentity() {
//		TODO
		identity = 123;
	}
	
	public String generateBizNo() {
//		TODO
		bizNo = "xxx";
		return bizNo;
	}
	
	public abstract void referenceTo(AggregateRoot ref);
//	{ref.addXXXEntity(this);}

	public long getIdentity() {
		return identity;
	}

	public void setIdentity(long identity) {
		this.identity = identity;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

//	public InternalSeqCreator getInternalSeqCreator() {
//		return internalSeqCreator;
//	}
//
//	public void setInternalSeqCreator(InternalSeqCreator internalSeqCreator) {
//		this.internalSeqCreator = internalSeqCreator;
//	}
	
}
