package com.formation.cdb.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;


@RestController
@RequestMapping("/computers")
public class ComputerController {
    Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    private CDBService<Computer> computerService;

    private CDBService<Company> companyService;

    private PagerComputer pagerComputer;
    


    public ComputerController(@Qualifier("computerServiceImpl") CDBService<Computer> computerService,@Qualifier("companyServiceImpl")  CDBService<Company> companyService,
            PagerComputer pagerComputer) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
        this.pagerComputer = pagerComputer;
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> view(@PathVariable("id") long id) {
        Optional<Computer> computer = computerService.findById(id);
        Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(computer);
        if (computerDto.isPresent()) {
            return new ResponseEntity<ComputerDto>(computerDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/update")
    public @ResponseBody ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody ComputerDto computerDto) {
        try {
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> computerService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<?> showComputer(@RequestBody ComputerDto computerDto) {
        try {
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> computerService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody List<Long> ids) {
        try {
            computerService.deleteMultiple(ids);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public @ResponseBody List<ComputerDto> viewList(@RequestParam("page") Optional<Integer> page,
            @RequestParam("filter") Optional<String> filter, @RequestParam("size") Optional<Integer> size) {
        LOGGER.debug("showComputers()");

        if (page.isPresent()) {
            pagerComputer.goTo(page.get());
        }
        if (filter.isPresent()) {
            pagerComputer.setFilter(filter.get());
        }
        if (size.isPresent()) {
            pagerComputer.setPageSize(size.get());
        }

        List<Computer> computers = pagerComputer.getCurrentPage();
        List<ComputerDto> computersDto = ComputerDtoMapper.mapComputersDtoFromComputers(computers);

        return computersDto;
    }
}
