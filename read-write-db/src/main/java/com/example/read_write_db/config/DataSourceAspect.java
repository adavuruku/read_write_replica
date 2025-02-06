package com.example.read_write_db.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:47 PM
 **/
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

//    @Before("execution(* com.example.read_write_db.repo..*.save*(..)) || " +
//            "execution(* com.example.read_write_db.repo..*.update*(..)) || " +
//            "execution(* com.example.read_write_db.repo..*.create*(..)) || " +
//            "execution(* com.example.read_write_db.repo..*.alter*(..)) || " +
//            "execution(* com.example.read_write_db.repo..*.delete*(..))")
//    public Object setWriteDataSource(ProceedingJoinPoint joinPoint) throws Throwable{
//        DatabaseContextHolder.set(DataSourceType.WRITE);
//        log.info("Switching to WRITE data source for: {}", joinPoint.getSignature());// Set to write for modifying data
//        try {
//            return joinPoint.proceed();
//        } finally {
////            DatabaseContextHolder.clear();
//        }
//    }
//
//    @Before("execution(* com.example.read_write_db.repo..*.find*(..)) || " +
//            "execution(* com.example.read_write_db.repo..*.get*(..))")
//    public Object setReadDataSource(ProceedingJoinPoint joinPoint) throws Throwable{
//        DatabaseContextHolder.set(DataSourceType.READ);  // Set to read for read operations
//        log.info("Switching to READ data source for: {}", joinPoint.getSignature());
//        try {
//            return joinPoint.proceed();
//        } finally {
//            DatabaseContextHolder.clear();
//        }
//    }
//
//    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        DataSourceType databaseType = isReadOnly ? DataSourceType.READ : DataSourceType.WRITE;
//
//        DatabaseContextHolder.set(databaseType);
//        log.info("Switching to {} data source for: {}", databaseType, joinPoint.getSignature());
//
//        try {
//            return joinPoint.proceed();
//        } finally {
//            DatabaseContextHolder.clear();
//        }
//    }

//    @Around("execution(* com.example.read_write_db.repo..*(..))") // Intercept all repository methods
//    public Object switchDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        // Check if method is a read operation (SELECT, FIND, GET)
//        if (isReadOperation(method.getName())) {
//            DatabaseContextHolder.set(DataSourceType.READ);
//        } else {
//            DatabaseContextHolder.set(DataSourceType.WRITE);
//        }
//
//        try {
//            return joinPoint.proceed();
//        } finally {
//            DatabaseContextHolder.clear(); // Reset after execution
//        }
//    }
//
//    private boolean isReadOperation(String methodName) {
//        return methodName.startsWith("find") ||
//                methodName.startsWith("get") ||
//                methodName.startsWith("read") ||
//                methodName.startsWith("fetch");
//    }
}


