package com.formation.cdb.mapper.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.persistence.connection.ConnectionManager;

public enum CompanyRowMapper implements RowMapper<Company> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerRowMapper.class);

    String COL_ID;
    String COL_NAME;
    /**
     * Constructor private for singleton implementation.
     */
    CompanyRowMapper() {
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
            COL_ID = prop.getProperty("db_company_col_id");
            COL_NAME = prop.getProperty("db_company_col_name");
        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    };

    @Override
    public Optional<List<Optional<Company>>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {

        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            return Optional.empty();
        }

        try {

            List<Optional<Company>> companies;
            long id;
            String name;
            Company company;

            companies = new ArrayList<>();

            while (rs.get().next()) {

                id = rs.get().getLong(COL_ID);

                name = rs.get().getString(COL_NAME);
                company = new Company(id, name);

                companies.add(Optional.ofNullable(company));
            }

            return Optional.ofNullable(companies);

        } catch (SQLException e) {

            // TODO

        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> mapObjectFromOneRow(Optional<ResultSet> rs) {

        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            return Optional.empty();
        }

        try {

            ResultSet r = rs.get();
            r.next();
            long id = r.getLong(COL_ID);
            String name = r.getString(COL_NAME);
            Company company = new Company(id, name);

            return Optional.ofNullable(company);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
