package com.formation.cdb;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

import static com.formation.cdb.ui.Control.isValidComputer;
import static com.formation.cdb.ui.Control.isValidStringDate;

import org.junit.Test;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlTest.
 */
public class ControlTest {

    /**
     * Checks if is valid computer test with optional empty.
     */
    @Test
    public void isValidComputerTestWithOptionalEmpty() {
        assertFalse(isValidComputer(Optional.empty()));
    }

    /**
     * Checks if is valid computer test with optional null.
     */
    @Test
    public void isValidComputerTestWithOptionalNull() {
        assertFalse(isValidComputer(Optional.ofNullable(null)));
    }

    /**
     * Checks if is valid computer test with zero id.
     */
    @Test
    public void isValidComputerTestWithZeroId() {
        Computer c = new Computer.ComputerBuilder(0, "test").build();
        assertFalse(isValidComputer(Optional.of((Computer) c)));
    }

    /**
     * Checks if is valid computer with negative id.
     */
    @Test
    public void isValidComputerWithNegativeId() {
        Computer c = new Computer.ComputerBuilder(-1, "test").build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with discontinued date before introduced date.
     */
    @Test
    public void isValidComputerWithDiscontinuedDateBeforeIntroducedDate() {
        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011,1,1))
                .withDiscontinued(LocalDate.of(2010,1,1))
                .withCompany(null)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with null name.
     */
    @Test
    public void isValidComputerWithNullName() {
        Computer c = new Computer.ComputerBuilder(1, null)
                .withIntroduced(null)
                .withDiscontinued(null)
                .withCompany(null)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }
    
    /**
     * Checks if is valid computer with empty name.
     */
    @Test
    public void isValidComputerWithEmptyName() {
        Computer c = new Computer.ComputerBuilder(1, "")
                .withIntroduced(null)
                .withDiscontinued(null)
                .withCompany(null)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer valid.
     */
    @Test
    public void isValidComputerValid() {
        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(null)
                .withDiscontinued(null)
                .withCompany(null)
                .build();
        assertTrue(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with valid introduced date.
     */
    @Test
    public void isValidComputerWithValidIntroducedDate() {
        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011,1,1))
                .withDiscontinued(null)
                .withCompany(null)
                .build();
        assertTrue(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with valid discontinued date.
     */
    @Test
    public void isValidComputerWithValidDiscontinuedDate() {
        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(null)
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(null)
                .build();
        assertTrue(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with all fields except company.
     */
    @Test
    public void isValidComputerWithAllFieldsExceptCompany() {
        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2010, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(null)
                .build();
        assertTrue(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with invalid company id negative.
     */
    @Test
    public void isValidComputerWithInvalidCompanyIdNegative() {
        Company cy = new Company.CompanyBuilder(-1, "company").build();

        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(cy)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with invalid company id zero.
     */
    @Test
    public void isValidComputerWithInvalidCompanyIdZero() {
        Company cy = new Company.CompanyBuilder(0, "company").build();

        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(cy)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with invalid company name null.
     */
    @Test
    public void isValidComputerWithInvalidCompanyNameNull() {
        Company cy = new Company.CompanyBuilder(1, null).build();

        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(cy)
                .build();
        assertFalse(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid computer with invalid company name empty.
     */
    @Test
    public void isValidComputerWithInvalidCompanyNameEmpty() {
        Company cy = new Company.CompanyBuilder(1, "").build();

        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2011, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(cy)
                .build();

        assertFalse(isValidComputer(Optional.of(c)));
    }
    
    /**
     * Checks if is valid computer with all fields including valid company.
     */
    @Test
    public void isValidComputerWithAllFieldsIncludingValidCompany() {
        Company cy = new Company.CompanyBuilder(1, "company").build();

        Computer c = new Computer.ComputerBuilder(1, "test")
                .withIntroduced(LocalDate.of(2010, 1, 1))
                .withDiscontinued(LocalDate.of(2011, 1, 1))
                .withCompany(cy)
                .build();
        assertTrue(isValidComputer(Optional.of(c)));
    }

    /**
     * Checks if is valid string date test with optional empty.
     */
    @Test
    public void isValidStringDateTestWithOptionalEmpty() {
        assertFalse(isValidStringDate(Optional.empty()));
    }

    /**
     * Checks if is valid string date test with valid date.
     */
    @Test
    public void isValidStringDateTestWithValidDate() {
        assertTrue(isValidStringDate(Optional.of("02-10-1994")));
    }

    /**
     * Checks if is valid string date test with invalid month.
     */
    @Test
    public void isValidStringDateTestWithInvalidMonth() {
        assertFalse(isValidStringDate(Optional.of("02-13-1994")));
    }

    /**
     * Checks if is valid string date test with invalid day.
     */
    @Test
    public void isValidStringDateTestWithInvalidDay() {
        assertFalse(isValidStringDate(Optional.of("32-10-1994")));
    }

    /**
     * Checks if is valid string date test with invalid format alpha.
     */
    @Test
    public void isValidStringDateTestWithInvalidFormatAlpha() {
        assertFalse(isValidStringDate(Optional.of("aaaaaaa")));
    }

    /**
     * Checks if is valid string date test with invalid format numeric.
     */
    @Test
    public void isValidStringDateTestWithInvalidFormatNumeric() {
        assertFalse(isValidStringDate(Optional.of("123456789")));
    }

    /**
     * Checks if is valid string date test with valid format alpha.
     */
    @Test
    public void isValidStringDateTestWithValidFormatAlpha() {
        assertFalse(isValidStringDate(Optional.of("bb-cc-dddd")));
    }

    /**
     * Checks if is valid string date test with null optional.
     */
    @Test
    public void isValidStringDateTestWithNullOptional() {
        assertFalse(isValidStringDate(Optional.ofNullable(null)));
    }
}
