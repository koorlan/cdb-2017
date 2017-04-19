package com.formation.cdb.dto;


import java.util.List;

public class CompaniesApiDto {
    private List<CompanyDto> companies;
    private int total;

    public void setCompanies(List<CompanyDto> companies) {
        this.companies = companies;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CompanyDto> getCompanies() {
        return companies;
    }

    public int getTotal() {
        return total;
    }
}
