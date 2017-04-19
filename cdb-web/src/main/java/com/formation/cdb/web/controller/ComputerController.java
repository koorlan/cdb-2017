package com.formation.cdb.web.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.formatter.USLocalDateFormatter;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.pager.Pager;
import com.formation.cdb.validator.ComputerFormValidator;


@Controller
@RequestMapping("/computers")
public class ComputerController {

    Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    private CDBService<Computer> computerService;

    private CDBService<Company> companyService;
    
    private ComputerFormValidator computerFormValidator;

    public ComputerController(CDBService<Computer> computerService, CDBService<Company> companyService,
            ComputerFormValidator computerFormValidator) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerFormValidator = computerFormValidator;

    }

    @InitBinder("computerForm")
    private void initBinderComputerForm(WebDataBinder binder) {
        binder.setValidator(computerFormValidator);

    }

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale) {
        return StringUtils.lowerCase(USLocalDateFormatter.getPattern(locale));
    }

    @GetMapping
    public String showComputers(ModelMap model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("filter") Optional<String> filter, @RequestParam("size") Optional<Integer> size) {
        LOGGER.debug("showComputers()");
        
        int numberOfComputers = computerService.sizeOfTable(filter.orElse("")); 
        Pager pager = new Pager(filter,size, page, numberOfComputers);

        model.put("filter", pager.getFilter());
        model.put("size", pager.getPageSize());
        
        List<Computer> computers = computerService.findAllWithOffsetAndLimit(pager.getOffset(), pager.getPageSize(), pager.getFilter());
        List<ComputerDto> computersDto = ComputerDtoMapper.mapComputersDtoFromComputers(computers);
        model.put("computers", computersDto);
        model.put("totalComputers", pager.getMax());
        model.put("currentIndexPage", pager.getCurrentPageIndex());
        model.put("maxIndexPage", pager.getNbPages());
        
        
        model.put("deleteForm", new ComputerListWrapper());

        return "dashboard";
    }

    @PostMapping
    public String saveOrUpdateComputer(@ModelAttribute("computerForm") @Validated ComputerDto computerDto,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        LOGGER.debug("saveOrUpdateComputer() : DTO:" + computerDto);

        if (result.hasErrors()) {
            
            populateDefaultModel(model);
            return "formComputer";
        } else {
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            LOGGER.debug("saveOrUpdateComputer() : Computer after mapping:" + computer);
            redirectAttributes.addFlashAttribute("css", "success");
            if (computer.get().isNew()) {
                redirectAttributes.addFlashAttribute("msg", "add");
            } else {
                redirectAttributes.addFlashAttribute("msg", "update");
            }

            computer.ifPresent(c -> computerService.saveOrUpdate(c));

            return "redirect:/computers";
        }
    }


    @GetMapping("/{id}/edit")
    public String showUpdateComputerForm(@PathVariable("id") long id, Model model,
            final RedirectAttributes redirectAttributes) {
        try {
            Optional<Computer> computer = computerService.findById(id);
            if (computer.isPresent()) {

                Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(computer);

                if (computerDto.isPresent()) {
                    model.addAttribute("computerForm", computerDto.get());
                    populateDefaultModel(model);
                    return "formComputer";
                } else {
                    redirectAttributes.addFlashAttribute("css", "danger");
                    redirectAttributes.addFlashAttribute("msg", "mapping");
                    return "redirect:/computers";
                }
            } else {
                return "404";
            }
        } catch (DAOException | ServiceException ex) {
            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", ex.getMessage());
        }
        return "redirect:/computers";
    }

    @PostMapping("/delete")
    public String deleteComputer(@ModelAttribute("deleteForm") ComputerListWrapper computerListWrapper,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

        LOGGER.debug("deleteComputer() : {}", computerListWrapper.getComputers());

        // TODO Catch error form dao
        computerService.deleteMultiple(computerListWrapper.getComputers());

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "delete");

        return "redirect:/computers";

    }

    @GetMapping("/add")
    public String showAddComputerForm(Model model) {
        ComputerDto computerDto = new ComputerDto();
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
