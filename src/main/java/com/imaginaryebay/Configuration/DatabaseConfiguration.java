package com.imaginaryebay.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.HashMap;



/**
 * Created by Ben_Big on 6/27/16.
 */


@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws PropertyVetoException{
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:file:/Users/ben/Desktop/Hello");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private Map<String,?> jpaProperties() {
        Map<String,String> jpaPropertiesMap = new HashMap<String,String>();
        jpaPropertiesMap.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");

        //ToDo: "update" property should only be used during development
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "update");
        return jpaPropertiesMap;
    }


    //ToDo: What to do with PropertyVetoException
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws PropertyVetoException{
        LocalContainerEntityManagerFactoryBean factoryBean=
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.imaginaryebay.");
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }





}
