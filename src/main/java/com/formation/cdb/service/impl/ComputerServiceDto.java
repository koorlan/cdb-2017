package com.formation.cdb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;

public class ComputerServiceDto {
    
    public static List<ComputerDto> readAllWithOffsetAndLimit(int offset, int limit) {
        Optional<List<Optional<Computer>>> listO = ComputerDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit);
    
      return fromOptionalListOfOptionaltoSimpleList(listO);
    }
    
    public static List<ComputerDto> fromOptionalListOfOptionaltoSimpleList(Optional<List<Optional<Computer>>> list){
        if(!list.isPresent()) {
            return null;
        }
        
        List<ComputerDto> simplifiedList = new ArrayList<>();
        
        for(Optional<Computer> computerO: list.get()){
            if(computerO.isPresent()){
                Computer c = computerO.get();
                ComputerDto computerD = new ComputerDto(c.getId(),c.getName().orElse("UNKNOWN"),c.getIntroduced().orElse(null),c.getDiscontinued().orElse(null),null);
                //TODO USER CompanyService DTO
                if (c.getCompany().isPresent()) {
                    c.setCompany(new Company(c.getCompany().get().getId(),c.getCompany().get().getName().orElse("UNKNOWN")));
                }
                simplifiedList.add(computerD);
            }
        }
        
        return simplifiedList;
    }
}
