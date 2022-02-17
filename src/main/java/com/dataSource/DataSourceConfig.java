package com.dataSource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.isl")
@EnableTransactionManagement
public class DataSourceConfig {
    
    @Value("${spring.datasource.driverClassName}")
    private String dbDriverClassName;
    
    @Value("${spring.datasource.url}")
    private String dbJdbcUrl;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
	
    @SuppressWarnings("rawtypes")
	@Bean(name = "dataSource")
    public DataSource dataSource() {
    	DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    	
    	dataSourceBuilder.driverClassName(dbDriverClassName);
        dataSourceBuilder.url(dbJdbcUrl);
        dataSourceBuilder.username(dbUsername);
        dataSourceBuilder.password(dbPassword);
        return dataSourceBuilder.build();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/config/mybatis-config.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/**/**/*.xml"));
        //sessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis/config/mybatis-config.xml"));
        //sessionFactory.setMapperLocations(applicationContext.getResource("classpath:/mapper/**/**/**/*.xml")); //여기서 오류남 이유를 모르겠음.
        return sessionFactory;
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
      SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
      return sqlSessionTemplate;
    }
    
	@Bean
	public DataSourceTransactionManager transactionManager(){
		DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());
		return manager;
	}
}
