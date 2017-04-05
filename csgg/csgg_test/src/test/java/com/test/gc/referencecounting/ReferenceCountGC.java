package com.test.gc.referencecounting;

/**
 * 
 * testGC()方法执行后，objA和objB会不会被GC呢？
 * @author wanglei
 *
 */
public class ReferenceCountGC {

	public Object instance = null;
	private static final int _1MB = 1024*1024;
	
	// 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过
	private byte[] bigSize = new byte[2*_1MB];
	
	public static void testGC() {
		ReferenceCountGC objA = new ReferenceCountGC();
		ReferenceCountGC objB = new ReferenceCountGC();
		objA.instance = objB;
		objB.instance = objA;
		
		objA = null;
		objB = null;
		
		// objA 和 objB能被回收掉吗？
		System.gc();
	}

	public static void main(String[] args) {
		ReferenceCountGC.testGC();
	}
}
