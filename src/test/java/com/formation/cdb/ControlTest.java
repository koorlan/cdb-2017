package com.formation.cdb;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Optional;

import static com.formation.cdb.ui.Control.isValidComputer;
import static com.formation.cdb.ui.Control.isValidStringDate;

import org.junit.Test;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;

public class ControlTest {

    @Test
    public void isValidComputerTestWithOptionalEmpty() {
        assertFalse(isValidComputer(Optional.empty()));
    }

    @Test
    public void isValidComputerTestWithOptionalNull() {
        assertFalse(isValidComputer(Optional.ofNullable(null)));
    }

    @Test
    public void isValidComputerTestWithZeroId() {
        Computer c = new Computer(0, "test", null, null, null);
        assertFalse(isValidComputer(Optional.of((Computer) c)));
    }

    @Test
    public void isValidComputerWithNegativeId() {
        Computer c = new Computer(-1, "test", null, null, null);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithDiscontinuedDateBeforeIntroducedDate() {
        Computer c = new Computer(1, "test", LocalDate.of(2011, 1, 1), LocalDate.of(2010, 1, 1), null);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithNullName() {
        Computer c = new Computer(1, null, null, null, null);
        assertFalse(isValidComputer(Optional.of(c)));
    }
    
    @Test
    public void isValidComputerWithEmptyName() {
        Computer c = new Computer(1, "", null, null, null);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerValid() {
        Computer c = new Computer(1, "test", null, null, null);
        assertTrue(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithValidIntroducedDate() {
        Computer c = new Computer(1, "test", LocalDate.of(2011, 1, 1), null, null);
        assertTrue(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithValidDiscontinuedDate() {
        Computer c = new Computer(1, "test", null, LocalDate.of(2011, 1, 1), null);
        assertTrue(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithAllFieldsExceptCompany() {
        Computer c = new Computer(1, "test", LocalDate.of(2010, 1, 1), LocalDate.of(2011, 1, 1), null);
        assertTrue(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithInvalidCompanyIdNegative() {
        Company cy = new Company(-1, "company");
        Computer c = new Computer(1, "test", null, null, cy);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithInvalidCompanyIdZero() {
        Company cy = new Company(0, "company");
        Computer c = new Computer(1, "test", null, null, cy);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithInvalidCompanyNameNull() {
        Company cy = new Company(1, null);
        Computer c = new Computer(1, "test", null, null, cy);
        assertFalse(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidComputerWithInvalidCompanyNameEmpty() {
        Company cy = new Company(1, "");
        Computer c = new Computer(1, "test", null, null, cy);
        assertFalse(isValidComputer(Optional.of(c)));
    }
    
    @Test
    public void isValidComputerWithAllFieldsIncludingValidCompany() {
        Company cy = new Company(1, "test");
        Computer c = new Computer(1, "test", null, null, cy);
        assertTrue(isValidComputer(Optional.of(c)));
    }

    @Test
    public void isValidStringDateTestWithOptionalEmpty() {
        assertFalse(isValidStringDate(Optional.empty()));
    }

    @Test
    public void isValidStringDateTestWithValidDate() {
        assertTrue(isValidStringDate(Optional.of("02-10-1994")));
    }

    @Test
    public void isValidStringDateTestWithInvalidMonth() {
        assertFalse(isValidStringDate(Optional.of("02-13-1994")));
    }

    @Test
    public void isValidStringDateTestWithInvalidDay() {
        assertFalse(isValidStringDate(Optional.of("32-10-1994")));
    }

    @Test
    public void isValidStringDateTestWithInvalidFormatAlpha() {
        assertFalse(isValidStringDate(Optional.of("aaaaaaa")));
    }

    @Test
    public void isValidStringDateTestWithInvalidFormatNumeric() {
        assertFalse(isValidStringDate(Optional.of("123456789")));
    }

    @Test
    public void isValidStringDateTestWithValidFormatAlpha() {
        assertFalse(isValidStringDate(Optional.of("bb-cc-dddd")));
    }

    @Test
    public void isValidStringDateTestWithNullOptional() {
        assertFalse(isValidStringDate(Optional.ofNullable(null)));
    }
}
