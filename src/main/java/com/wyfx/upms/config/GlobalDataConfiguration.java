package com.wyfx.upms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by liu on 2017/9/12.
 * 多数据源
 */
@Configuration
public class GlobalDataConfiguration {
    @Bean(name = "primaryDS")
    @Qualifier("primaryDS")
    @Primary
    @ConfigurationProperties(prefix = "spring.primary.datasource")
    public DataSource primaryDataSource() {
        System.err.println("-------------------- primaryDataSource init ---------------------");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDS")
    @Qualifier("secondaryDS")
    @ConfigurationProperties(prefix = "spring.secondary.datasource")
    public DataSource secondaryDataSource() {
        System.err.println("-------------------- secondaryDataSource init ---------------------");
        return DataSourceBuilder.create().build();
    }
}
