/**
 * Package Entity.impl.
 * Implementation of @Entity interface located at @entities.
 */
package com.formation.cdb.entity.impl;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.formation.cdb.entity.Model;


/**
 * Company class is a representation of what a company look like on database.
 *
 * @author korlan
 * @version 0.0.1
 */
@Entity
@Table(name = "company")
@NamedQueries({
    @NamedQuery(name="Company.findAllwithFilter",
                query="SELECT c FROM Company c WHERE c.name LIKE :filter"),
    @NamedQuery(name="Company.countWithFilter",
    query="SELECT count(id) FROM Company c WHERE name LIKE :filter"),
    @NamedQuery(name="Company.findById",
    query = "Select c from Company c where id = :id")
}) 
public class Company extends Model {
    /**
     * id (primary key) of a company.
     */
    @Id
    private Long id;
    /**
     * the name of the company.
     */
    
    private String name;
    
    
    public Company(){};
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
        return Optional.of(name);
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
