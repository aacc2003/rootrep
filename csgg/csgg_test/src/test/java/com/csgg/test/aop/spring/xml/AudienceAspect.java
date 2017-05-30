package com.csgg.test.aop.spring.xml;

public class AudienceAspect {

	public void takeSeats() {
		System.out.println("the audience is taking their seats");
	}
	
	public void turnOffCellPhones() {
		System.out.println("the audience is turning off their cellphone");
	}
	
	public void applaud() {
		System.out.println("clap clap clap");
	}
	
	public void demandRefund() {
		System.out.println("Boo! we want our money back");
	}
}
