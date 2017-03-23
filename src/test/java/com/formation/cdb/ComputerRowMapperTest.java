// package com.formation.cdb;
//
//
// import static org.junit.Assert.*;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;
// import java.util.Properties;
//
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;
// import org.mockito.Mockito;
//
// import com.formation.cdb.entity.impl.Computer;
// import com.formation.cdb.mapper.impl.ComputerRowMapper;
// import com.formation.cdb.persistence.connection.ConnectionManager;
// import com.formation.cdb.util.DateUtil;
//
//
// // TODO: Auto-generated Javadoc
// /**
//  * The Class ComputerRowMapperTest.
//  */
// public class ComputerRowMapperTest {
//
//     /** The result set. */
//     private ResultSet resultSet;
//
//     /** The col id. */
//     private static String COL_ID;
//
//     /** The col name. */
//     private static String COL_NAME;
//
//     /** The col introduced. */
//     private static String COL_INTRODUCED;
//
//     /** The col discontinued. */
//     private static String COL_DISCONTINUED;
//
//     /** The col company id. */
//     private static String COL_COMPANY_ID;
//
//     /**
//      * Inits the class.
//      *
//      * @throws IOException Signals that an I/O exception has occurred.
//      */
//     @BeforeClass
//     public static void initClass() throws IOException{
//         String filename = "config.properties";
//         Properties prop = new Properties();
//         InputStream input = null;
//         input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
//         assertNotNull(input);
//         prop.load(input);
//         COL_ID = prop.getProperty("db_computer_col_id");
//         COL_NAME = prop.getProperty("db_computer_col_name");
//         COL_INTRODUCED = prop.getProperty("db_computer_col_introduced");
//         COL_DISCONTINUED = prop.getProperty("db_computer_col_discontinued");
//         COL_COMPANY_ID = prop.getProperty("db_computer_col_company_id");
//
//     }
//
//     /**
//      * Before each test.
//      */
//     @Before
//     public void beforeEachTest() {
//         resultSet = Mockito.mock(ResultSet.class);
//     }
//
//     /**
//      * Test map object from one row.
//      *
//      * @throws SQLException the SQL exception
//      */
//     @Test
//     public void testMapObjectFromOneRow() throws SQLException{
//        Mockito.when(resultSet.getRow()).thenReturn(1);
//
//        Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1l);
//        Mockito.when(resultSet.getString(COL_NAME)).thenReturn("computer_test");
//        Mockito.when(resultSet.getTimestamp(COL_INTRODUCED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2001, 10, 2))) ;
//        Mockito.when(resultSet.getTimestamp(COL_DISCONTINUED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2010, 2, 10))) ;
//        Mockito.when(resultSet.getLong(COL_COMPANY_ID)).thenReturn(2l);
//        Mockito.when(resultSet.getString("company_name")).thenReturn("company_test");
//
//        Optional<Computer> computerO = ComputerRowMapper.INSTANCE.mapObjectFromOneRow(Optional.of(resultSet));
//        assertTrue(computerO.isPresent());
//        Computer computer = computerO.get();
//        assertEquals(computer.getId(),1l);
//        assertTrue(computer.getName().isPresent());
//        assertEquals(computer.getName().get(),"computer_test");
//        assertTrue(computer.getIntroduced().isPresent());
//        assertEquals(computer.getIntroduced().get(),LocalDate.of(2001, 10, 2));
//        assertTrue(computer.getDiscontinued().isPresent());
//        assertEquals(computer.getDiscontinued().get(),LocalDate.of(2010, 2, 10));
//        assertTrue(computer.getCompany().isPresent());
//        assertEquals(computer.getCompany().get().getId(),2l);
//        assertTrue(computer.getCompany().get().getName().isPresent());
//        assertEquals(computer.getCompany().get().getName().get(),"company_test");
//     }
//
//     /**
//      * Test map list of objects from multiple rows.
//      *
//      * @throws SQLException the SQL exception
//      */
//     @Test
//     public void testMapListOfObjectsFromMultipleRows() throws SQLException{
//
//         Mockito.when(resultSet.getRow()).thenReturn(2);
//         Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//
//         Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1l);
//         Mockito.when(resultSet.getString(COL_NAME)).thenReturn("computer_test");
//         Mockito.when(resultSet.getTimestamp(COL_INTRODUCED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2001, 10, 2))) ;
//         Mockito.when(resultSet.getTimestamp(COL_DISCONTINUED)).thenReturn(DateUtil.dateToTimestamp(LocalDate.of(2010, 2, 10))) ;
//         Mockito.when(resultSet.getLong(COL_COMPANY_ID)).thenReturn(2l);
//         Mockito.when(resultSet.getString("company_name")).thenReturn("company_test");
//
//
//         List<Computer> computerList = ComputerRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(Optional.of(resultSet));
//         assertEquals(computerList.size(),2);
//         for (Computer computer: computerList) {
//
//             assertEquals(computer.getId(),1l);
//             assertTrue(computer.getName().isPresent());
//             assertEquals(computer.getName().get(),"computer_test");
//             assertTrue(computer.getIntroduced().isPresent());
//             assertEquals(computer.getIntroduced().get(),LocalDate.of(2001, 10, 2));
//             assertTrue(computer.getDiscontinued().isPresent());
//             assertEquals(computer.getDiscontinued().get(),LocalDate.of(2010, 2, 10));
//             assertTrue(computer.getCompany().isPresent());
//             assertEquals(computer.getCompany().get().getId(),2l);
//             assertTrue(computer.getCompany().get().getName().isPresent());
//             assertEquals(computer.getCompany().get().getName().get(),"company_test");
//         }
//
//
//     }
//
// }
