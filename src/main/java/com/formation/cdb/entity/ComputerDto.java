package com.formation.cdb.entity;


// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDto.
 */
public class ComputerDto {

    /** The id. */
    private final long id;

    /** The name. */
    private final String name;

    /** The introduced. */
    private final String introduced;

    /** The discontinued. */
    private final String discontinued;

    /** The company. */
    private final CompanyDto company;

    /**
     * Instantiates a new computer dto.
     *
     * @param builder the builder
     */
    public ComputerDto(ComputerDtoBuilder builder) {
        super();
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
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
    public String getIntroduced() {
        return introduced;
    }

    /**
     * Gets the discontinued.
     *
     * @return the discontinued
     */
    public String getDiscontinued() {
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
        private String introduced;
        
        /** The discontinued. */
        private String discontinued;
        
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
        public ComputerDtoBuilder withIntroduced(String introduced) {
            this.introduced = introduced;
            return this;
        }
        
        /**
         * With discontinued.
         *
         * @param discontinued the discontinued
         * @return the computer dto builder
         */
        public ComputerDtoBuilder withDiscontinued(String discontinued) {
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
    
}
