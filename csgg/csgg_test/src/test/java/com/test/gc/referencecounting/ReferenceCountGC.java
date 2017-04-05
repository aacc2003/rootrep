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

	public static void main(String[] args) {
		ReferenceCountGC.testGC();
	}
}
