package com.csgg.test.aop.spring.xml;

public class AudienceAspect {

	public void takeSeats() {
		System.out.println("the audience is taking their seats");
	}
	
	public void turnOffCellPhones(String performerSound, String audienceSound) {
		System.out.println("the audience is turning off their cellphone  \n\t"+performerSound+"\t"+audienceSound);
	}
	
	public void applaud() {
		System.out.println("clap clap clap");
	}
	
	public void demandRefund() {
		System.out.println("Boo! we want our money back");
	}
}
