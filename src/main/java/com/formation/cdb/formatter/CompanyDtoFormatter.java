package com.formation.cdb.formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.service.CDBService;

public class CompanyDtoFormatter implements Formatter<CompanyDto>{

    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;
    
    @Override
    public String print(CompanyDto company, Locale arg1) {
        return company.toString();
    }

    @Override
    public CompanyDto parse(String companyId, Locale arg1) throws ParseException {
        long id = Long.parseLong(companyId);
        Optional<Company> companyOptional = companyService.readById(id);
        if ( !companyOptional.isPresent() ) {
            throw new ParseException("Company was not found on the database: " + id,0);
        }
        CompanyDto company = CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional).get();
        return company;
    }

}
