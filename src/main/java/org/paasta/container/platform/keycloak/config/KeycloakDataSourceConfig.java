package org.paasta.container.platform.keycloak.config;


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
        entityManagerFactoryRef = "keycloakEntityManagerFactory",
        transactionManagerRef = "keycloakTransactionManager",
        basePackages = {"org.paasta.container.platform.keycloak"})
public class KeycloakDataSourceConfig {

    @Bean(name = "keycloakDataSource")
    @ConfigurationProperties(prefix = "spring.keycloak.datasource")
    public DataSource keycloakDataSource() {

        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }


    @Bean(name = "keycloakEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean keycloakEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("keycloakDataSource") DataSource keycloakDataSource) {

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", true);


        return builder
                .dataSource(keycloakDataSource)
                .packages("org.paasta.container.platform.keycloak")
                .persistenceUnit("keycloak")
                .properties(properties)
                .build();
    }

    @Bean(name = "keycloakTransactionManager")
    public PlatformTransactionManager keycloakTransactionManager(
            @Qualifier("keycloakEntityManagerFactory") EntityManagerFactory keycloakEntityManagerFactory) {
        return new JpaTransactionManager(keycloakEntityManagerFactory);
    }

}