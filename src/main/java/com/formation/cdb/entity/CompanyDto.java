package com.formation.cdb.entity;

public class CompanyDto {
    private long id;
    
    private String name;

    
    
    public CompanyDto(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    
}
