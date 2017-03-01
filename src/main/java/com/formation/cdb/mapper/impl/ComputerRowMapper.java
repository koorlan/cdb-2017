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
    public Optional<List<Optional<Computer>>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {
        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            return Optional.empty();
        }

        try {

            ResultSet r = rs.get();
            List<Optional<Computer>> computers = new ArrayList<>();
            while (r.next()) {

                long id = r.getLong(COL_ID);
                String name = r.getString(COL_NAME);
                LocalDate introduced = Optional.ofNullable(r.getTimestamp(COL_INTRODUCED)).map(Timestamp::toLocalDateTime)
                        .map(LocalDateTime::toLocalDate).orElse(null);
                LocalDate discontinued = Optional.ofNullable(r.getTimestamp(COL_DISCONTINUED))
                        .map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).orElse(null);

                long companyId = r.getLong(COL_COMPANY_ID);

                Optional<Company> company = (companyId != 0)
                        ? Optional.of(new Company(companyId, r.getString("c_name"))) : Optional.empty();

                Computer computer = new Computer(id, name, introduced, discontinued, company.orElse(null));

                computers.add(Optional.ofNullable(computer));
            }

            return Optional.ofNullable(computers);
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return Optional.empty();
    }

    @Override
    public Optional<Computer> mapObjectFromOneRow(Optional<ResultSet> rs) {

        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
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

            Optional<Company> company = (companyId != 0) ? Optional.of(new Company(companyId, r.getString("c_name")))
                    : Optional.empty();
            Computer computer = new Computer(id, name, introduced, discontinued, company.orElse(null));
            return Optional.ofNullable(computer);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return Optional.empty();
    }

}
