package com.example.read_write_db.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Sherif.Abdulraheem 2/6/2025 - 10:44 AM
 **/

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.read_write_db.repo.read",
        entityManagerFactoryRef = "readEntityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@Slf4j
public class JpaReadConfig {

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create(HikariDataSource.class.getClassLoader()).build();
    }



    @Bean(name = "readEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("readDataSource") DataSource readDataSource) {
        return builder
                .dataSource(readDataSource)
                .packages("com.example.read_write_db.model") // Update with your entity package
                .persistenceUnit("readPU")
                .build();
    }

//    @Bean(name = "readTransactionManager")
//    public PlatformTransactionManager secondaryTransactionManager(
//            @Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

}
