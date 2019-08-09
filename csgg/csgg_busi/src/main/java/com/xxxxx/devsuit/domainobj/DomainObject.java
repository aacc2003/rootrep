package com.xxxxx.devsuit.domainobj;

import org.springframework.beans.factory.annotation.Autowired;

import com.xxxxx.devsuit.domain.DomainFactory;

import org.mybatis.spring.SqlSessionTemplate;

public class DomainObject extends AbstractDomain implements DOBaseAction {
	

	private DomainFactory domainFactory;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	private String insert;
	
	private String delete;
	
	private String update;
	
	private boolean isSqlBinderSupport = true;

	@Override
	public DomainObject insert() {
		
		sqlSessionTemplate.insert(insert, this);
		return this;
	}

	@Override
	public int update() {
		
		return sqlSessionTemplate.update(update, this);
	}

	@Override
	public int delete() {
		
		return sqlSessionTemplate.delete(delete, this);
	}

	@Override
	public <T, R> R load(T key, String queryId) {
		
		return sqlSessionTemplate.selectOne(queryId, key);
	}

	public DomainFactory getDomainFactory() {
		return domainFactory;
	}

	public void setDomainFactory(DomainFactory domainFactory) {
		this.domainFactory = domainFactory;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public boolean isSqlBinderSupport() {
		return isSqlBinderSupport;
	}

	public void setSqlBinderSupport(boolean isSqlBinderSupport) {
		this.isSqlBinderSupport = isSqlBinderSupport;
	}

//	TODO
//	@Override
//	public DomainFactory domainFactory() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
