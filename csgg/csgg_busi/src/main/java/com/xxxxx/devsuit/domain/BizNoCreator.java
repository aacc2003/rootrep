package com.xxxxx.devsuit.domain;

//24位长度 4bizPrefix + yymmddhhmiss + 8seq  

public interface BizNoCreator {

	String createBizNo(long seq, String bizPrefix);
	
	String createBizNo(String seqName, String bizPrefix);
	
	long getSeq(String seqName);
}
