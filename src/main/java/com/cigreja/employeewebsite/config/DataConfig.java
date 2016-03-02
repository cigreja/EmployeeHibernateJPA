package com.cigreja.employeewebsite.config;

import org.apache.commons.dbcp.BasicDataSource;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.util.Properties;

/**
 * DataConfig
 *
 * @author Carlos Igreja
 * @since Feb 21, 2016
 */
@Configuration
public class DataConfig {

    @Bean(name = "datasource")
    public DataSource datasource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/hibernateDB");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource) {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        // this sets hibernate properties
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.id.new_generator_mappings", "false");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        emf.setJpaProperties(hibernateProperties);
        emf.setPackagesToScan("com.cigreja.employeewebsite.entities");
        return emf;
    }

    @Configuration
    @EnableTransactionManagement
    public static class TransactionConfig implements TransactionManagementConfigurer{

        @Inject
        private EntityManagerFactory emf;

        public PlatformTransactionManager annotationDrivenTransactionManager() {
            return new JpaTransactionManager(emf);
        }
    }
}
