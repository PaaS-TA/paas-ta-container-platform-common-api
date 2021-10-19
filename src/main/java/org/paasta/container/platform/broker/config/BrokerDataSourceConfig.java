package org.paasta.container.platform.broker.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "brokerEntityManagerFactory",
        transactionManagerRef = "brokerTransactionManager",
        basePackages = {"org.paasta.container.platform.broker"})
public class BrokerDataSourceConfig {

    @Bean(name = "brokerDataSource")
    @ConfigurationProperties(prefix = "spring.broker.datasource")
    public DataSource brokerDataSource() {

        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }


    @Bean(name = "brokerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean brokerEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("brokerDataSource") DataSource brokerDataSource) {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.show_sql", true);


        return builder
                .dataSource(brokerDataSource)
                .packages("org.paasta.container.platform.broker")
                .persistenceUnit("broker")
                .properties(properties)
                .build();
    }

    @Bean(name = "brokerTransactionManager")
    public PlatformTransactionManager brokerTransactionManager(
            @Qualifier("brokerEntityManagerFactory") EntityManagerFactory brokerEntityManagerFactory) {
        return new JpaTransactionManager(brokerEntityManagerFactory);
    }

}