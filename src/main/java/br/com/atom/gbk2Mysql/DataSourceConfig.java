package br.com.atom.gbk2Mysql;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    @Value("${firebird.datasource.url}")
    private String firebirdUrl;

    @Value("${firebird.datasource.username}")
    private String firebirdUsername;

    @Value("${firebird.datasource.password}")
    private String firebirdPassword;

    @Value("${firebird.datasource.driver-class-name}")
    private String firebirdDriverClassName;

    @Value("${mysql.datasource.url}")
    private String mysqlUrl;

    @Value("${mysql.datasource.username}")
    private String mysqlUsername;

    @Value("${mysql.datasource.password}")
    private String mysqlPassword;

    @Value("${mysql.datasource.driver-class-name}")
    private String mysqlDriverClassName;

    @Bean
    DataSource firebirdDataSource() {
        return createDataSource(firebirdUrl, firebirdUsername, firebirdPassword, firebirdDriverClassName);
    }

    @Bean
    DataSource mySQLDataSource() {
        return createDataSource(mysqlUrl, mysqlUsername, mysqlPassword, mysqlDriverClassName);
    }

    private DataSource createDataSource(String url, String username, String password, String driverClassName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}