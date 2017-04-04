package com.formation.cdb.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;

@Controller
@RequestMapping("/edit/computers/{id}")
public class EditComputer {
    
    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> computerService;
    
    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;
    
    @GetMapping
    public String updateComputer(ModelMap model, @PathVariable("id") long id){
        Optional<Computer> computerOptional = computerService.findById(id);
        Optional<ComputerDto> computerDtoOptional = ComputerDtoMapper.mapComputerDtoFromComputer(computerOptional);
        
        if( !computerDtoOptional.isPresent() ) {
            return  "404";
        }
        
        ComputerDto computerDto = computerDtoOptional.get();  
        model.put("computerDto", computerDto);
        
        int numberOfCompanies = companyService.sizeOfTable("");
        List<Company> companies = companyService.findAllWithOffsetAndLimit(0,numberOfCompanies, "");
        List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
        
        model.put("companies", companiesDto);

        return "editComputer";
    }
    
    @PostMapping
    public String updateComputer(
            @ModelAttribute("computerDto") @Valid ComputerDto computerDto,
            BindingResult result) {
        
        long companyId = computerDto.getId();

        LoggerFactory.getLogger("**********DBG EDIT********").info(String.valueOf(companyId));
        Optional<Company> companyOptional = companyService.findById(companyId);
        
        computerDto.setCompany(CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional).orElse(null));
        Optional<Computer> computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDto));
        
        if ( ! computerOptional.isPresent() ) {
            return "404";
        }

        Computer computer = computerOptional.get();
        LoggerFactory.getLogger("DBG").info(computer.toString());
        

        Company currentCompany = new Company.CompanyBuilder(computer.getCompany().get().getId(), computer.getCompany().get().getName().get()).build();
        
        Computer currentComputer = new Computer.ComputerBuilder(computer.getId(), computer.getName().get())
                .withIntroduced(computer.getIntroduced().orElse(null))
                .withDiscontinued(computer.getDiscontinued().orElse(null))
                .withCompany(currentCompany)
                .build();
        
        
        computerService.saveOrUpdate(Optional.of(currentComputer));
        return "redirect:/dashboard/computers";
    }

}
