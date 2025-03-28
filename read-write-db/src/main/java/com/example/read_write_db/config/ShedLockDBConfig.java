package com.example.read_write_db.config;

import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * Created by Sherif.Abdulraheem 27/03/2025 - 14:10
 **/
@Configuration
@EnableScheduling
@EnableAsync
@Slf4j
public class ShedLockDBConfig {

    @Bean
    public JdbcTemplateLockProvider lockProvider(@Qualifier("writeDataSource") DataSource dataSource) {
        log.info("lockProvider ", dataSource);
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .usingDbTime().build()
        );
    }
}
