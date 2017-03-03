package com.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.ComputerDto.ComputerDtoBuilder;
import com.formation.cdb.entity.impl.Computer;

public class ComputerDtoMapper {
    
    public static List<ComputerDto> mapComputersDtoFromComputers( List<Computer> computers ) {
        
        List<ComputerDto> computersDto=  new ArrayList<>();
        
        for ( Computer computer: computers) {
            Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(Optional.ofNullable(computer));
            if ( computerDto.isPresent() ) {
                computersDto.add(computerDto.get());
            }
         }
        return computersDto;
    }
    
    public static Optional<ComputerDto> mapComputerDtoFromComputer(Optional<Computer> computer) {
        if ( !computer.isPresent() ) {
            return Optional.empty();
        }
        
        if ( computer.get().getId() <= 0 || !computer.get().getName().isPresent() || StringUtils.isBlank(computer.get().getName().get()) ) {
            return Optional.empty();
        }
        
        ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder(computer.get().getId(), computer.get().getName().get());
        
        if ( computer.get().getIntroduced().isPresent() ) {     
            computerDtoBuilder.withIntroduced(computer.get().getIntroduced().get().toString());
        }
        
        if ( computer.get().getDiscontinued().isPresent() ) {     
            computerDtoBuilder.withDiscontinued(computer.get().getDiscontinued().get().toString());
        }
        
        if ( computer.get().getCompany().isPresent() ) {
            Optional<CompanyDto> company = CompanyDtoMapper.mapCompanyDtoFromCompany(computer.get().getCompany()); 
            if ( company.isPresent() ) {
                computerDtoBuilder.withCompany(company.get());   
            }
        }
            
        return Optional.of(computerDtoBuilder.build());       
    }
}
