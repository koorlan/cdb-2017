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
import com.formation.cdb.entity.impl.Company.CompanyBuilder;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.persistence.connection.ConnectionManager;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyRowMapper.
 */
public enum CompanyRowMapper implements RowMapper<Company> {

    /** The instance. */
    INSTANCE;

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerRowMapper.class);

    /** The col id. */
    String COL_ID;
    
    /** The col name. */
    String COL_NAME;
    /**
     * Constructor private for singleton implementation.
     */
    CompanyRowMapper() {
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = CompanyRowMapper.class.getClassLoader().getResourceAsStream(filename);
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

    /* (non-Javadoc)
     * @see com.formation.cdb.mapper.RowMapper#mapListOfObjectsFromMultipleRows(java.util.Optional)
     */
    @Override
    public List<Company> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {
        
        List<Company> companies =  new ArrayList<>();
        
        if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
            return companies;
        }

        try {
   
            long id;
            String name;

            while (rs.get().next()) {

               id = rs.get().getLong(COL_ID);
               name = rs.get().getString(COL_NAME);
               
               if( !(id == 0 || name == null) ) {
                   Company company = new Company.CompanyBuilder(id, name).build();
                   companies.add(company);
               }         
            }
        } catch (SQLException e) {

            // TODO

        }
        return companies;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.mapper.RowMapper#mapObjectFromOneRow(java.util.Optional)
     */
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
           
            if( !(id == 0 || name == null) ) {
                Company company = new Company.CompanyBuilder(id,name).build();
                return Optional.of(company);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
