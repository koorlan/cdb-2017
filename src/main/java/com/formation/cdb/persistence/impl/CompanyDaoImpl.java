package com.formation.cdb.persistence.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;

public enum CompanyDaoImpl implements Dao<Company> {

    INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    String READ_BY_ID = "SELECT * FROM company WHERE id=?";
    String READ_ALL_LIMIT = "SELECT * FROM company LIMIT ?,?";
    String ROW_COUNT = "SELECT COUNT(*) c FROM company";
    /**
     * Private constructor for Singleton Implementation.
     */
    CompanyDaoImpl() {
      //construct queries from configuration file;
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
            //READ_BY_ID
            sb.append("SELECT * FROM ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" WHERE ");
            sb.append(prop.getProperty("db_company_id"));
            sb.append("=?;");

            READ_BY_ID = sb.toString();

            //READ_ALL_LIMIT

            sb = new StringBuilder();
            sb.append("SELECT * FROM ");
            sb.append(prop.getProperty("db_company_table"));
            sb.append(" LIMIT ?,?;");

            READ_ALL_LIMIT = sb.toString();
            //ROW_COUNT
            sb = new StringBuilder();
            sb.append("SELECT COUNT(*) c FROM ");
            sb.append(prop.getProperty("db_company_table") + ";");

            READ_ALL_LIMIT = sb.toString();
        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    }

    @Override
    public void create(Optional<Company> e) {
        LOGGER.warn("Method Create is not implemented");
    }

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
            return company;

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
    }

    @Override
    public void update(Optional<Company> e) {
        LOGGER.warn("Method update is not implemented");
    }

    @Override
    public void delete(Optional<Company> e) {
        LOGGER.warn("Method delete is not implemented");
    }

    @Override
    public Optional<List<Optional<Company>>> readAllWithOffsetAndLimit(int offset, int limit) {

        if (offset < 0 || limit < 0) {
            LOGGER.warn("Offset and limit must be positive. Offset:" + offset + " Limit:" + limit);
            return Optional.empty();
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return Optional.empty();
        }

        try {
            PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
            stmt.setInt(1, offset);
            stmt.setInt(2, limit);

            Optional<ResultSet> rs;
            rs = Optional.ofNullable(stmt.executeQuery());

            Optional<List<Optional<Company>>> companies = CompanyRowMapper.INSTANCE
                    .mapListOfObjectsFromMultipleRows(rs);

            return companies;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int rowCount() {
        int count = 0;
        Optional<ResultSet> rs;
        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return 0;
        }

        try {
            rs = Optional.ofNullable(connection.get().prepareStatement(ROW_COUNT).executeQuery());
            count = RowMapper.mapCountResult(rs);
            return count;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    };

}
