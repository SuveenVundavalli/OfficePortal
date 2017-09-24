package com.suveen.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ApplicationContextConfig {
	
private static Logger logger = LoggerFactory.getLogger(ApplicationContextConfig.class);

	
	// H2 Database
	
		@Bean(name = "dataSource")
		public DataSource getH2DataSource() {
			logger.debug("Starting of the method getOracleDataSource");

			DriverManagerDataSource dataSource = new DriverManagerDataSource();

			dataSource.setUrl("jdbc:h2:tcp://localhost/~/OfficePortal");
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUsername("sa");
			dataSource.setPassword("sa");
			
			logger.debug("Setting the data source :" + dataSource.getConnectionProperties());
			logger.debug("Ending of the method getOracleDataSource");

			return dataSource;
		}
		
		private Properties getHibernateProperties() {
			logger.debug("Starting of the method getHibernateProperties");
			Properties properties = new Properties();
			properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.put("hibernate.show_sql", "true");
			properties.put("hibernate.hbm2ddl.auto", "update");
			logger.debug("Ending of the method getHibernateProperties");

			return properties;
		}
		
		@Autowired
		@Bean(name = "sessionFactory")
		public SessionFactory getSessionFactory(DataSource dataSource) {
			logger.debug("Starting of the method getSessionFactory");

			LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
			sessionBuilder.addProperties(getHibernateProperties());
			
			sessionBuilder.scanPackages("com.suveen");
			
			logger.debug("Ending of the method getSessionFactory");
			return sessionBuilder.buildSessionFactory();
		}
		
		@Autowired
		@Bean(name = "transactionManager")
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
			logger.debug("Starting of the method getTransactionManager");
			HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
			logger.debug("Ending of the method getTransactionManager");
			return transactionManager;
		}

}
