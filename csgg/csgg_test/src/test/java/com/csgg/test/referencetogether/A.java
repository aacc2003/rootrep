package com.csgg.test.referencetogether;

public class A {

	private String id;
	
	private B b;
	
	public A(String id) {
		this.id=id;
		this.b=new B("bb", this);
	}
	
	public void say() {
		System.out.println(id);
	}
	
	public static void main(String[] args) {
		
		A a = new A("aa");
		a.say();
		a.b.say();
	}
}
