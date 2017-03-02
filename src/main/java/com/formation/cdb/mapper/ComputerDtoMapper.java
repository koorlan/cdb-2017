package com.formation.cdb.mapper;

import java.util.Optional;

import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Computer;

public class ComputerDtoMapper {
    public static Optional<ComputerDto> mapComputerFromComputerDto(Optional<Computer> computer) {
        if ( !computer.isPresent() ) {
            return Optional.empty();
        }
        
        
    
    }
}
