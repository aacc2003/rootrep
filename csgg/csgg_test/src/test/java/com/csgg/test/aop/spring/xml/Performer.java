package com.csgg.test.aop.spring.xml;

public class Performer {

//	public void perform(String performerSound) {
//		System.out.println("performing performing performerSound:"+performerSound);
//	}	
	public void perform(String performerSound, String audienceSound) {
		System.out.println("performing performing performerSound:"+performerSound+"\t audienceSound:"+audienceSound);
	}
}
