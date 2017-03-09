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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;
import com.formation.cdb.util.DateUtil;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.ComputerRowMapper;

public enum ComputerDaoImpl implements Dao<Computer> {

    INSTANCE;

    String INSERT;
    String READ_BY_ID;
    String UPDATE;
    String DELETE;
    String READ_ALL_LIMIT;
    String ROW_COUNT;
    /**
     * Private constructor for Singleton Implementation.
     */
    ComputerDaoImpl() {
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
            sb.append(" AS c_name FROM ");
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
            sb.append(" AS c_name FROM ");
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

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());



    @Override
    public void create(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return;
        }

        try {
            PreparedStatement stmt = connection.get().prepareStatement(INSERT);
            stmt.setString(1, e.flatMap(Computer::getName).orElseThrow(() -> new PersistenceException("Trying to bypass validation, name is required")));

            stmt.setTimestamp(2, e.flatMap(Computer::getIntroduced).map(DateUtil::dateToTimestamp).orElse(null));

            stmt.setTimestamp(3, e.flatMap(Computer::getDiscontinued).map(DateUtil::dateToTimestamp).orElse(null));

            if (e.get().getCompany().isPresent()) {
                stmt.setLong(4, e.flatMap(Computer::getCompany).map(Company::getId).orElse(Long.valueOf(Types.NULL)));
            } else {
               stmt.setNull(4, Types.NULL); 
            }

            stmt.execute();
            LOGGER.info("Query sucessfully executed " + stmt.toString());
        } catch (SQLException e1) {
            LOGGER.info("Create failed " + e.orElse(null));
            throw new PersistenceException(e1);
        } finally {
            ConnectionManager.close(connection);
        }

    }

    @Override
    public Optional<Computer> readById(long id) {

        if (id <= 0) {
            LOGGER.warn("Id can't be negative or equal 0");
            return Optional.empty();
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return Optional.empty();
        }

        try {

            PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
            stmt.setInt(1, (int) id);
            Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
            LOGGER.info("Try to read id: " + id);
            Optional<Computer> computer = ComputerRowMapper.INSTANCE.mapObjectFromOneRow(rs);
            LOGGER.info("Sucessfully readed: " + computer.toString());
            return computer;

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
    }

    @Override
    public void update(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return;
        }
       
        try {
            
            PreparedStatement stmt = connection.get().prepareStatement(UPDATE);
                
            stmt.setString(1, e.flatMap(Computer::getName).orElseThrow(() -> new PersistenceException("Trying to bypass validation, name is required")));

            stmt.setTimestamp(2, e.flatMap(Computer::getIntroduced).map(DateUtil::dateToTimestamp).orElse(null));

            stmt.setTimestamp(3, e.flatMap(Computer::getDiscontinued).map(DateUtil::dateToTimestamp).orElse(null));

            if (e.get().getCompany().isPresent()) {
                stmt.setLong(4, e.flatMap(Computer::getCompany).map(Company::getId).get());
            } else {
                stmt.setNull(4, Types.NULL);
            }
           
            stmt.setLong(5, e.map(Computer::getId).orElseThrow(() -> new PersistenceException("Trying to bypass validation, id is required")));

            stmt.execute();
            stmt.close();
            LOGGER.info("Sucessfully updated: " + e);
        } catch (SQLException e1) {
            LOGGER.info("Update failed " + e.orElse(null));
            throw new PersistenceException(e1);
        } finally {
            ConnectionManager.close(connection);
           
        }

    }

    @Override
    public void delete(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Create failed, null computer");
            return;
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return;
        }


        try {
            PreparedStatement stmt = connection.get().prepareStatement(DELETE);
            stmt.setLong(1, e.map(Computer::getId).orElseThrow(() -> new PersistenceException("id is less or equal zero")));
            stmt.execute();
            stmt.close();
            LOGGER.info("Succesfully deleted " + e);
        } catch (SQLException e1) {
            LOGGER.info("Delete failed " + e.orElse(null));
            throw new PersistenceException(e1);
        } finally {
            ConnectionManager.close(connection);
        }


    }

    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        
        List<Computer> computers = new ArrayList<>();
        
        if (offset < 0 || limit < 0) {
            LOGGER.warn("Offset and limit must be positive. Offset:" + offset + " Limit:" + limit);
            return computers;
        }

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return computers;
        }
        try {
            PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
            stmt.setString(1, filter);
            stmt.setInt(2, offset);
            stmt.setInt(3, limit);

            Optional<ResultSet> rs;
            rs = Optional.ofNullable(stmt.executeQuery());

            computers = ComputerRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(rs);

            stmt.close();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }
        return computers;
    }

    @Override
    public int rowCount(String filter) {
        int count = 0;

        Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

        if (!connection.isPresent()) {
            LOGGER.warn("can't get a connection");
            return 0;
        }

        try {
            Optional<ResultSet> rs;
            PreparedStatement stmt = connection.get().prepareStatement(ROW_COUNT);
            stmt.setString(1, filter);
            rs = Optional.ofNullable(stmt.executeQuery());
            count = RowMapper.mapCountResult(rs);
            stmt.close();
            return count;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            ConnectionManager.close(connection);
        }

    }


}
