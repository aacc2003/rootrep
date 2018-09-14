
package com.csgg.test.base;

import javax.servlet.ServletContext;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ServletContextAware;

import com.github.kevinsawicki.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@IntegrationTest("server.port:0") //  org.springframework.boot.test.IntegrationTest;   tomcat的随机启动端口    网上有个@WebIntegrationTest
@WebAppConfiguration
public class TestWebBase extends TestBase implements ServletContextAware {
	
	protected final Logger			logger								= LoggerFactory
																			.getLogger(getClass());
	
	protected ServletContext servletContext;
	
	@Value("${local.server.port:${server.port}}")
	protected int port;
	
	public String getHost() {
		return "http://127.0.0.1:" + this.port + "/";
	}
	
	/**
	 * 构造请求地址
	 * @param uri 请求相对路径
	 * @return 请求地址
	 */
	public String buildUrl(String uri) {
		return getHost() + uri;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	/**
	 * 验证get请求返回内容为
	 * @param url 相对路径
	 * @param content
	 */
	public void assertGetBodyIs(String url, String content) {
		HttpRequest request = HttpRequest.get(buildUrl(url));
		assertThat(request.body()).isEqualTo(content);
	}
	
	/**
	 * 验证get请求内容包含
	 * @param url 相对路径
	 * @param content
	 */
	public void assertGetBodyContains(String url, String content) {
		HttpRequest request = HttpRequest.get(buildUrl(url));
		assertThat(request.body()).contains(content);
	}
}
