package com.xxxxx.devsuit.domainobj;

import java.util.Date;

import com.xxxxx.devsuit.domain.BizNoCreator;
import com.xxxxx.devsuit.exception.ContainerBaseException;

public abstract class EntityObject extends DomainObjectValidator {

	private long identity;
	
	private String bizNo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private BizNoCreator bizNoCreator;
	
	public void createIdentity(String seqName) {
		if (bizNoCreator == null) {
			throw new ContainerBaseException(String.format("序列号生存器bizNoCreator尚未初始化，不支持的操作..."));
		}
		
		this.identity = bizNoCreator.getSeq(seqName);
	}
	
	public String createBizNo(String seqName, boolean isOverrideIdentity, String prefix) {
		if (bizNoCreator == null) {
			throw new ContainerBaseException(String.format("序列号生存器bizNoCreator尚未初始化，不支持的操作..."));
		}
		
		long seq = bizNoCreator.getSeq(seqName);
		this.bizNo = bizNoCreator.createBizNo(seq, prefix);
		
		if (isOverrideIdentity) {
			this.identity = seq;
		}
		
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

	public BizNoCreator getBizNoCreator() {
		return bizNoCreator;
	}

	public void setBizNoCreator(BizNoCreator bizNoCreator) {
		this.bizNoCreator = bizNoCreator;
	}

}
