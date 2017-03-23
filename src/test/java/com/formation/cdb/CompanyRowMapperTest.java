// package com.formation.cdb;
//
//
// import static org.junit.Assert.*;
//
// import java.io.IOException;
// import java.io.InputStream;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.List;
// import java.util.Optional;
// import java.util.Properties;
//
// import org.junit.Before;
// import org.junit.BeforeClass;
// import org.junit.Test;
// import org.mockito.Mockito;
//
// import com.formation.cdb.entity.impl.Company;
//
//
// // TODO: Auto-generated Javadoc
// /**
//  * The Class CompanyRowMapperTest.
//  */
// public class CompanyRowMapperTest {
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
//     /**
//      * Inits the class.
//      *
//      * @throws IOException Signals that an I/O exception has occurred.
//      */
//     @BeforeClass
//     public static void initClass() throws IOException {
//         String filename = "config.properties";
//         Properties prop = new Properties();
//         InputStream input;
//         input = CompanyRowMapperTest.class.getClassLoader().getResourceAsStream(filename);
//         assertNotNull(input);
//         prop.load(input);
//         COL_ID = prop.getProperty("db_company_col_id");
//         COL_NAME = prop.getProperty("db_company_col_name");
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
//     public void testMapObjectFromOneRow() throws SQLException {
//         Mockito.when(resultSet.getRow()).thenReturn(1);
//
//         Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1L);
//         Mockito.when(resultSet.getString(COL_NAME)).thenReturn("company_test");
//
//         Optional<Company> companyO = CompanyRowMapper.INSTANCE.mapObjectFromOneRow(Optional.of(resultSet));
//         assertTrue(companyO.isPresent());
//         Company company = companyO.get();
//         assertEquals(company.getId(), 1L);
//         assertTrue(company.getName().isPresent());
//         assertEquals(company.getName().get(), "company_test");
//
//     }
//
//     /**
//      * Test map list of objects from multiple rows.
//      *
//      * @throws SQLException the SQL exception
//      */
//     @Test
//     public void testMapListOfObjectsFromMultipleRows() throws SQLException {
//
//         Mockito.when(resultSet.getRow()).thenReturn(2);
//         Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//
//         Mockito.when(resultSet.getLong(COL_ID)).thenReturn(1L);
//         Mockito.when(resultSet.getString(COL_NAME)).thenReturn("company_test");
//
//         List<Company> companyList = CompanyRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(Optional.of(resultSet));
//         assertEquals(companyList.size(), 2);
//         for (Company company : companyList) {
//             assertEquals(company.getId(), 1l);
//             assertTrue(company.getName().isPresent());
//             assertEquals(company.getName().get(), "company_test");
//         }
//     }
//
// }
