package com.csgg.test.int_reference;

import java.util.concurrent.atomic.AtomicInteger;

public class Int_Reference_Test {

	public static void main(String[] args) {
		int a = 0;
		int b = a;
		
		b++;
		
		System.out.print("a:"+a+", b:"+b);
		
		// cas
		AtomicInteger ato = new AtomicInteger(5);
		int c = ato.get();
		boolean x = ato.compareAndSet(c, c+1);
		System.out.print("----- c:"+c+", ato:"+ato+", x:"+x);
		
		// 给for命名   // break retry    continue retry
		retry:
			for (int i=0; i<100; i++) {
				if (i==5)
				{
					System.out.println("break retry : i:"+i);
					break retry;
				}
				System.out.println(" i:"+i);
			}
		System.out.println("out retry");
	}
}
