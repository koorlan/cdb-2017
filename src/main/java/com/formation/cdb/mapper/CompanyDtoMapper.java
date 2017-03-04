package com.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;


public class CompanyDtoMapper {
    
    public static List<CompanyDto> mapCompaniesDtoFromCompanies( List<Company> companies ) {
        
        List<CompanyDto> companiesDto = new ArrayList<>();
        
        for (Company company: companies) {
            
            Optional<CompanyDto> companyDto = CompanyDtoMapper.mapCompanyDtoFromCompany(Optional.ofNullable(company)); 
            if ( companyDto.isPresent() ) {
                companiesDto.add(companyDto.get());
            }
        }
        
        return companiesDto;
    }
    
    public static Optional<CompanyDto> mapCompanyDtoFromCompany(Optional<Company> company) {
        if ( !company.isPresent() ) {
            return Optional.empty();
        }
        
        long id = company.get().getId();
        Optional<String> name = company.get().getName();
        
        if ( id <= 0 || !name.isPresent() || StringUtils.isBlank(name.get()) ) {
            return Optional.empty();
        }       
        return Optional.of(new CompanyDto.CompanyDtoBuilder(id, name.get()).build());       
    }
}
