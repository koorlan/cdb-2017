package com.formation.cdb.persistence.datasource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class ConfiguredDatasource extends DriverManagerDataSource{
    public ConfiguredDatasource(){
        this.setDriverClassName("com.mysql.jdbc.Driver");
        this.setUrl("jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull");
        this.setUsername("admincdb");
        this.setPassword("qwerty1234");
    }
}
