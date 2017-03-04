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
 * 
 * @author excilys
 * @version 0.0.1
 */
public class Computer extends Entity {
    /**
     * id of the computer in the database.
     */
    private final long id;
    /**
     * Name of a computer in the database.
     */
    private final String name;
    /**
     * The data when the computer have been introduced.
     */
    private final LocalDate introduced;
    /**
     * The date when the computer have been discontinued of the marker.
     */
    private final LocalDate discontinued;
    /**
     * The company object known as the manufacturer of the computer.
     */
    private final Company company;

    /**
     * Constructor of computer.
     * 
     * @param id
     *            the id of computer.
     * @param name
     *            the name of the computer.
     * @param introduced
     *            date when the computer was introduced on the market.
     * @param discontinued
     *            Date when the manufacturer have discontinued the computer.
     * @param company
     *            The manufacturer of the computer.
     */
    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    /**
     * Getter for the computer identifier.
     * 
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
    
    public static class ComputerBuilder {
        private final long id;
        private final String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;
        
        public ComputerBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public ComputerBuilder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }
        
        public ComputerBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        public ComputerBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
        
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (discontinued == null) {
            if (other.discontinued != null)
                return false;
        } else if (!discontinued.equals(other.discontinued))
            return false;
        if (id != other.id)
            return false;
        if (introduced == null) {
            if (other.introduced != null)
                return false;
        } else if (!introduced.equals(other.introduced))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
