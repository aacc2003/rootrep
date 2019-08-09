package com.xxxxx.devsuit.domain;

import java.sql.Timestamp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.StringUtils;

public abstract class DBPluginImpl implements DBPlugin {

	protected SqlSessionTemplate sqlSessionTemplate;
	
	public DBPluginImpl(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public Timestamp currentTime() {

		return sqlSessionTemplate.selectOne("CURRENTTIMESTAMP");
	}

	@Override
	public Long nextVar(String seqName) {

		if (StringUtils.hasText(seqName)) {
			throw new RuntimeException("seqName为空");
		}
		
		return sqlSessionTemplate.selectOne("seqName", seqName);
	}

	@Override
	public abstract void lock(String lockName) ;

	@Override
	public abstract void lockNowait(String lockName) ;

}
