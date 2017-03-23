package com.formation.cdb.web.servlet;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/dashboard")
public class Dashboard {

    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> serviceComputer;
    
    @Autowired
    private PagerComputer pagerComputer;
    
    
    @GetMapping("/") 
    public ModelAndView getDashboardIndex (){
        //TODO decider si quand on tape /dashboard on revient au début ou on reste la ou on est en tant que session
        //int page = pagerComputer.getCurrentPageIndex();
        int size = pagerComputer.getPageSize();
        return new ModelAndView("redirect:/dashboard/page/1/size/" + Integer.toString(size));
    }
    
    @GetMapping("/page/{page}")
    public ModelAndView getDashboard(ModelMap model, @PathVariable int page) {
       int size = pagerComputer.getPageSize();
       return new ModelAndView("redirect:/dashboard/page/"+ Integer.toString(page) +"/size/" + Integer.toString(size));
    }
    
    @GetMapping("/page/{page}/size/{size}")
    public String getDashboard(ModelMap model, @PathVariable int page, @PathVariable int size) {
       pagerComputer.goTo(page);
       pagerComputer.setPageSize(size);
       model = fillModel(model);
       return "dashboard";
    }

    private ModelMap fillModel(ModelMap model) {
        List<Computer> computers = pagerComputer.getCurrentPage();
        List<ComputerDto> computersDto = ComputerDtoMapper.mapComputersDtoFromComputers(computers);
        model.put("computers", computersDto);
        model.put("totalComputers", pagerComputer.getMax());
        model.put("currentIndexPage", pagerComputer.getCurrentPageIndex());
        model.put("maxIndexPage", pagerComputer.getNbPages());
        return model;
    }
    
}
