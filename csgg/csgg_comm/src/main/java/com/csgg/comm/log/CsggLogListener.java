package com.csgg.comm.log;

//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ch.qos.logback.classic.LoggerContext;
//import ch.qos.logback.classic.joran.JoranConfigurator;
//import ch.qos.logback.core.joran.spi.JoranException;
//
//public class CsggLogListener implements ServletContextListener {
//	
//	private static final Logger	logger			= LoggerFactory
//													.getLogger(CsggLogListener.class);
//	
//	private static final String	CONFIG_LOCATION	= "logbackcsgg";
//	
//	@Override
//	public void contextInitialized(ServletContextEvent event) {
//		//从web.xml中加载指定文件名的日志配置文件  
//		String logbackConfigLocation = event.getServletContext().getInitParameter(CONFIG_LOCATION);
//		String fn = event.getServletContext().getRealPath(logbackConfigLocation);
//		try {
//			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//			loggerContext.reset();
//			JoranConfigurator joranConfigurator = new JoranConfigurator();
//			joranConfigurator.setContext(loggerContext);
//			joranConfigurator.doConfigure(fn);
//			logger.debug("loaded slf4j configure file from {}", fn);
//			
//		} catch (JoranException e) {
//			logger.error("can loading slf4j configure file from " + fn, e);
//		}
//	}
//	
//	@Override
//	public void contextDestroyed(ServletContextEvent event) {
//	}
//	
//}


//  以上是原始内容，搜寻后发现没有其他地方使用， 暂先注释 
public class CsggLogListener {}