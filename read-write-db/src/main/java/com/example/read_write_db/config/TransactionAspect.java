package com.example.read_write_db.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by Sherif.Abdulraheem 2/6/2025 - 12:21 PM
 **/
@Aspect
@Component
@Slf4j
public class TransactionAspect {

    @Autowired
    private PlatformTransactionManager transactionManager;

    private final TransactionTemplate readOnlyTransactionTemplate;
    private final TransactionTemplate writeTransactionTemplate;

    public TransactionAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;

        // Read-Only Transaction Template
        this.readOnlyTransactionTemplate = new TransactionTemplate(transactionManager);
//        this.readOnlyTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        this.readOnlyTransactionTemplate.setReadOnly(true);

        // Write Transaction Template
        this.writeTransactionTemplate = new TransactionTemplate(transactionManager);
//        this.writeTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        this.writeTransactionTemplate.setReadOnly(false);
    }

    // Apply read-only transaction to find methods
//    @Around("execution(* com.example.read_write_db.repo.*.find*(..)) || execution(* com.example.read_write_db.repo.*.get*(..))")
//    public Object applyReadOnlyTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        DatabaseContextHolder.set(DataSourceType.READ); // Force Read DB
//        try {
//            return readOnlyTransactionTemplate.execute(status -> {
//                try {
//                    return joinPoint.proceed();
//                } catch (Throwable throwable) {
//                    throw new RuntimeException(throwable);
//                }
//            });
//        } finally {
//            DatabaseContextHolder.clear();
//        }
//    }

    // Apply write transaction to save/update methods
//    @Around("execution(* com.example.read_write_db.repo.*.save*(..)) || execution(* com.example.read_write_db.repo.*.update*(..)) || execution(* com.example.read_write_db.repo.*.delete*(..))")

//    @Around("@annotation(useDataSource)")
//    public Object applyWriteTransaction(ProceedingJoinPoint joinPoint, UseDataSource useDataSource) throws Throwable {
//        try {
//            log.info("CTX Holder {} - UDS {}", DatabaseContextHolder.get(), useDataSource.value());
//            if(!DatabaseContextHolder.get().name().equalsIgnoreCase(useDataSource.value().name())){
//                //switch context and recreate a new one (transaction manager)
//                log.info("Creating a new transaction manager for {}", useDataSource.value());
//                DatabaseContextHolder.set(useDataSource.value());
//                if(useDataSource.value().name().equals(DataSourceType.WRITE.name())){
//                    return writeTransactionTemplate.execute(status -> {
//                        try {
//                            return joinPoint.proceed();
//                        } catch (Throwable throwable) {
//                            throw new RuntimeException(throwable);
//                        }
//                    });
//                }else{
//                    return readOnlyTransactionTemplate.execute(status -> {
//                        try {
//                            return joinPoint.proceed();
//                        } catch (Throwable throwable) {
//                            throw new RuntimeException(throwable);
//                        }
//                    });
//                }
//            }
//            //if it same operation proceed and dont create a new transaction manager
//            return joinPoint.proceed();
//        } finally {
////            DatabaseContextHolder.clear();
//        }
//    }

//    @Around("@annotation(useDataSource)")
//    public Object applyWriteTransaction(ProceedingJoinPoint joinPoint, UseDataSource useDataSource) throws Throwable {
//
//        log.info("CTX Holder {} - UDS {}", DatabaseContextHolder.get(), useDataSource.value());
//        TransactionTemplate transactionTemplate = (useDataSource.value() == DataSourceType.WRITE)
//                ? writeTransactionTemplate
//                : readOnlyTransactionTemplate;
//
//        DatabaseContextHolder.set(useDataSource.value());
//
//        return transactionTemplate.execute(status -> {
//            try {
//                return joinPoint.proceed();
//            } catch (Throwable throwable) {
//                status.setRollbackOnly();  // Mark transaction for rollback
//                throw new RuntimeException("Transaction failed and rolled back", throwable);
//            }
//        });
//    }

//    @Around("@annotation(useDataSource)")
//    public Object applyWriteTransaction(ProceedingJoinPoint joinPoint, UseDataSource useDataSource) throws Throwable {
//        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);  // Always create a new transaction
//        definition.setReadOnly(useDataSource.value() == DataSourceType.READ);  // Read-Only for READ
//
//        TransactionStatus status = transactionManager.getTransaction(definition);
//        DatabaseContextHolder.set(useDataSource.value());
//        try {
//            log.info("CTX {}", DatabaseContextHolder.get());
//            Object result = joinPoint.proceed();  // Execute the method
//            transactionManager.commit(status);   // Commit transaction
//            return result;
//        } catch (Throwable throwable) {
//            transactionManager.rollback(status); // Explicitly roll back
//            throw new RuntimeException("Transaction failed and rolled back", throwable);
//        }
//    }
}
