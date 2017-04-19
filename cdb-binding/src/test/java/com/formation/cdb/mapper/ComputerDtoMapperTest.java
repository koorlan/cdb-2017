package com.formation.cdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.dto.ComputerDto.ComputerDtoBuilder;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.util.DateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ComputerDtoMapper.
 */
public class ComputerDtoMapperTest {
    
    @Test
    public void mapComputerFromComputerDto_Empty() throws Exception {
        Optional<Computer> computerOptional;
        Optional<ComputerDto> computerDTO = Optional.empty();
        computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(computerDTO);
        assertFalse(computerOptional.isPresent());
    }
    
    @Test
    public void mapComputerFromComputerDto_IdNeg() throws Exception {
        
        long id = -1;
        String name = "FABLER BJÖRN";
        
        Optional<Computer> computerOptional;
        ComputerDto computerDTO = new ComputerDto.ComputerDtoBuilder(id, name).build();
        computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDTO));
        
        
        assertFalse(computerOptional.isPresent());
    }
    
    @Test
    public void mapComputerFromComputerDto_NameBlank() throws Exception {
        
        long id = 42;
        String name = "";
        
        Optional<Computer> computerOptional;
        ComputerDto computerDTO = new ComputerDto.ComputerDtoBuilder(id, name).build();
        computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDTO));
        
        
        assertFalse(computerOptional.isPresent());
        
    }
    
    @Test
    public void mapComputerFromComputerDto_Full() throws Exception {
        
        long id = 42;
        String name = "FABLER BJÖRN";
        
        Optional<Computer> computerOptional;
        ComputerDto computerDTO = new ComputerDto.ComputerDtoBuilder(id, name).build();
        computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDTO));
        assertTrue(computerOptional.isPresent());
        Computer computer = computerOptional.get();
        
        assertEquals(id , computer.getId());
        assertEquals(name , computer.getName().get());        
        assertEquals(computerDTO.getId(), computer.getId());
        assertEquals(computerDTO.getName(), computer.getName().get());
        
    }
    
    @Test
    public void mapComputerDtoFromComputer_Empty() throws Exception {
        Optional<Computer> computerOptional = Optional.empty();
        Optional<ComputerDto> computerDTO;
        computerDTO = ComputerDtoMapper.mapComputerDtoFromComputer(computerOptional);
        assertFalse(computerDTO.isPresent());
    }
    
    @Test
    public void mapComputerDtoFromComputer_IdNeg() throws Exception {
        
        long id = -1;
        String name = "FABLER BJÖRN";
        Computer c = new Computer.ComputerBuilder(id, name).build();
        Optional<Computer> companyOptional = Optional.of(c);
        Optional<ComputerDto> companyDTO = ComputerDtoMapper.mapComputerDtoFromComputer(companyOptional);
        
        assertFalse(companyDTO.isPresent());
    }
    
    @Test
    public void mapComputerDtoFromComputer_NameBlank() throws Exception {
        
        long id = 42;
        String name = "";
        Computer c = new Computer.ComputerBuilder(id, name).build();
        Optional<Computer> companyOptional = Optional.of(c);
        Optional<ComputerDto> companyDTO = ComputerDtoMapper.mapComputerDtoFromComputer(companyOptional);
        
        assertFalse(companyDTO.isPresent());
    }
    
    @Test
    public void mapComputerDtoFromComputer_Full() throws Exception {
        
        long id = 42;
        String name = "FABLER BJÖRN";
        
        Computer c = new Computer.ComputerBuilder(id, name).build();
        
        Optional<Computer> computerOptional = Optional.of(c);
        Optional<ComputerDto> computerDTO = ComputerDtoMapper.mapComputerDtoFromComputer(computerOptional);
        
        assertTrue(computerDTO.isPresent());
        
        assertEquals(id , computerDTO.get().getId());
        assertEquals(name , computerDTO.get().getName());
        
        assertEquals(computerOptional.get().getId(), computerDTO.get().getId());
        assertEquals(computerOptional.get().getName().get(), computerDTO.get().getName());
        
    }
    
   
    
    @Test
    public void mapCompaniesDtoFromCompanies_Empty() throws Exception {
        List<Computer> list = new ArrayList<>();
        
        List<ComputerDto> listDto = ComputerDtoMapper.mapComputersDtoFromComputers(list);
        assertTrue(listDto.isEmpty());
    }
    
    @Test
    public void mapComputersDtoFromComputers_OneError() throws Exception {
        List<Computer> list = new ArrayList<>();
        
        long id1 = 42;
        String name1 = "FABLER BJÖRN";
        
        // Company non valide
        long id2 = -42;
        String name2 = "FABLER BJÖRN2";
        
        Computer c1 = new Computer.ComputerBuilder(id1, name1).build();
        Computer c2 = new Computer.ComputerBuilder(id2, name2).build();
        
        list.add(c1);
        list.add(c2);
        
        
        List<ComputerDto> listDto = ComputerDtoMapper.mapComputersDtoFromComputers(list);
        assertTrue(listDto.size() == 1);
        
        assertEquals(c1.getId(), listDto.get(0).getId());
        assertEquals(c1.getName().get(), listDto.get(0).getName());
    }
    
    @Test
    public void mapCompaniesDtoFromCompanies_NoError() throws Exception {
        List<Computer> list = new ArrayList<>();
        
        long id1 = 42;
        String name1 = "FABLER BJÖRN";
        
        long id2 = 33;
        String name2 = "FABLER BJÖRN2";
        
        Computer c1 = new Computer.ComputerBuilder(id1, name1).build();
        Computer c2 = new Computer.ComputerBuilder(id2, name2).build();
        
        list.add(c1);
        list.add(c2);
        
        
        List<ComputerDto> listDto = ComputerDtoMapper.mapComputersDtoFromComputers(list);
        assertTrue(listDto.size() == 2);
        
        assertEquals(c1.getId(), listDto.get(0).getId());
        assertEquals(c1.getName().get(), listDto.get(0).getName());
        
        assertEquals(c2.getId(), listDto.get(1).getId());
        assertEquals(c2.getName().get(), listDto.get(1).getName());
    }

}
