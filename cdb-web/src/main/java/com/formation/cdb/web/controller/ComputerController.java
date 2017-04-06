package com.formation.cdb.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.persistence.CompanyDto;
import com.formation.cdb.persistence.ComputerDto;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.pager.PagerComputer;
import com.formation.cdb.validator.ComputerFormValidator;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> computerService;

    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;

    @Autowired
    private PagerComputer pagerComputer;

    @Autowired
    private ComputerFormValidator computerFormValidator;
    
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(computerFormValidator);
    }
    
    @GetMapping
    public String showComputers(ModelMap model, @RequestParam("page") Optional<Integer> page,
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
        model.put("computers", computersDto);
        model.put("totalComputers", pagerComputer.getMax());
        model.put("currentIndexPage", pagerComputer.getCurrentPageIndex());
        model.put("maxIndexPage", pagerComputer.getNbPages());

        return "dashboard";
    }

    @PostMapping
    public String saveOrUpdateComputer(@ModelAttribute("computerForm") @Validated ComputerDto computerDto,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        LOGGER.debug("saveOrUpdateComputer() : {}", computerDto);

        if (result.hasErrors()) {
            populateDefaultModel(model);
            return "formComputer";
        } else {
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            redirectAttributes.addFlashAttribute("css", "success");
            if (computer.get().isNew()) {
                redirectAttributes.addFlashAttribute("msg", "Computer added successfully!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Computer updated successfully!");
            }

            computerService.saveOrUpdate(computer);

            return "redirect:/computers";
        }
    }

    @GetMapping("/{id}")
    public void showComputer() {
       //TODO
    }

    @GetMapping("/{id}/edit")
    public String showUpdateComputerForm(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
        Optional<Computer> computer = computerService.findById(id);
        if ( computer.isPresent() ) {
            
            Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(computer);
            
            if( computerDto.isPresent() ) {
                model.addAttribute("computerForm", computerDto.get());              
                populateDefaultModel(model);           
                return "formComputer";   
            } else {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Error while mapping computer.");
                return "redirect:/computers2";
            }
        } else {
            return "404";
        }
    }

    @PostMapping("/delete")
    public String deleteComputer(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {

        LOGGER.debug("deleteComputer() : {}", id);

        //TODO Catch error form dao    
        computerService.delete(id);
        
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        
        return "redirect:/users";

    }

    @GetMapping("/add")
    public String showAddComputerForm(Model model) {
        ComputerDto computerDto =  new ComputerDto();
        computerDto.setName("name");
        
        model.addAttribute("computerForm", computerDto);
        populateDefaultModel(model);
        return "formComputer";
    }

    private void populateDefaultModel(Model model) {
        int numberOfCompanies = companyService.sizeOfTable("");
        List<Company> companies = companyService.findAllWithOffsetAndLimit(0, numberOfCompanies, "");
        List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
        model.addAttribute("companies", companiesDto);
    }
}
