package com.csgg.test.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
/**
 * 参数标记
 * @author wl
 *
 * @param <T>
 * @param <R>
 */
public class Fruit<T , R> {

	private double warters;
	
	private String name;
	
	private T t;

	public double getWarters() {
		return warters;
	}

	public void setWarters(double warters) {
		this.warters = warters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public void testApple() {
//		TypeVariable
		TypeVariable[] typeVariable = getClass().getTypeParameters();
		System.out.println("------ begine   getSuperclass ------ ");
		for (TypeVariable t : typeVariable) {
			System.out.println("getSuperclass: "+t);
		}
		System.out.println("------ end   getSuperclass ------");
		
		System.out.println("------ begine   getGenericSuperclass ------");
		Type s = getClass();
		System.out.println("class type: "+s.getTypeName());
		if (s instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) s;
			Type[] ty = pt.getActualTypeArguments();
			for (Type t : ty) {
				System.out.println("getGenericSuperclass: "+t);
			}
		}
		System.out.println("------ end   getGenericSuperclass ------");
	}
	
	public static void main(String[] args) {
		Fruit ap = new Fruit();
		ap.testApple();
	}
}
