
package com.csgg.test;

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

	@Resource(name="testServerCxf")
	@Reference
	private TestServer	testR;
	
	@Test
	public void testTestS() {
		String p = "khd";
		
		String result = testR.testS(p);
//		Assert.assertTrue(result.isSuccess());
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		new TestServerTest().testTestS();
	}
}
