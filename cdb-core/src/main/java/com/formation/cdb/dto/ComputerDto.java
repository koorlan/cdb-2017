package com.formation.cdb.dto;

import java.time.LocalDate;

import com.formation.cdb.dto.CompanyDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDto.
 */
public class ComputerDto  {

    /** The id. */
    private long id;

    /** The name. */
    private String name;

    /** The introduced. */    

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate introduced;

    /** The discontinued. */
    private LocalDate discontinued;

    /** The company. */
    private CompanyDto company;
    
    public ComputerDto(){
    }
    
    /**
     * Instantiates a new computer dto.
     *
     * @param builder the builder
     */
    private ComputerDto(ComputerDtoBuilder builder) {
        super();
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }
    
    @JsonIgnore
    public boolean isNew(){
        return id == 0;
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the introduced.
     *
     * @return the introduced
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Gets the discontinued.
     *
     * @return the discontinued
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public CompanyDto getCompany() {
        return company;
    }
 
    /**
     * The Class ComputerDtoBuilder.
     */
    public static class ComputerDtoBuilder {
        
        /** The id. */
        private final long id;
        
        /** The name. */
        private final String name;
        
        /** The introduced. */
        private LocalDate introduced;
        
        /** The discontinued. */
        private LocalDate discontinued;
        
        /** The company. */
        private CompanyDto company;
        
        /**
         * Instantiates a new computer dto builder.
         *
         * @param id the id
         * @param name the name
         */
        public ComputerDtoBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        /**
         * With introduced.
         *
         * @param introduced the introduced
         * @return the computer dto builder
         */
        public ComputerDtoBuilder withIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }
        
        /**
         * With discontinued.
         *
         * @param discontinued the discontinued
         * @return the computer dto builder
         */
        public ComputerDtoBuilder withDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        /**
         * With company.
         *
         * @param company the company
         * @return the computer dto builder
         */
        public ComputerDtoBuilder withCompany(CompanyDto company) {
            this.company = company;
            return this;
        }
        
        /**
         * Builds the.
         *
         * @return the computer dto
         */
        public ComputerDto build() {
            return new ComputerDto(this);
        }
        
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", company=" + company + "]";
    }
    
    
    
}
