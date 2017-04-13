package com.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.entity.impl.Company;


// TODO: Auto-generated Javadoc
/**
 * The Class CompanyDtoMapper.
 */
public class CompanyDtoMapper {
    
    /**
     * Map companies dto from companies.
     *
     * @param companies the companies
     * @return the list
     */
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
    
    /**
     * Map company dto from company.
     *
     * @param company the company
     * @return the optional
     */
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

    /**
     * Map company from company dto.
     *
     * @param companyDtoO the company dto O
     * @return the optional
     */
    public static Optional<Company> mapCompanyFromCompanyDto(Optional<CompanyDto> companyDtoO) {
        if (!companyDtoO.isPresent()) {
            return Optional.empty();
        }

        CompanyDto companyDto = companyDtoO.get();
        long id = companyDto.getId();
        String name = companyDto.getName();

        if ( id <= 0) {
            return Optional.empty();
        }
        
        if ( StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        Company company = new Company.CompanyBuilder(id,name).build();
        return Optional.of(company);
    }
}
