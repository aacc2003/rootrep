package com.xxxxx.devsuit.domain;

import java.sql.Timestamp;

public interface DBPlugin {

	// CURRENTTIMESTAMP  默认的 select.id
	Timestamp currentTime() ;
	
	// sql var name "seqName"
	Long nextVar(String seqName);
	
	void lock(String lockName);
	
	void lockNowait(String lockName);
}
