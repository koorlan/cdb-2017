package com.formation.cdb.persistence.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyDaoImpl.
 */
@Named
public class CompanyDaoImpl implements Dao<Company> {


    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    /** The read by id. */
    String READ_BY_ID;
    
    /** The read all limit. */
    String READ_ALL_LIMIT;
    
    /** The row count. */
    String ROW_COUNT;

    /**
     * Private constructor for Singleton Implementation.
     */
    public CompanyDaoImpl() {
        // construct queries from configuration file;
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            LOGGER.error("Sorry, unable to find " + filename);
            throw new PersistenceException("Unable to acces config file at " + filename);
        }

        try {
            prop.load(input);
            StringBuilder sb = new StringBuilder();
            // READ_BY_ID
            sb.append("SELECT * FROM ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" WHERE ");
            sb.append(prop.getProperty("db_company_col_id"));
            sb.append("=?;");
            READ_BY_ID = sb.toString();
            // READ_ALL_LIMIT

            sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" LIMIT ?,?;");

            READ_ALL_LIMIT = sb.toString();
            // ROW_COUNT
            sb = new StringBuilder();
            sb.append("SELECT COUNT(*) c FROM ");
            sb.append(prop.getProperty("db_company_table") + ";");

            ROW_COUNT = sb.toString();
        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#create(java.util.Optional)
     */
    @Override
    public void create(Optional<Company> company) {
        LOGGER.warn("Method Create is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Company> readById(long id) {

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return Optional.empty();
        }

        try {

            PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
            stmt.setLong(1, id);
            Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
            Optional<Company> company = CompanyRowMapper.INSTANCE.mapObjectFromOneRow(rs);
            stmt.close();
            return company;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Company> company) {
        LOGGER.warn("Method update is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#delete(java.util.Optional)
     */
    @Override
    public void delete(Optional<Company> company) {
        LOGGER.warn("Method delete is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        
        List<Company> companies = new ArrayList<>();
        
        if (offset < 0 || limit < 0) {
            LOGGER.warn("Offset and limit must be positive. Offset:" + offset + " Limit:" + limit);
            return companies;
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return companies;
        }

        try {
            PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
            stmt.setInt(1, offset);
            stmt.setInt(2, limit);

            Optional<ResultSet> rs;
            rs = Optional.ofNullable(stmt.executeQuery());

            companies = CompanyRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(rs);
            stmt.close();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        
        return companies;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.persistence.Dao#rowCount(java.lang.String)
     */
    @Override
    public int rowCount(String filter) {
        int count = 0;
        Optional<ResultSet> rs;
        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return 0;
        }

        try {
            PreparedStatement stmt = connection.get().prepareStatement(ROW_COUNT);
            rs = Optional.ofNullable(stmt.executeQuery());
            count = RowMapper.mapCountResult(rs);
            stmt.close();
            return count;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
    };

}
