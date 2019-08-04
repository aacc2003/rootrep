package com.csgg.db;

import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker;
import com.alibaba.druid.pool.vendor.OracleValidConnectionChecker;
import com.xxxxx.env.Env;

@Component
public class DBProperties {
	
	private static final int ORACLE_MAX_ACTIVE = 200;
	private static final int MYSQL_MAX_ACTIVE = 100;
	public static final int DEFAULT_SLOW_SQL_THRESHOLD = 1000;

	@Value("${xxxxx.ds.url}")
	private String url;
	
	/**
	 * 必填：数据库用户名
	 */
	@Value("${xxxxx.ds.username}")
	private String username;
	
	/**
	 * 必填：数据库密码
	 */
	@Value("${xxxxx.ds.password}")
	private String password;
	
	/**
	 * 初始连接数
	 */
	@Value("${xxxxx.ds.initialSize}")
	private Integer initialSize = 5;
	
	/**
	 * 最小空闲连接数
	 */
	@Value("${xxxxx.ds.minIdle}")
	private Integer minIdle = 20;
	
	/**
	 * 最大连接数
	 */
	@Value("${xxxxx.ds.maxActive}")
	private Integer maxActive = 100;
	
	/**
	 * 获取连接等待超时的时间
	 */
	@Value("${xxxxx.ds.maxWait}")
	private Integer maxWait = 10000;
	
	/**
	 * 慢sql日志阈值，超过此值则打印日志
	 */
	@Value("${xxxxx.ds.slowSqlThreshold}")
	private Integer slowSqlThreshold = 5000;
	
	/**
	 * 大结果集阈值，超过此值则打印日志
	 */
	@Value("${xxxxx.ds.maxResultThreshold}")
	private Integer maxResultThreshold = 1000;
	
	/**
	 * 是否在非线上环境开启打印sql，默认开启
	 */
	@Value("${xxxxx.ds.showSql}")
	private boolean showSql = true;
	
	private ClassLoader beanClassLoader;
	
	public void check() {
			Assert.hasText(url, "数据库连接xxxxx.ds.url不能为空");
			Assert.hasText(username, "数据库用户名xxxxx.ds.username不能为空");
			Assert.hasText(password, "数据库密码xxxxx.ds.password不能为空");
	}
	
	public String getUrl() {
		return normalizeUrl(this.url);
	}
	
	public static String normalizeUrl(String url) {
		if (isMysql(url) && !url.contains("?")) {
			return url
					+ "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false";
		}
		return url;
	}
	
	public static boolean isMysql(String url) {
		return url.toLowerCase().startsWith("jdbc:mysql");
	}
	
	public boolean mysql() {
		return isMysql(this.url);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}

	public Integer getSlowSqlThreshold() {
		return slowSqlThreshold;
	}

	public void setSlowSqlThreshold(Integer slowSqlThreshold) {
		this.slowSqlThreshold = slowSqlThreshold;
	}

	public Integer getMaxResultThreshold() {
		return maxResultThreshold;
	}

	public void setMaxResultThreshold(Integer maxResultThreshold) {
		this.maxResultThreshold = maxResultThreshold;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isShowSql() {
		return showSql;
	}
	
	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}
	
	public ClassLoader getBeanClassLoader() {
		return beanClassLoader;
	}

	public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

