package com.csgg.appconfig;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.health.DubboHealthIndicator;
import com.google.common.collect.Maps;

@Configuration
//@ConditionalOnProperty(value = "spring.dubbo.server", matchIfMissing = true)
public class DubboConfiguration implements InitializingBean, ApplicationContextAware {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DubboConfiguration.class);
	
	private ApplicationContext applicationContext ;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	@Bean
	public static ApplicationConfig applicationConfig() {
System.out.println("-------------dubbo app:");
		ApplicationConfig config = new ApplicationConfig();
		config.setName("csgg");
		config.setOwner("wanglei");
		
		return config;
	}
	
	@Bean
	@DependsOn("applicationConfig")
	public static RegistryConfig registryConfig() {
System.out.println("-------------dubbo registry:");
		RegistryConfig config = new RegistryConfig();
		config.setProtocol("zookeeper");
//		config.setProtocol("dubbo");
		
		config.setRegister(true);
		config.setAddress("zookeeper://192.168.0.104:2181");
		config.setFile("/var/dubbo/dubbo.cache");
		return config;
	}
	
	@Bean
	@ConditionalOnProperty(value = "yiji.dubbo.provider.enable", matchIfMissing = true)
	@DependsOn("applicationConfig")
	public static ProtocolConfig protocolConfig() {
System.out.println("-------------dubbo protocol:");
		ProtocolConfig config = new ProtocolConfig();
		config.setName("dubbo");
		config.setPort(20880);
		
//		config.setThreadpool("yijiDubbo");
		config.setThreads(200);
		config.setQueues(400);
		Map<String, String> params = Maps.newHashMap();
		params.put("corethreads", "200");
		params.put("alive", "300");
		config.setParameters(params);
		//设置序列化协议,如果不设置,使用dubbo默认协议 hessian2
		
		return config;
	}
	
	@Bean
	@ConditionalOnProperty(value = "yiji.dubbo.provider.enable", matchIfMissing = true)
	public static ProviderConfig providerConfig() {
System.out.println("-------------dubbo provider:");
		ProviderConfig config = new ProviderConfig();
		config.setTimeout(60000);
		config.setCluster("failfast");
		config.setRegister(true);

		return config;
	}
	
	@Bean
	@DependsOn({ "applicationConfig", "registryConfig" })
	public static ConsumerConfig consumerConfig() {
System.out.println("-------------dubbo consumer:");
		ConsumerConfig config = new ConsumerConfig();
		config.setCheck(false);
		config.setLoadbalance("roundrobin");
		
		return config;
	}
	
	@Bean
	@DependsOn({ "registryConfig" })
	public static MonitorConfig monitorConfig() {
System.out.println("-------------dubbo monitor:");
		MonitorConfig config = new MonitorConfig();
		config.setProtocol("registry");
		return config;
	}
	
//	// for shardingjdbc
//	2.1.7 parent 已经实现了
//	@Bean
//	public DubboHealthIndicator dubboHealthIndicator() {
//System.out.println("-------------dubbo indicator:");
//		return new DubboHealthIndicator();
//	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.applicationContext= applicationContext;
	}
	
}
