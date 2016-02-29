package com.cigreja.employeewebsite.config;

import org.apache.commons.dbcp.BasicDataSource;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
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
//@EnableTransactionManagement
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
    public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        // this sets hibernate properties
        Properties hibernateProperties = new Properties();
        //String hibernateDialect = "org.hibernate.dialect.MySQL5Dialect";
        //hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        //hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty("hibernate.id.new_generator_mappings", "false");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setJpaProperties(hibernateProperties);
        emf.setPackagesToScan("com.cigreja.employeewebsite.business");
        return emf;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return adapter;
    }


    @Configuration
    @EnableTransactionManagement
    public static class TransactionConfig implements TransactionManagementConfigurer {
        @Inject
        private EntityManagerFactory emf;

        public PlatformTransactionManager annotationDrivenTransactionManager() {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(emf);
            return transactionManager;
        }
    }

//    @Bean
//    public HibernateJpaVendorAdapter jpaVendorAdapter(){
//
//        // Hibernate JPA Vendor Adapter
//        System.out.println("jpa before init");
//        HibernateJpaVendorAdapter jpaAdapter;
//        jpaAdapter = new HibernateJpaVendorAdapter();
//        System.out.println("jpa after init");
//        jpaAdapter.setDatabase(Database.MYSQL);
//        System.out.println("jpa setdb");
//        jpaAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
//        System.out.println("jpa setDatabasePlatform");
//        jpaAdapter.setShowSql(true);
//        System.out.println("jpa setShowSql");
//        jpaAdapter.setGenerateDdl(false);
//        System.out.println("jpa setGenerateDdl");
//
//        return jpaAdapter;
//    }
//
//    @Autowired
//    @Bean(name = "entityManagerFactory")
//    public EntityManagerFactory entityManagerFactory(DataSource dataSource, HibernateJpaVendorAdapter jpaAdapter) {
//
//        // this sets hibernate properties
//        Properties hibernateProperties = new Properties();
//        //String hibernateDialect = "org.hibernate.dialect.MySQL5Dialect";
//        //hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
//        //hibernateProperties.setProperty("hibernate.show_sql", "true");
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
//        hibernateProperties.setProperty("hibernate.id.new_generator_mappings", "false");
//
//        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
//        lcemfb.setDataSource(dataSource);
//        lcemfb.setJpaVendorAdapter(jpaAdapter);
//        //emf.setJpaProperties(hibernateProperties); // JPA Properties
//        lcemfb.setPackagesToScan("com.cigreja.employeewebsite.business");
//        //emf.setAnnotatedClasses(new Class<?>[]{Employee.class,Address.class});
//
//        // return the singleton entity manager factory
//        return lcemfb.getObject();
//    }
//
//    @Autowired
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }

    //@Configuration
    //@EnableTransactionManagement
//    public static class TransactionConfig implements TransactionManagementConfigurer {
//
//        @Inject
//        private EntityManagerFactory lcemfb;
//
//        public PlatformTransactionManager annotationDrivenTransactionManager() {
//            JpaTransactionManager transactionManager = new JpaTransactionManager();
//            transactionManager.setEntityManagerFactory(lcemfb);
//            return transactionManager;
//        }
//    }
}
