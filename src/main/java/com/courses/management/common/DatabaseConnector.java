package com.courses.management.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnector {
    private static final HikariDataSource ds;

    private DatabaseConnector() {
        throw new RuntimeException("This operation not supported!");
    }

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5547/course_management");
        config.setUsername("postgres");
        config.setPassword("Sam@64hd!+4");
        ds = new HikariDataSource(config);
        ds.setMaximumPoolSize(10);
    }

    public static HikariDataSource getConnector(){
        return ds;
    }
}
