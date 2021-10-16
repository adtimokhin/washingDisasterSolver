package com.adtimokhin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author adtimokhin
 * 03.08.2021
 **/

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
@EnableJpaRepositories("com.adtimokhin.repository")
public class PersistenceConfig {

    @Bean
    public DataSource getDataSource(@Value("${user}") String user,
                                    @Value("${password}") String password,
                                    @Value("${url}") String url,
                                    @Value("${driver}") String driver) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource); // emf needs datasource to create Connection with the database
        emf.setPackagesToScan("com.adtimokhin.model"); // tells Spring where to find our entities
        emf.setJpaVendorAdapter(jpaVendorAdapter); // emf needs jpaVendorAdapter to work with JPA
        emf.afterPropertiesSet();

        return emf.getObject();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}


