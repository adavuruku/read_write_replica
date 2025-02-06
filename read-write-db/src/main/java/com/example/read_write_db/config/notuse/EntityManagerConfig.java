package com.example.read_write_db.config.notuse;


/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 3:33 PM
 **/
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories (
//        basePackages = "com.example.read_write_db.repo",
//        entityManagerFactoryRef = "primaryEntityManagerFactory",
//        transactionManagerRef = "primaryTransactionManager"
//)
//@EnableJpaRepositories (
//        basePackages = "com.example.read_write_db.repo",
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef = "transactionManager"
//)
public class EntityManagerConfig {

//    @Primary
//    @Bean(name = "primaryEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("writeDataSource") DataSource writeDataSource) {
//        return builder
//                .dataSource(writeDataSource)
//                .packages("com.example.read_write_db.model") // Update with your entity package
//                .persistenceUnit("writePU")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "primaryTransactionManager")
//    public PlatformTransactionManager primaryTransactionManager(
//            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory));
//    }
//
//
//
//    @Bean(name = "secondaryEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("readDataSource") DataSource readDataSource) {
//        return builder
//                .dataSource(readDataSource)
//                .packages("com.example.read_write_db.model") // Update with your entity package
//                .persistenceUnit("readPU")
//                .build();
//    }
//
//    @Bean(name = "secondaryTransactionManager")
//    public PlatformTransactionManager secondaryTransactionManager(
//            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }

//    @Primary
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("dataSource") DataSource dataSource) { // Use RoutingDataSource
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.read_write_db.model") // Entity package
//                .persistenceUnit("dynamicPU")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory));
//    }
}
