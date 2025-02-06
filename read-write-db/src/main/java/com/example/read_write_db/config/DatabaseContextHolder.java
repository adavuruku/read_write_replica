package com.example.read_write_db.config;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:45 PM
 **/
public class DatabaseContextHolder {
    private static final ThreadLocal<DataSourceType> CONTEXT = new ThreadLocal<>();

    public static void set(DataSourceType databaseType) {
        CONTEXT.set(databaseType);
    }

    public static DataSourceType get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
