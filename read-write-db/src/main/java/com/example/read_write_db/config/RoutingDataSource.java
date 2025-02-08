package com.example.read_write_db.config;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;



/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:43 PM
 **/
//@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        DataSourceType dataSourceType = isReadOnly ? DataSourceType.READ : DataSourceType.WRITE;
//
//
//        logger.info("Using data source: {} - isReadOnly {}", dataSourceType, isReadOnly);
//
//        return dataSourceType;
        logger.info("Using data source: {} - isReadOnly {}", DatabaseContextHolder.get());
        return DatabaseContextHolder.get();
    }
}
