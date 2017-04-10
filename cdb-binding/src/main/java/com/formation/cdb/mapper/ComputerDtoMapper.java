package com.formation.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.dto.ComputerDto.ComputerDtoBuilder;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.util.DateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDtoMapper.
 */
public class ComputerDtoMapper {
    
    /**
     * Map computers dto from computers.
     *
     * @param computers the computers
     * @return the list
     */
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
    
    /**
     * Map computer dto from computer.
     *
     * @param computer the computer
     * @return the optional
     */
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

    /**
     * Map computer from computer dto.
     *
     * @param computerDtoO the computer dto O
     * @return the optional
     */
    public static Optional<Computer> mapComputerFromComputerDto(Optional<ComputerDto> computerDtoO) {
        if (!computerDtoO.isPresent()) {
            return Optional.empty();
        }

        ComputerDto computerDto = computerDtoO.get();
        Long id = computerDto.getId();
        String name = computerDto.getName();
        String introduced = computerDto.getIntroduced();
        String discontinued = computerDto.getDiscontinued();
        CompanyDto companyDto = computerDto.getCompany();

        LocalDate introducedDate = null;
        LocalDate discontinuedDate = null;

        if ( id != null && id < 0) {
            return Optional.empty();
        }
       
        if ( StringUtils.isBlank(name)) {
            return Optional.empty();
        }

        if ( StringUtils.isNotBlank(introduced)) {
            try {
                introducedDate = DateUtil.stringToDateDashSeparatedYYYYMMDD(introduced);
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        }

        if ( StringUtils.isNotBlank(discontinued)) {
            try {
                discontinuedDate = DateUtil.stringToDateDashSeparatedYYYYMMDD(discontinued);
            } catch (DateTimeParseException e) {
                return Optional.empty();
            }
        }

        Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.ofNullable(companyDto));

        Computer computer = new Computer.ComputerBuilder(id, name)
                .withIntroduced(introducedDate)
                .withDiscontinued(discontinuedDate)
                .withCompany(company.orElse(null))
                .build();
        return Optional.of(computer);
    }
}
