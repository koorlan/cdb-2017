package com.formation.cdb.entity;

import java.time.LocalDate;


public class ComputerDto {
    long id;

    private String name;

    private LocalDate introduced;

    private LocalDate discontinued;
 
    private CompanyDto company;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public ComputerDto(long id, String name, LocalDate introduced, LocalDate discontinued, CompanyDto company) {
        super();
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }
    
    
}
