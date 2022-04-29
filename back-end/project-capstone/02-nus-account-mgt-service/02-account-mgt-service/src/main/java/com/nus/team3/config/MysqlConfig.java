package com.nus.team3.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = "com.nus.team3.mapper", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
@EnableTransactionManagement
public class MysqlConfig {

  @Value("${spring.datasource.driverClassName}")
  private String driverName;

  @Value("${spring.datasource.url}")
  private String mysqlUrl;

  @Value("${spring.datasource.username}")
  private String mysqlUserName;

  @Value("${spring.datasource.password}")
  private String mysqlPassword;

  @Value("${spring.datasource.pool.name}")
  private String poolName;

  @Value("${spring.datasource.max.pool.size:8}")
  private final int maxPoolSize = 8;

  @Bean(name = "mysqlDataSource")
  @Primary
  public DataSource mysqlDataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(driverName);
    config.setJdbcUrl(mysqlUrl);
    config.setUsername(mysqlUserName);
    config.setPassword(mysqlPassword);
    config.setPoolName(poolName);
    config.setMaximumPoolSize(maxPoolSize);
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    return new HikariDataSource(config);
  }

  @Bean(name = "mysqlSqlSessionFactory")
  @Primary
  public SqlSessionFactory mysqlSqlSessionFactory(
      @Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    return sqlSessionFactoryBean.getObject();
  }

  @Bean(name = "mysqlTm")
  @Primary
  public DataSourceTransactionManager mysqlDataSourceTransactionManager(
      @Qualifier("mysqlDataSource") DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }

  @Bean(name = "mysqlSqlSessionTemplate")
  @Primary
  public SqlSessionTemplate mysqlSqlSessionTemplate(
      @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  private org.apache.ibatis.session.Configuration mybatisConfiguration() {
    org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
    conf.setMapUnderscoreToCamelCase(true);
    return conf;
  }
}