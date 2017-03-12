package com.formation.cdb.mapper.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.persistence.connection.ConnectionManager;

public enum ComputerRowMapper implements RowMapper<Computer> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerRowMapper.class);

    String COL_ID;
    String COL_NAME;
    String COL_INTRODUCED;
    String COL_DISCONTINUED;
    String COL_COMPANY_ID;
    /**
     * Constructor private for singleton implementation.
     */
    ComputerRowMapper() {
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
            COL_ID = prop.getProperty("db_computer_col_id");
            COL_NAME = prop.getProperty("db_computer_col_name");
            COL_INTRODUCED = prop.getProperty("db_computer_col_introduced");
            COL_DISCONTINUED = prop.getProperty("db_computer_col_discontinued");
            COL_COMPANY_ID = prop.getProperty("db_computer_col_company_id");
        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    };

    @Override
    public List<Computer> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {
        
        List<Computer> computers = new ArrayList<>();
        
        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            return computers;
        }

        try {

            ResultSet r = rs.get();
            while (r.next()) {

                long id = r.getLong(COL_ID);
                String name = r.getString(COL_NAME);
                LocalDate introduced = Optional.ofNullable(r.getTimestamp(COL_INTRODUCED)).map(Timestamp::toLocalDateTime)
                        .map(LocalDateTime::toLocalDate).orElse(null);
                LocalDate discontinued = Optional.ofNullable(r.getTimestamp(COL_DISCONTINUED))
                        .map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).orElse(null);

                long companyId = r.getLong(COL_COMPANY_ID);
                String companyName = r.getString("c_name");
                
                Optional<Computer> computer = constructComputerFromResultSetvalues(id, name, introduced, discontinued, companyId, companyName);
                
                if ( computer.isPresent() ) {
                    computers.add(computer.get());
                }
            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return computers;
    }

    @Override
    public Optional<Computer> mapObjectFromOneRow(Optional<ResultSet> rs) {

        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            LOGGER.error("map 1 object, failed, ResultSet present : " + rs.isPresent() + " rowsOfRs: " + RowMapper.countRowsOfResultSet(rs));
            return Optional.empty();
        }

        try {

            ResultSet r = rs.get();
            r.next();

            long id = r.getLong(COL_ID);
            String name = r.getString(COL_NAME);
            LocalDate introduced = Optional.ofNullable(r.getTimestamp(COL_INTRODUCED)).map(Timestamp::toLocalDateTime)
                    .map(LocalDateTime::toLocalDate).orElse(null);
            LocalDate discontinued = Optional.ofNullable(r.getTimestamp(COL_DISCONTINUED)).map(Timestamp::toLocalDateTime)
                    .map(LocalDateTime::toLocalDate).orElse(null);

            long companyId = r.getLong(COL_COMPANY_ID);
            String companyName = r.getString("c_name");
            LOGGER.info("Try to map, " + id + " " + name + " "+ introduced + " "+ discontinued + " "+ companyId + " "+ companyName );
            Optional<Computer> computer = constructComputerFromResultSetvalues(id, name, introduced, discontinued, companyId, companyName);
            return computer;
        } catch (SQLException e) {
            LOGGER.error("Map object from one row failed, SQL exception");
        }

        return Optional.empty();
    }
    
    private Optional<Computer> constructComputerFromResultSetvalues(long id, String name, LocalDate introduced, LocalDate discontinued, long companyId, String companyName){
        if ( id == 0 || name == null || StringUtils.isBlank(name) ) {
            return Optional.empty();
        }
        
        Optional<Company> company;
        
        if ( companyId == 0 || companyName == null || StringUtils.isBlank(companyName)) {
            company = Optional.empty();
        } else {
            company = Optional.of(new Company.CompanyBuilder(companyId, companyName).build());
        }
        
        Computer.ComputerBuilder computerBuilder = new Computer.ComputerBuilder(id, name);
        if ( introduced != null ) {
            computerBuilder.withIntroduced(introduced);
        }
        
        if ( discontinued != null ) {
            computerBuilder.withDiscontinued(discontinued);
        }
        
        if ( company.isPresent() ) {
            computerBuilder.withCompany(company.get());
        }
        
        Computer computer = computerBuilder.build();
        return Optional.of(computer);
    }

}
