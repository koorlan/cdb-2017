/**
 * Package Entity.impl.
 * Implementation of @Entity interface located at @entities.
 */
package com.formation.cdb.entity.impl;

import java.time.LocalDate;
import java.util.Optional;

import com.formation.cdb.entity.Entity;

/**
 * Company class is a representation of what a company look like on database.
 * @author excilys
 * @version 0.0.1
 */
public class Computer implements Entity {
    /**
     * id of the computer in the database.
     */
    long id;
    /**
     * Name of a computer in the database.
     */
    private String name;
    /**
     * The data when the computer have been introduced.
     */
    private LocalDate introduced;
    /**
     * The date when the computer have been discontinued of the marker.
     */
    private LocalDate discontinued;
    /**
     * The company object known as the manufacturer of the computer.
     */
    private Company company;

   /**
    * Constructor of computer.
    * @param id the id of computer.
    * @param name the name of the computer.
    * @param introduced date when the computer was introduced on the market.
    * @param discontinued Date when the manufacturer have discontinued the computer.
    * @param company The manufacturer of the computer.
    */
    public Computer(final long id,
            final String name,
            final LocalDate introduced,
            final LocalDate discontinued,
            final Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    /**
     * Getter for the computer identifier.
     * @return the id of a computer
     */
    public long getId() {
        return id;
    }

    public final Optional<String> getName() {
        return Optional.ofNullable(name);
    }
    public final Optional<LocalDate> getIntroduced() {
        return Optional.ofNullable(introduced);
    }
    public final Optional<LocalDate> getDiscontinued() {
        return Optional.ofNullable(discontinued);
    }
    public final Optional<Company> getCompany() {
        return Optional.ofNullable(company);
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setIntroduced(final LocalDate introduced) {
        this.introduced = introduced;
    }

    public final void setDiscontinued(final LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public final void setCompany(final Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(name + " (id: " + id + ")");

        if (company != null) {
            sb.append(newLine);
            sb.append("| Company -> " + company);

        }
        if (introduced != null) {
            sb.append(newLine);
            sb.append("| Introduced -> " + introduced);

        }
        if (discontinued != null) {
            sb.append(newLine);
            sb.append("| Discontinued -> " + discontinued);
        }

        return sb.toString();
    }


}
