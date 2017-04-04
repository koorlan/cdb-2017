package com.formation.cdb.web.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.PagerComputer;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;

@Controller
@RequestMapping("/dashboard/computers")
public class Dashboard {
    
    Logger LOGGER = LoggerFactory.getLogger(Dashboard.class);
    
    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> serviceComputer;
    
    @Autowired
    private PagerComputer pagerComputer;
    
    
    @GetMapping
    public String listPageComputer ( ModelMap model,    
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("filter") Optional<String> filter, 
                                      @RequestParam("size") Optional<Integer> size) 
    {   
        LOGGER.debug("listPageComputer()");
        
        if ( page.isPresent() ) {
            pagerComputer.goTo(page.get());
        }
        if ( filter.isPresent() ) {
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
    
    @GetMapping("/{id}")
    public ModelAndView showComputer(@PathVariable("id") long id){
        LOGGER.debug("showComputer()");
        return new ModelAndView("redirect:/edit/computers/"+ id);
    }
    
}
