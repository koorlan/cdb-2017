package com.formation.cdb.persistence.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.datasource.ConfiguredDatasource;

@Repository
public class CompanyJDBCTemplate implements Dao<Company>{
    
    @Autowired
    private ConfiguredDatasource dataSource;
    
    private JdbcTemplate jdbcTemplateObject;
    
    @PostConstruct
    public void setDataSource() {
       this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void create(Optional<Company> e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<Company> readById(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Optional<Company> e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Optional<Company> e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int rowCount(String filter) {
        String SQL = "SELECT COUNT(*) c FROM company";
        int i = jdbcTemplateObject.queryForObject(SQL, Integer.class);
        return i;
    }

}
