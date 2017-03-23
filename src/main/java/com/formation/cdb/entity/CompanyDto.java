package com.formation.cdb.entity;

import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDto.
 */
public class CompanyDto {

    /** The id. */
    private long id;
    
    /** The name. */
    private String name;
    
    public CompanyDto() {
        name = "test";
    }
    /**
     * Instantiates a new company dto.
     *
     * @param builder the builder
     */
    private CompanyDto(CompanyDtoBuilder builder) {
        super();
        this.id = builder.id;
        this.name = builder.name;
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
     * The Class CompanyDtoBuilder.
     */
    public static class CompanyDtoBuilder {

        /** The id. */
        private final long id;
        
        /** The name. */
        private final String name;

        /**
         * Instantiates a new company dto builder.
         *
         * @param id the id
         * @param name the name
         */
        public CompanyDtoBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Builds the.
         *
         * @return the company dto
         */
        public CompanyDto build() {
            return new CompanyDto(this);
        }
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    

}
