package com.csgg.busi.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("tvProductFactory")
public class TVProductFactory implements FactoryBean<TV> {
	
	TV tv= new TV();

	@Override
	public TV getObject() throws Exception {
		
		return tv;
	}

	@Override
	public Class<?> getObjectType() {
		
		return TV.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
