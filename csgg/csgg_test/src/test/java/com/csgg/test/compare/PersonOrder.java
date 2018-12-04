package com.csgg.test.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonOrder implements Comparable<PersonOrder> {

	private int ordered;

	public PersonOrder() {}
	public PersonOrder(int od) {
		this.ordered = od;
	}
	
	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	@Override
	public int compareTo(PersonOrder o) {
		return Integer.compare(this.getOrdered(), o.getOrdered());
	}
	
	public static void main(String[] args) {
		List<PersonOrder> list = new ArrayList<PersonOrder>();
		
		list.add(new PersonOrder(5));
		list.add(new PersonOrder(2));
		list.add(new PersonOrder(1));
		list.add(new PersonOrder(4));
		list.add(new PersonOrder(3));
		
		list.sort(Collections.reverseOrder((s1, s2) -> s1.compareTo(s2)));
		
		System.out.println(list);
	}
}
