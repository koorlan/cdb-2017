package com.formation.cdb.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;

@Controller
@RequestMapping("/add/computers")
public class AddComputer {
        
    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> computerService;
    
    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;
    
    
    @GetMapping
    public String addComputerGet(ModelMap model){
        int numberOfCompanies = companyService.sizeOfTable("");
        List<Company> companies = companyService.readAllWithOffsetAndLimit(0,numberOfCompanies, "");
        List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
        
        model.put("companies", companiesDto);
        
        model.put("computer", new ComputerDto());
        
        return "addComputer";
    }
    
    @PostMapping
    public ModelAndView addComputerPost(@ModelAttribute("computerDto") @Valid ComputerDto computerDto,BindingResult result )
    {
        long companyId = computerDto.getId();
        Optional<Company> companyOptional = companyService.readById(companyId);
        
        computerDto.setCompany(CompanyDtoMapper.mapCompanyDtoFromCompany(companyOptional).orElse(null));
        Optional<Computer> computerOptional = ComputerDtoMapper.mapComputerFromComputerDto(Optional.ofNullable(computerDto));
        
        if ( ! computerOptional.isPresent() ) {
            return new ModelAndView("404");
        }

        Computer computer = computerOptional.get();
        

        Company currentCompany = new Company.CompanyBuilder(computer.getCompany().get().getId(), computer.getCompany().get().getName().get()).build();
        
        Computer currentComputer = new Computer.ComputerBuilder(computer.getId(), computer.getName().get())
                .withIntroduced(computer.getIntroduced().orElse(null))
                .withDiscontinued(computer.getDiscontinued().orElse(null))
                .withCompany(currentCompany)
                .build();
        
        computerService.update(Optional.of(currentComputer));
        return new ModelAndView("redirect:/dashboard/computers");
    }
    

}
