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
	
	/**
	 * 基本日志
	 * 
	 * 新生代，老生代测试
	 */
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
	
	/**
	 * 栈溢出
	 * @param a
	 * @param b
	 * @param c
	 */
	public static void recursion(long a, long b, long c, long count) {
        long e = 1, f = 2, g = 3, h = 4, i = 5, k = 6, q = 7, x = 8, y = 9, z = 10;
        count++; 
        System.out.println(count);
        recursion(a, b, c, count);
    }

	public static void main(String[] args) {
		ReferenceCountGC.testGC();
		
		// 递归测试栈溢出
//		recursion(0L, 0L, 0L, 0L);
	}
}
