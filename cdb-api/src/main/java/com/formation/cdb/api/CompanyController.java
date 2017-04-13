package com.formation.cdb.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.formation.cdb.service.pager.PagerCompany;
import com.formation.cdb.service.pager.PagerComputer;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private CDBService<Computer> computerService;

    private CDBService<Company> companyService;

    private PagerComputer pagerComputer;
  
    private PagerCompany pagerCompany;

    public CompanyController(CDBService<Computer> computerService, CDBService<Company> companyService,
            PagerComputer pagerComputer, PagerCompany pagerCompany) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
        this.pagerComputer = pagerComputer;
        this.pagerCompany = pagerCompany;
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

    @PostMapping("/{id}/update")
    public @ResponseBody ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody CompanyDto companyDto) {
        try {
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> companyService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<?> showCompany(@RequestBody CompanyDto companyDto) {
        try {
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> companyService.saveOrUpdate(c));
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody List<Long> ids) {
        try {
            companyService.deleteMultiple(ids);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (DAOException | ServiceException e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public @ResponseBody List<CompanyDto> viewList(@RequestParam("page") Optional<Integer> page,
            @RequestParam("filter") Optional<String> filter, @RequestParam("size") Optional<Integer> size) {
        LOGGER.debug("showCompanys()");

        if (page.isPresent()) {
            pagerCompany.goTo(page.get());
        }
        if (filter.isPresent()) {
            pagerCompany.setFilter(filter.get());
        }
        if (size.isPresent()) {
            pagerCompany.setPageSize(size.get());
        }

        List<Company> companies = pagerCompany.getCurrentPage();
        List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);

        return companiesDto;
    }
}
