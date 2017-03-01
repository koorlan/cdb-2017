package com.formation.cdb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.persistence.connection.ConnectionManager;

public class CompanyRowMapperTest {
    private ResultSet resultSet;

    static String COL_ID;
    static String COL_NAME;

    @BeforeClass
    public static void initClass() throws IOException {
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        prop.load(input);
        COL_ID = prop.getProperty("db_company_col_id");
        COL_NAME = prop.getProperty("db_company_col_name");
    }

    @Before
    public void beforeEachTest() {
        resultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    public void testMapObjectFromOneRow() throws SQLException {
        Mockito.when(resultSet.getRow()).thenReturn(1);

        Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1L);
        Mockito.when(resultSet.getString(COL_NAME)).thenReturn("company_test");

        Optional<Company> companyO = CompanyRowMapper.INSTANCE.mapObjectFromOneRow(Optional.of(resultSet));
        assertTrue(companyO.isPresent());
        Company company = companyO.get();
        assertEquals(company.getId(), 1L);
        assertTrue(company.getName().isPresent());
        assertEquals(company.getName().get(), "company_test");

    }

    @Test
    public void testMapListOfObjectsFromMultipleRows() throws SQLException {

        Mockito.when(resultSet.getRow()).thenReturn(2);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1L);
        Mockito.when(resultSet.getString(COL_NAME)).thenReturn("company_test");

        Optional<List<Optional<Company>>> companyListO = CompanyRowMapper.INSTANCE
                .mapListOfObjectsFromMultipleRows(Optional.of(resultSet));
        assertTrue(companyListO.isPresent());
        List<Optional<Company>> companyList = companyListO.get();
        assertEquals(companyList.size(), 2);
        for (Optional<Company> cO : companyList) {
            assertTrue(cO.isPresent());
            Company company = cO.get();
            assertEquals(company.getId(), 1L);
            assertTrue(company.getName().isPresent());
            assertEquals(company.getName().get(), "company_test");
        }

    }

}
