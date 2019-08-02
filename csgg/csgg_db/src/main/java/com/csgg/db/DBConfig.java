package com.csgg.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(name="DBProperties")
public class DBConfig {

	@Autowired
	private DBProperties dbProperties;
	
	@Bean
	public DataSource dataSource() {
		return dbProperties.build();
	}
	
	@Bean
	@ConditionalOnMissingBean(TransactionTemplate.class)
	public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
		return new TransactionTemplate(platformTransactionManager);
	}
}
