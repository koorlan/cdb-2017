package com.formation.cdb.web.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.formation.cdb.persistence.datasource.ConfiguredDatasource;

@Component
public class TransactionManagerConfigurer implements TransactionManagementConfigurer {

    @Autowired
    ConfiguredDatasource dataSource;
    
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
