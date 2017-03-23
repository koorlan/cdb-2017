package com.formation.cdb.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    
    @GetMapping()
    public String updateComputer(ModelMap model, @PathVariable("id") long id){
        Optional<Computer> computerOptional = computerService.readById(id);
        Optional<ComputerDto> computerDtoOptional = ComputerDtoMapper.mapComputerDtoFromComputer(computerOptional);
        
        if( !computerDtoOptional.isPresent() ) {
            return  "404";
        }
        
        ComputerDto computerDto = computerDtoOptional.get();  
        model.put("computer", computerDto);
        
        int numberOfCompanies = companyService.sizeOfTable("");
        List<Company> companies = companyService.readAllWithOffsetAndLimit(0,numberOfCompanies, "");
        List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
        
        model.put("companies", companiesDto);

        return "editComputer";
    }
    
    @PostMapping()
    public ModelAndView updateComputer(@PathVariable("id") long id, 
            @ModelAttribute("computerDto") @Validated ComputerDto computerDto,
            BindingResult result) {
        
        Optional<Computer> computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDto));
        
        if ( ! computerOptional.isPresent() ) {
            return new ModelAndView("404");
        }

        Computer computer = computerOptional.get();
        

        Company currentCompany = new Company.CompanyBuilder(computer.getCompany().get().getId(), computer.getCompany().get().getName().get()).build();
        
        Computer currentComputer = new Computer.ComputerBuilder(id, computer.getName().get())
                .withIntroduced(computer.getIntroduced().orElse(null))
                .withDiscontinued(computer.getDiscontinued().orElse(null))
                .withCompany(currentCompany)
                .build();
        
        computerService.update(Optional.of(currentComputer));
        return new ModelAndView("redirect:/dashboard/computers");
    }
    
    

}
