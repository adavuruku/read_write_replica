package com.example.read_write_db.config;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Sherif.Abdulraheem 06/02/2025 - 20:32
 * @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
 * @Retention(RetentionPolicy.RUNTIME)
 * @Qualifier
 * public @interface ReplicaDataSource {
 * }
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public @interface UseDataSource {
    DataSourceType value();
}
