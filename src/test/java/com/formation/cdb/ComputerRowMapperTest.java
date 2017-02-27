package com.formation.cdb;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.impl.ComputerRowMapper;
import com.formation.cdb.persistence.connection.ConnectionManager;
import com.formation.cdb.util.DateUtil;


public class ComputerRowMapperTest {
    private ResultSet resultSet;
    
    static String COL_ID;
    static String COL_NAME;
    static String COL_INTRODUCED;
    static String COL_DISCONTINUED;
    static String COL_COMPANY_ID;
    
    @BeforeClass
    public static void initClass() throws IOException{
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
        assertNotNull(input);
        prop.load(input);
        COL_ID = prop.getProperty("db_computer_col_id");
        COL_NAME = prop.getProperty("db_computer_col_name");
        COL_INTRODUCED = prop.getProperty("db_computer_col_introduced");
        COL_DISCONTINUED = prop.getProperty("db_computer_col_discontinued");
        COL_COMPANY_ID = prop.getProperty("db_computer_col_company_id");

    }
    
    @Before
    public void beforeEachTest() {
        resultSet = Mockito.mock(ResultSet.class); 
    }
    
    @Test
    public void testMapObjectFromOneRow() throws SQLException{
       Mockito.when(resultSet.getRow()).thenReturn(1);
        
       Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1l);
       Mockito.when(resultSet.getString(COL_NAME)).thenReturn("computer_test");
       Mockito.when(resultSet.getTimestamp(COL_INTRODUCED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2001, 10, 2))) ;
       Mockito.when(resultSet.getTimestamp(COL_DISCONTINUED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2010, 2, 10))) ;
       Mockito.when(resultSet.getLong(COL_COMPANY_ID)).thenReturn(2l);
       Mockito.when(resultSet.getString("c_name")).thenReturn("company_test");
       
       Optional<Computer> computerO = ComputerRowMapper.INSTANCE.mapObjectFromOneRow(Optional.of(resultSet));
       assertTrue(computerO.isPresent());
       Computer computer = computerO.get();
       assertEquals(computer.getId(),1l);
       assertTrue(computer.getName().isPresent());
       assertEquals(computer.getName().get(),"computer_test");
       assertTrue(computer.getIntroduced().isPresent());
       assertEquals(computer.getIntroduced().get(),LocalDate.of(2001, 10, 2));
       assertTrue(computer.getDiscontinued().isPresent());
       assertEquals(computer.getDiscontinued().get(),LocalDate.of(2010, 2, 10));
       assertTrue(computer.getCompany().isPresent());
       assertEquals(computer.getCompany().get().getId(),2l);
       assertTrue(computer.getCompany().get().getName().isPresent());
       assertEquals(computer.getCompany().get().getName().get(),"company_test");
    }
    
    @Test
    public void testMapListOfObjectsFromMultipleRows() throws SQLException{

        Mockito.when(resultSet.getRow()).thenReturn(2);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        
        Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1l);
        Mockito.when(resultSet.getString(COL_NAME)).thenReturn("computer_test");
        Mockito.when(resultSet.getTimestamp(COL_INTRODUCED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2001, 10, 2))) ;
        Mockito.when(resultSet.getTimestamp(COL_DISCONTINUED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2010, 2, 10))) ;
        Mockito.when(resultSet.getLong(COL_COMPANY_ID)).thenReturn(2l);
        Mockito.when(resultSet.getString("c_name")).thenReturn("company_test");
        
        Optional<List<Optional<Computer>>> computerListO = ComputerRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(Optional.of(resultSet));
        assertTrue(computerListO.isPresent());
        List<Optional<Computer>> computerList = computerListO.get();
        assertEquals(computerList.size(),2);
        for (Optional<Computer> cO: computerList) {
            assertTrue(cO.isPresent());
            Computer computer = cO.get();
            assertEquals(computer.getId(),1l);
            assertTrue(computer.getName().isPresent());
            assertEquals(computer.getName().get(),"computer_test");
            assertTrue(computer.getIntroduced().isPresent());
            assertEquals(computer.getIntroduced().get(),LocalDate.of(2001, 10, 2));
            assertTrue(computer.getDiscontinued().isPresent());
            assertEquals(computer.getDiscontinued().get(),LocalDate.of(2010, 2, 10));
            assertTrue(computer.getCompany().isPresent());
            assertEquals(computer.getCompany().get().getId(),2l);
            assertTrue(computer.getCompany().get().getName().isPresent());
            assertEquals(computer.getCompany().get().getName().get(),"company_test");
        }
        
        
    }
    
}
