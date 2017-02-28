package com.formation.cdb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;

public class CompanyServiceDto {
    
    public static List<CompanyDto> readAllWithOffsetAndLimit(int offset, int limit) {
        Optional<List<Optional<Company>>> listO = CompanyDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit);
        
        if(!listO.isPresent()) {
            return null;
        }

        List<Optional<Company>> list = listO.get();
        
        List<CompanyDto> listOfCompanyDto = new ArrayList<>();
        
        for(Optional<Company> companyO: list){
            if(companyO.isPresent()){
                Company c = companyO.get();
                CompanyDto companyD = new CompanyDto(c.getId(),c.getName().orElse("UNKNOW"));
                listOfCompanyDto.add(companyD);
            }
        }
      return listOfCompanyDto;
    }
    
}
