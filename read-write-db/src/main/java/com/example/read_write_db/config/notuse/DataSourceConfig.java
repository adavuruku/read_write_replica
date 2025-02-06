package com.example.read_write_db.config.notuse;

import org.springframework.context.annotation.Configuration;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:34 PM
 **/
@Configuration
public class DataSourceConfig {

//    @Primary
//    @Bean(name = "writeDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public HikariDataSource writeDataSource() {
//        return new HikariDataSource();
//    }
//
//    @Bean(name = "readDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.read")
//    public HikariDataSource readDataSource() {
//        return new HikariDataSource();
//    }
//    @Primary
//    @Bean(name = "writeDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource writeDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "readDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.read")
//    public DataSource readDataSource() {
//        return DataSourceBuilder.create().build();
//    }

//    @Bean(name = "dataSource")
//    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
//                                        @Qualifier("readDataSource") DataSource readDataSource) {
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(DataSourceType.WRITE, writeDataSource);
//        targetDataSources.put(DataSourceType.READ, readDataSource);
//
//        RoutingDataSource routingDataSource = new RoutingDataSource();
//        routingDataSource.setDefaultTargetDataSource(writeDataSource); // Default is write
//        routingDataSource.setTargetDataSources(targetDataSources);
//        return routingDataSource;
//    }

//    @Bean(name = "dataSource")
//    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
//        return new LazyConnectionDataSourceProxy(routingDataSource);
//    }
}
