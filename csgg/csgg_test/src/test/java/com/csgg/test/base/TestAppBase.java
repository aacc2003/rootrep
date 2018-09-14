
package com.csgg.test.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.csgg.ui.CsggBootMain;

/**
 */
//@SpringApplicationConfiguration(classes = Main.class)
@SpringBootTest(classes = CsggBootMain.class)
public class TestAppBase extends TestWebBase {
	
	protected final Logger			logger								= LoggerFactory
																			.getLogger(getClass());
	
	protected static final String PROFILE = "sdev";
	
	static {
		System.setProperty("yiji.dubbo.provider.register", "false");
		Apps.setProfileIfNotExists(PROFILE);
	}
}
