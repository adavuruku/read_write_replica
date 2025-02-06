package com.example.read_write_db.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by Sherif.Abdulraheem 2/6/2025 - 12:21 PM
 **/
@Aspect
@Component
public class TransactionAspect {

//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
//    private final TransactionTemplate readOnlyTransactionTemplate;
//    private final TransactionTemplate writeTransactionTemplate;
//
//    public TransactionAspect(PlatformTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//
//        // Read-Only Transaction Template
//        this.readOnlyTransactionTemplate = new TransactionTemplate(transactionManager);
//        this.readOnlyTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        this.readOnlyTransactionTemplate.setReadOnly(true);
//
//        // Write Transaction Template
//        this.writeTransactionTemplate = new TransactionTemplate(transactionManager);
//        this.writeTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        this.writeTransactionTemplate.setReadOnly(false);
//    }
//
//    // Apply read-only transaction to find methods
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
//
//    // Apply write transaction to save/update methods
//    @Around("execution(* com.example.read_write_db.repo.*.save*(..)) || execution(* com.example.read_write_db.repo.*.update*(..)) || execution(* com.example.read_write_db.repo.*.delete*(..))")
//    public Object applyWriteTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        DatabaseContextHolder.set(DataSourceType.WRITE); // Force Write
//        try {
//            return writeTransactionTemplate.execute(status -> {
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
}
