
package com.csgg.test;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;

import com.csgg.service.api.TestServer;
import com.csgg.test.base.TestRemoteBase;

/**
 * @author Administrator
 *
 */
public class TestServerTest extends TestRemoteBase {
//public class TestServerTest {

	@Resource(name="testServerCxf")
	private TestServer	testR;
	
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
