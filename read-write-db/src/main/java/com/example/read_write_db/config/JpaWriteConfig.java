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
        basePackages = "com.example.read_write_db.repo.write",
        entityManagerFactoryRef = "writeEntityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@Slf4j
public class JpaWriteConfig {

    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create(HikariDataSource.class.getClassLoader()).build();
    }

    @Primary
    @Bean(name = "writeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("writeDataSource") DataSource writeDataSource) {
        return builder
                .dataSource(writeDataSource)
                .packages("com.example.read_write_db.model") // Update with your entity package
                .persistenceUnit("writePU")
                .build();
    }

//    @Primary
//    @Bean(name = "writeTransactionManager")
//    public PlatformTransactionManager primaryTransactionManager(
//            @Qualifier("writeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory));
//    }
}
