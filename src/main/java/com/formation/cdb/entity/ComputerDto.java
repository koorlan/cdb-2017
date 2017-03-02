package com.formation.cdb.entity;

import java.time.LocalDate;


public class ComputerDto {
    
    private final long id;
    private final String name;
    private final String introduced;
    private final String discontinued;
    private final CompanyDto company;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public ComputerDto(ComputerDtoBuilder builder) {
        super();
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }
    
    
    public static class ComputerDtoBuilder {
        private final long id;
        private final String name;
        private String introduced;
        private String discontinued;
        private CompanyDto company;
        
        public ComputerDtoBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public ComputerDtoBuilder introduced(String introduced) {
            this.introduced = introduced;
            return this;
        }
        
        public ComputerDtoBuilder discontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }
        
        public ComputerDtoBuilder company(CompanyDto company) {
            this.company = company;
            return this;
        }
        
        public ComputerDto build() {
            return new ComputerDto(this);
        }
        
    }
    
}
