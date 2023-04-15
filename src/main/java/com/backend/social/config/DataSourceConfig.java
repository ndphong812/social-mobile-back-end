package com.backend.social.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/socialdb");
        dataSourceBuilder.username("sysadmin");
        dataSourceBuilder.password("duongtanthanh");
        return dataSourceBuilder.build();
    }

    public static void configDataSource(String username,String password){
        DataSource dataSource = DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/socialdb")
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    public static void configAdmin(){
        DataSource dataSource = DataSourceBuilder
                .create()
                .url("jdbc:postgresql://localhost:5432/socialdb")
                .username("sysadmin")
                .password("duongtanthanh")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
