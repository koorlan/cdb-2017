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
        private final long id;
        private final String name;
        
        public CompanyDtoBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
                
        public CompanyDto build() {
            return new CompanyDto(this);
        }
    }
    
}
