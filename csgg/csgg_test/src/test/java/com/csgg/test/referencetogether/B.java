package com.csgg.test.referencetogether;

public class B {

	private String id;
	
	private A a;
	
	public B(String id, A a) {
		this.id=id;
		this.a=a;
	}
	
	public void say() {
		System.out.println(id);
	}
}
