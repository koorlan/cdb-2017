package com.formation.cdb.api;

import java.util.List;
import java.util.Optional;

import com.formation.cdb.dto.ComputersApiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.pager.Pager;


@RestController
@RequestMapping("/computers")
public class ComputerController {
    Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    private CDBService<Computer> computerService;

    private CDBService<Company> companyService;

    private ComputerValidator computerValidator;


    //@InitBinder("computer")
    private void initBinderComputerForm(WebDataBinder binder) {
        binder.setValidator(computerValidator);

    }


    public ComputerController(@Qualifier("computerServiceImpl") CDBService<Computer> computerService,@Qualifier("companyServiceImpl")  CDBService<Company> companyService,
                              ComputerValidator computerValidator) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerValidator = computerValidator;
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

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<?> updateComputer(@PathVariable("id") long id, @RequestBody @Validated ComputerDto computerDto, BindingResult result) {
        try {
            computerValidator.validate(computerDto, result);
            if ( result.hasErrors() ) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> computerService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?> addComputer(@RequestBody @Validated ComputerDto computerDto, BindingResult result) {
        LOGGER.debug("addComputer()");
        try {
            computerValidator.validate(computerDto, result);
            if ( result.hasErrors() ) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            LOGGER.debug("Dto from request : " + computerDto);
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> computerService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public @ResponseBody ResponseEntity<?> delete(@RequestBody List<Long> ids) {
        try {
            computerService.deleteMultiple(ids);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteComputer(@PathVariable("id") long id) {
        try {
            computerService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> viewList(@RequestParam("page") Optional<Integer> page,
            @RequestParam("filter") Optional<String> filter, @RequestParam("size") Optional<Integer> size) {
        LOGGER.debug("showComputers()");
        try {
            int numberOfComputers = computerService.sizeOfTable(filter.orElse(""));
            Pager pager = new Pager(filter, size, page, numberOfComputers);

            List<Computer> computers = computerService.findAllWithOffsetAndLimit(pager.getOffset(), pager.getPageSize(), pager.getFilter());
            List<ComputerDto> computersDto = ComputerDtoMapper.mapComputersDtoFromComputers(computers);

            ComputersApiDto wrapper = new ComputersApiDto();
            wrapper.setComputers(computersDto);

            int totalComputer = computerService.sizeOfTable(pager.getFilter());
            wrapper.setTotal(totalComputer);
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
