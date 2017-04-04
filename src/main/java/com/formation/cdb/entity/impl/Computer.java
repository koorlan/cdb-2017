/**
 * Package Entity.impl.
 * Implementation of @Entity interface located at @entities.
 */
package com.formation.cdb.entity.impl;

import java.time.LocalDate;
import java.util.Optional;

import com.formation.cdb.entity.Entity;

// TODO: Auto-generated Javadoc
/**
 * Company class is a representation of what a company look like on database.
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
     * @param builder the builder
     */
    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }
    
    public boolean isNew() {
        return this.id == 0;
    }

    /**
     * Getter for the computer identifier.
     * @return the id of a computer
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    /**
     * Gets the introduced.
     *
     * @return the introduced
     */
    public final Optional<LocalDate> getIntroduced() {
        return Optional.ofNullable(introduced);
    }

    /**
     * Gets the discontinued.
     *
     * @return the discontinued
     */
    public final Optional<LocalDate> getDiscontinued() {
        return Optional.ofNullable(discontinued);
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public final Optional<Company> getCompany() {
        return Optional.ofNullable(company);
    }

    /**
     * The Class ComputerBuilder.
     */
    public static class ComputerBuilder {

        /** The id. */
        private final long id;

        /** The name. */
        private final String name;
        /** The introduced. */
        private LocalDate introduced;

        /** The discontinued. */
        private LocalDate discontinued;

        /** The company. */
        private Company company;

        /**
         * Instantiates a new computer builder.
         *
         * @param id the id
         * @param name the name
         */
        public ComputerBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * With introduced.
         *
         * @param introduced the introduced
         * @return the computer builder
         */
        public ComputerBuilder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * With discontinued.
         *
         * @param discontinued the discontinued
         * @return the computer builder
         */
        public ComputerBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * With company.
         *
         * @param company the company
         * @return the computer builder
         */
        public ComputerBuilder withCompany(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Builds the.
         *
         * @return the computer
         */
        public Computer build() {
            return new Computer(this);
        }

    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Entity#toString()
     */
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

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Entity#hashCode()
     */
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

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Entity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
