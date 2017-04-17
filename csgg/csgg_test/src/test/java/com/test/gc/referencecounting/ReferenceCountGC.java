package com.test.gc.referencecounting;

/**
 * 
 * testGC()����ִ�к�objA��objB�᲻�ᱻGC�أ�
 * @author wanglei
 *
 */
public class ReferenceCountGC {

	public Object instance = null;
	private static final int _1MB = 1024*1024;
	
	// �����Ա���Ե�Ψһ�������ռ���ڴ棬�Ա�����GC��־�п�����Ƿ񱻻��չ�
	private byte[] bigSize = new byte[2*_1MB];
	
	/**
	 * ������־
	 * 
	 * ������������������
	 */
	public static void testGC() {
		ReferenceCountGC objA = new ReferenceCountGC();
		ReferenceCountGC objB = new ReferenceCountGC();
		objA.instance = objB;
		objB.instance = objA;
		
		objA = null;
		objB = null;
		
		// objA �� objB�ܱ����յ���
		System.gc();
	}
	
	/**
	 * ջ���
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
		
		// �ݹ����ջ���
//		recursion(0L, 0L, 0L, 0L);
	}
}
