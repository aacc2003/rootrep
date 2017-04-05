package com.csgg.busi;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csgg.service.api.TestServer;

/**
 * Hello world!
 *
 */
@WebService(endpointInterface = "com.csgg.service.api.TestServer")
public class TestServerImpl implements TestServer
{
	Logger logger = LoggerFactory.getLogger("CSGG");
	
	@Override
	public String testS(String p) {
		logger.info("xxx kaishi{}", p);
		logger.info("jieshu   {}", p);
		return p+"-test";
	}
    
}
