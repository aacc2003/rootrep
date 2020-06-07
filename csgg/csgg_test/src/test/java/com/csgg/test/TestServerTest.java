
package com.csgg.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.csgg.service.api.TestServer;
import com.csgg.test.base.TestBase;

/**
 * test
 * @author Administrator
 *
 */
public class TestServerTest extends TestBase {
//public class TestServerTest {

	@Resource(name="testServer")
	@Reference(version="1.0")
	private TestServer	testR;
	
	@Test
	public void testTestS() {
		String p = "khd";
		
		String result = testR.testS(p);
//		Assert.assertTrue(result.isSuccess());
		System.out.println(result);
	}
	
	enum Key {
		A, B, C, D
	}
	
	static Map<Key, String> map = new HashMap<Key, String>();
	static {
	map.put(Key.A, "a");
	map.put(Key.B, "b");
	map.put(Key.C, "c");
	map.put(Key.D, "d");
	}
	
	
	public static void main(String[] args) {
//		new TestServerTest().testTestS();
		
		String str = "qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ"
				+"qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ"
				+"qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ"
				+"qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ"
				+"qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ"
				+"qwertyuioplkjhgfdsazxcvbnm09876543211qazxsw23edcvfr45tgbnhy67ujm,ki89ol.;p0!QUJJDYFGWKKCMNCGDJJDHFUHDNDJEIKJ";
		
		List<String> arrayList = new ArrayList<String>();
//		for  (int i=0; i<1000000; i++) {
		int i=0;
		while (true) {
			
				System.out.println("---"+i);
				arrayList.add(str+"---"+i);//占用内存
				new TestServerTest(); // 垃圾对象
			i++;
			try {
				Thread.currentThread().sleep(1L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
