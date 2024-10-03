package com.jp.products;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.jp.products"},
	entityManagerFactoryRef = "productsLocalSessionFactoryBean",
	transactionManagerRef = "productsPlatoformTransactionManager")
public class ProductsDBConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.productsdb")
	public DataSource productsDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	@Bean
	public LocalSessionFactoryBean productsLocalSessionFactoryBean() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(productsDataSource());
		localSessionFactoryBean.setPackagesToScan("com.jp.products");
		localSessionFactoryBean.setHibernateProperties(hibernateProperties());
		
		return localSessionFactoryBean;
	}
	
	@Bean
	public PlatformTransactionManager productsPlatoformTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(productsLocalSessionFactoryBean().getObject());
		return jpaTransactionManager;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		return properties;
	}
	

}
