/**
 * Package Entity.impl.
 * Implementation of @Entity interface located at @entities.
 */
package com.formation.cdb.entity.impl;

import java.util.Optional;

import com.formation.cdb.entity.Entity;

// TODO: Auto-generated Javadoc
/**
 * Company class is a representation of what a company look like on database.
 *
 * @author korlan
 * @version 0.0.1
 */
public class Company extends Entity {
    /**
     * id (primary key) of a company.
     */
    private final long id;
    /**
     * the name of the company.
     */
    private final String name;

    /**
     * Constructor of a company.
     *
     * @param builder the builder
     */
    public Company(CompanyBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    /**
     * Getter of the company name.
     * @return An @Optional of the @Company name
     */
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    /**
     * Get the @id of a @Company.
     * @return of the Company object on the database.
     */
    public long getId() {
        return id;
    }

    /**
     * The Class CompanyBuilder.
     */
    public static class CompanyBuilder {

        /** The id. */
        private final long id;

        /** The name. */
        private final String name;

        /**
         * Instantiates a new company builder.
         *
         * @param id the id
         * @param name the name
         */
        public CompanyBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Builds the.
         *
         * @return the company
         */
        public Company build() {
            return new Company(this);
        }
    }

    /**
     * Overrided method.
     * @return String representation of a @Company
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + " (id: " + id + ")");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Entity#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Company other = (Company) obj;
        if (id != other.id) {
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
