package com.formation.cdb.persistence.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.datasource.ConfiguredDatasource;
import com.formation.cdb.util.DateUtil;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.ComputerMapper;

// TODO: Auto-generated Javadoc
/**
 * The Enum ComputerDaoImpl.
 */
@Repository
public class ComputerDaoImpl implements Dao<Computer> {

    @Autowired
    private ConfiguredDatasource dataSource;
    
    private JdbcTemplate jdbcTemplateObject;
    
    @PostConstruct
    public void setDataSource() {
       this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
    /** The insert. */
    String INSERT;
    
    /** The read by id. */
    String READ_BY_ID;
    
    /** The update. */
    String UPDATE;
    
    /** The delete. */
    String DELETE;
    
    /** The read all limit. */
    String READ_ALL_LIMIT;
    
    /** The row count. */
    String ROW_COUNT;
    /**
     * Private constructor for Singleton Implementation.
     */
    public ComputerDaoImpl() {
        //construct queries from configuration file;
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ComputerDaoImpl.class.getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            LOGGER.error("Sorry, unable to find " + filename);
            throw new PersistenceException("Unable to acces config file at " + filename);
        }

        try {
            prop.load(input);
            StringBuilder sb = new StringBuilder();
            //INSERT
            sb.append("INSERT INTO ");
            sb.append(prop.getProperty("db_computer_table") + " (");
            sb.append(prop.getProperty("db_computer_col_name") + ",");
            sb.append(prop.getProperty("db_computer_col_introduced") + ",");
            sb.append(prop.getProperty("db_computer_col_discontinued") + ",");
            sb.append(prop.getProperty("db_computer_col_company_id"));
            sb.append(") values (?,?,?,?);");

            INSERT = sb.toString();
            //READ_BY_ID
            sb = new StringBuilder();
            sb.append("SELECT ");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_id") + ",");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_name") + ",");
            sb.append(prop.getProperty("db_computer_col_introduced") + ",");
            sb.append(prop.getProperty("db_computer_col_discontinued") + ",");
            sb.append(prop.getProperty("db_computer_col_company_id") + ",");
            sb.append(prop.getProperty("db_company_table") + '.' + prop.getProperty("db_company_col_name"));
            sb.append(" AS company_name FROM ");
            sb.append(prop.getProperty("db_computer_table"));
            sb.append(" LEFT JOIN ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" ON ");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_company_id"));
            sb.append('=');
            sb.append(prop.getProperty("db_company_table") + '.' + prop.getProperty("db_company_col_id"));
            sb.append(" WHERE ");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_id"));
            sb.append("=?;");

            READ_BY_ID = sb.toString();
            //DELETE
            sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(prop.getProperty("db_computer_table"));
            sb.append(" WHERE ");
            sb.append(prop.getProperty("db_computer_col_id"));
            sb.append("=?;");

            DELETE = sb.toString();
            //UPDATE
            sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(prop.getProperty("db_computer_table"));
            sb.append(" SET ");
            sb.append(prop.getProperty("db_computer_col_name") + "=?,");
            sb.append(prop.getProperty("db_computer_col_introduced") + "=?,");
            sb.append(prop.getProperty("db_computer_col_discontinued") + "=?,");
            sb.append(prop.getProperty("db_computer_col_company_id") + "=?");
            sb.append(" WHERE ");
            sb.append(prop.getProperty("db_computer_col_id") + "=?;");

            UPDATE = sb.toString();
            //READ_ALL_LIMIT
            sb = new StringBuilder();

            sb.append("SELECT ");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_id") + ",");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_name") + ",");
            sb.append(prop.getProperty("db_computer_col_introduced") + ",");
            sb.append(prop.getProperty("db_computer_col_discontinued") + ",");
            sb.append(prop.getProperty("db_computer_col_company_id") + ",");
            sb.append(prop.getProperty("db_company_table") + '.' + prop.getProperty("db_company_col_name"));
            sb.append(" AS company_name FROM ");
            sb.append(prop.getProperty("db_computer_table"));
            sb.append(" LEFT JOIN ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" ON ");
            sb.append(prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_company_id"));
            sb.append('=');
            sb.append(prop.getProperty("db_company_table") + '.' + prop.getProperty("db_company_col_id"));
            sb.append( " WHERE " + prop.getProperty("db_computer_table") + '.' + prop.getProperty("db_computer_col_name") + " LIKE ?");
            sb.append(" LIMIT ?,?;");

            READ_ALL_LIMIT = sb.toString();
            //ROW_COUNT

            sb = new StringBuilder();
            sb.append("SELECT COUNT(*) c FROM ");
            sb.append(prop.getProperty("db_computer_table") + " WHERE NAME LIKE ?;");

            ROW_COUNT = sb.toString();

        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    }

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());



    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#create(java.util.Optional)
     */
    @Override
    public void create(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }
        Computer c = e.get();
        
        if (!c.getName().isPresent()) {
            LOGGER.warn("Create failed, empty name");
            return;
        }
        
        if(c.getCompany().isPresent()) {
            jdbcTemplateObject.update(INSERT, c.getName().get(), c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null),c.getCompany().get().getId());
        } else {
            jdbcTemplateObject.update(INSERT, c.getName().get(), c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null),null);
        }
        
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Computer> readById(long id) {
        Computer c = jdbcTemplateObject.queryForObject(READ_BY_ID, new ComputerMapper(), id);
        return Optional.of(c);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }
        
       Computer c = e.get();
       
       if (!c.getName().isPresent()) {
           LOGGER.warn("Create failed, Empty name");
           return;
       }
       if ( c.getCompany().isPresent()) {
           jdbcTemplateObject.update(UPDATE, c.getName().get(), c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null), c.getCompany().get().getId(), c.getId());
       } else {
           jdbcTemplateObject.update(UPDATE, c.getName().get(), c.getIntroduced().orElse(null), c.getDiscontinued().orElse(null), null, c.getId());
       }
      
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#delete(java.util.Optional)
     */
    @Override
    public void delete(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }
            
        jdbcTemplateObject.update(DELETE, e.get().getId());
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter) { 
        return jdbcTemplateObject.query(READ_ALL_LIMIT, new ComputerMapper(),filter, offset, limit);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#rowCount(java.lang.String)
     */
    @Override
    public int rowCount(String filter) {
        return jdbcTemplateObject.queryForObject(ROW_COUNT, Integer.class, filter);
    }


}
