/**
 * 
 */
package com.csgg.comm.log;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author Administrator
 *
 */
public class LogbackAsync extends AsyncAppender {
	private static final String LOGGER_NAME = "com.csgg";
	
	public LogbackAsync() {
		super();
		//设置queue大小为1024
		this.setQueueSize(1024);
		//如果队列剩余空间小于这个值后，就开始做丢弃判断
		this.setDiscardingThreshold(100);
		//开启方法栈支持,配置文件中可以覆盖此配置.
		this.setIncludeCallerData(true);
	}
	
	/**
	 * 当日志处理队列到达discardingThreshold时 1.丢弃掉 所有TRACE, DEBUG日志 2.保留level大于info的日志
	 */
	protected boolean isDiscardable(ILoggingEvent event) {
		Level level = event.getLevel();
		if (level.toInt() < Level.INFO_INT) {
			return true;
		} else if (level.toInt() > Level.INFO_INT) {
			return false;
		} else {
			if (event.getLoggerName() != null && event.getLoggerName().startsWith(LOGGER_NAME)) {
				return false;
			} else {
				return true;
			}
		}
	}
}
