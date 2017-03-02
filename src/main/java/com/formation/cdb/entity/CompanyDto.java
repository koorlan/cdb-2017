package com.formation.cdb.entity;

public class CompanyDto {
    private final long id;
    private final String name;

    private CompanyDto(CompanyDtoBuilder builder) {
        super();
        this.id = builder.id;
        this.name = builder.name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static class CompanyDtoBuilder {
        long id;
        private final String name;
        
        public CompanyDtoBuilder(String name) {
            this.name = name;
        }
        
        public CompanyDtoBuilder id(long id) {
            this.id = id;
            return this;
        }
                
        public CompanyDto build() {
            return new CompanyDto(this);
        }
    }
    
}
