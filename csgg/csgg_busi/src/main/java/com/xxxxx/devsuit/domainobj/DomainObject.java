package com.xxxxx.devsuit.domainobj;

//import org.mybatis.spring.SqlSessionTemplate;

public class DomainObject extends AbstractDomain implements DOBaseAction {
	
//	TODO
//	private DomainFactory domainFactory;
//	TODO
//	private SqlSessionTemplate sqlSessionTemplate;

	private String insert;
	
	private String delete;
	
	private String update;
	
	private boolean isSqlBinderSupport = false;

	@Override
	public Object insert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T, R> R load(T key, String queryId) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	public DomainFactory getDomainFactory() {
//		return domainFactory;
//	}
//
//	public void setDomainFactory(DomainFactory domainFactory) {
//		this.domainFactory = domainFactory;
//	}
//
//	public SqlSessionTemplate getSqlSessionTemplate() {
//		return sqlSessionTemplate;
//	}
//
//	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//		this.sqlSessionTemplate = sqlSessionTemplate;
//	}

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
