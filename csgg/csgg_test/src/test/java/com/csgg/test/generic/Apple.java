package com.csgg.test.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
/**
 * 明确类型
 * 
 * getClass()取得的是 Apple，没有泛型参数
 * @author wl
 *
 */
public class Apple<E> extends Fruit<String, Integer> {

	private String pinkSkin;

	public String getPinkSkin() {
		return pinkSkin;
	}

	public void setPinkSkin(String pinkSkin) {
		this.pinkSkin = pinkSkin;
	}
	
	public void testApple() {
//		TypeVariable
		System.out.println("------ begine   getSuperclass ------ ");
		TypeVariable[] typeVariable = getClass().getSuperclass().getTypeParameters(); // SuperClass
		for (TypeVariable t : typeVariable) {
			System.out.println("getSuperclass: "+t);
		}
		System.out.println("------ end   getSuperclass ------");
		
		System.out.println("\n");
		
		System.out.println("------ begine   getGenericSuperclass ------");
		Type s = getClass().getGenericSuperclass(); // SuperClass
		if (s instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) s;
			Type[] ty = pt.getActualTypeArguments();
			for (Type t : ty) {
				System.out.println("getGenericSuperclass: "+t);
			}
		}
		
		TypeVariable[] typeVariables = getClass().getTypeParameters(); // SuperClass
		for (TypeVariable t : typeVariables) {
			System.out.println("getGenericSuperclass  typeVariables: "+t);
		}
		System.out.println("------ end   getGenericSuperclass ------");
		
		System.out.println("\n");
		
		System.out.println("------ begine   this ------");
		Type th = getClass(); // this
		System.out.println("this getTypeName : "+th.getTypeName());
		if (th instanceof ParameterizedType) {
			ParameterizedType ptt = (ParameterizedType) th;
			Type[] tyt = ptt.getActualTypeArguments();
			for (Type t : tyt) {
				System.out.println("this: "+t);
			}
		}
		if (th instanceof TypeVariable) {
			System.out.println("this: TypeVariable");
		}
		System.out.println("------ end   this ------");
		
		System.out.println("\n");
		
		System.out.println("------ begine   this TypeVariable ------ ");
		TypeVariable[] typeVariableth = getClass().getTypeParameters(); // this
		for (TypeVariable t : typeVariableth) {
			System.out.println("this typeVariableth: "+t);
		}
		System.out.println("------ end   this TypeVariable ------");
	}
	
	public static void main(String[] args) {
		Apple ap = new Apple();
		ap.testApple();
	}
}
