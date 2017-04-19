package com.formation.cdb.api;

import java.util.List;
import java.util.Optional;

import com.formation.cdb.dto.CompaniesApiDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.pager.Pager;


@RestController
@RequestMapping("/companies")
public class CompanyController {
    Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private CDBService<Computer> computerService;

    private CDBService<Company> companyService;


    public CompanyController(CDBService<Computer> computerService, CDBService<Company> companyService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> view(@PathVariable("id") long id) {
        Optional<Company> company = companyService.findById(id);
        Optional<CompanyDto> companyDto = CompanyDtoMapper.mapCompanyDtoFromCompany(company);
        if (companyDto.isPresent()) {
            return new ResponseEntity<CompanyDto>(companyDto.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody CompanyDto companyDto) {
        try {
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> companyService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public @ResponseBody ResponseEntity<?> createCompany(@RequestBody CompanyDto companyDto) {
        try {
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> companyService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public @ResponseBody ResponseEntity<?> deleteMultiple(@RequestBody List<Long> ids) {
        try {
            companyService.deleteMultiple(ids);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            companyService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public @ResponseBody ResponseEntity<?> viewList(@RequestParam("page") Optional<Integer> page,
            @RequestParam("filter") Optional<String> filter, @RequestParam("size") Optional<Integer> size) {
        LOGGER.debug("showCompanys()");
        try {
            int numberOfCompanies = companyService.sizeOfTable(filter.orElse(""));
            Pager pager = new Pager(filter, size, page, numberOfCompanies);

            List<Company> companies = companyService.findAllWithOffsetAndLimit(pager.getOffset(), pager.getPageSize(), pager.getFilter());
            List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);

            CompaniesApiDto wrapper = new CompaniesApiDto();
            wrapper.setCompanies(companiesDto);
            wrapper.setTotal(numberOfCompanies);

            return new ResponseEntity<>(wrapper,HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