	/**
	 * 通过当前配置创建datasource
	 */
	public DruidDataSource build() {
		this.check();
		if (this.beanClassLoader == null) {
			this.beanClassLoader = ClassUtils.getDefaultClassLoader();
		}
		DruidDataSource dataSource = new DruidDataSource();
		// 基本配置
		dataSource.setDriverClassLoader(this.getBeanClassLoader());
		dataSource.setUrl(this.getUrl());
		dataSource.setUsername(this.getUsername());
		dataSource.setPassword(this.getPassword());
		//应用程序可以自定义的参数
		dataSource.setInitialSize(this.getInitialSize());
		dataSource.setMinIdle(this.getMinIdle());
		
		if (mysql()) {
			maxActive = Math.max(maxActive, MYSQL_MAX_ACTIVE);
		} else {
			maxActive = Math.max(maxActive, ORACLE_MAX_ACTIVE);
		}
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(this.getMaxWait());
		//dataSource.setConnectionProperties(druidProperties.getConnectionProperties());
		//检测需要关闭的空闲连接间隔，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		//连接在池中最小生存的时间
		dataSource.setMinEvictableIdleTimeMillis(60000);
		dataSource.setTestWhileIdle(true);
		//从连接池中获取连接时不测试
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setValidationQueryTimeout(5);
		
		//		dataSource.setRemoveAbandoned(true);
		//		dataSource.setRemoveAbandonedTimeout(1800);
		//		dataSource.setLogAbandoned(true);
		if (this.mysql()) {
			System.setProperty("druid.mysql.usePingMethod", "true");
			dataSource.setValidConnectionChecker(new MySqlValidConnectionChecker());
		} else {
			System.setProperty("druid.oracle.pingTimeout", "5");
			dataSource.setValidConnectionChecker(new OracleValidConnectionChecker());
		}
		//fixme 开启ps cache
		//dataSource.setPoolPreparedStatements(!druidProperties.mysql());
		Properties properties = new Properties();
		if (this.isShowSql()) {
			properties.put("xxxxx.ds.logForHumanRead", Boolean.TRUE.toString());
		}
		if (!Env.isOnline()) {
			//线下测试时，执行时间超过100ms就打印sql，用户可以设置为0，每条sql语句都打印
			properties.put("xxxxx.ds.slowSqlMillis",
				Integer.toString(Math.min(this.getSlowSqlThreshold(), DEFAULT_SLOW_SQL_THRESHOLD)));
		} else {
			//线上运行时，阈值选最大值。有可能线下测试设置为0，方便调试
			properties.put("xxxxx.ds.slowSqlMillis",
				Integer.toString(Math.max(this.getSlowSqlThreshold(), DEFAULT_SLOW_SQL_THRESHOLD)));
		}
		properties.put("xxxxx.ds.maxResult", Integer.toString(this.getMaxResultThreshold()));
		dataSource.setConnectProperties(properties);
		try {
			dataSource.init();
//			registerMetrics(dataSource);
		} catch (SQLException e) {
//			throw new AppConfigException("druid连接池初始化失败", e);
		}
//		Hera.addValueTrigger(event -> {
//			event.ifPresent(this.mergePropertyPath("maxActive"), v -> {
//				try {
//					logger.info("修改数据源:id={},url={},maxActive={}", dataSource.getID(), dataSource.getUrl(), v);
//					dataSource.setMaxActive(Integer.parseInt(v.toString()));
//				} catch (Exception e) {
//					logger.error("动态修改数据源" + dataSource + "配置失败", e);
//				}
//			});
//			event.ifPresent(this.mergePropertyPath("password"), v -> {
//				logger.info("修改数据源:id={},url={}密码", dataSource.getID(), dataSource.getUrl());
//				dataSource.setPassword(v.trim());
//			});
//			//支持主节点宕机后,切换到其他节点
//			event.ifPresent(this.mergePropertyPath("url"), v -> {
//				v = v.trim();
//				DBEndpoint oldDE = parseJDBCUrl(DruidProperties.this.getUrl());
//				DBEndpoint newDE = parseJDBCUrl(v);
//				if (!newDE.getIp().equals(oldDE.getIp())) {
//					String newUrl = normalizeUrl(v);
//					logger.info("修改数据源:id={},url={},新url={}", dataSource.getID(), dataSource.getUrl(), newUrl);
//					//fixme: close时设置inited=false
//					dataSource.close();
//					try {
//						dataSource.restart();
//					} catch (SQLException e) {
//						logger.error("修改数据源连接失败", e);
//					}
//					dataSource.setUrl(newUrl);
//				}
//			});
//		});
		return dataSource;
	}
	
//	private void registerMetrics(DruidDataSource dataSource) {
//		Long id = dataSource.getID();
//		MetricRegistry metricRegistry = MetricsHolder.metricRegistry();
//		metricRegistry.register(MetricRegistry.name("druid", "activeCount", "id" + id.toString()),
//			(Gauge<Integer>) dataSource::getActiveCount);
//		metricRegistry.register(MetricRegistry.name("druid", "maxActive", "id" + id.toString()),
//			(Gauge<Integer>) dataSource::getMaxActive);
//		metricRegistry.register(MetricRegistry.name("druid", "full", "id" + id.toString()), (Gauge<Integer>) () -> {
//			if (dataSource.isFull()) {
//				return 1;
//			} else {
//				return 0;
//			}
//		});
//	}
}
