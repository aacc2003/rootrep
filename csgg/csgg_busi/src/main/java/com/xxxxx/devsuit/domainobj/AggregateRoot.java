package com.xxxxx.devsuit.domainobj;

public class AggregateRoot extends EntityObject {

	@Override
	public void referenceTo(AggregateRoot ref) {
		// TODO Auto-generated method stub
		
	}
	
	public void relationTo(EntityObject entityObject) {
		entityObject.referenceTo(this);
	}

}
