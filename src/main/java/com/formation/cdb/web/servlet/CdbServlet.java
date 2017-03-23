package com.formation.cdb.web.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.PagerComputer;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.mapper.ComputerDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.formation.cdb.service.impl.ComputerServiceImpl;
import com.formation.cdb.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class CdbServlet.
 */
//@Configurable
//@WebServlet(name = "CdbServlet", urlPatterns = "/database")
public class CdbServlet extends Servlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyServiceImpl;
    
    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> computerServiceImpl;
    
    @Autowired
    private PagerComputer pagerComputer;
    
    
    static private Logger LOGGER = LoggerFactory.getLogger(CdbServlet.class.getSimpleName());
    
    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pageToForward = "/dashboard.jsp";

        if (request.getParameter("action") != null) {
            switch (request.getParameter("action")) {

                case "goto":
                    if (request.getParameter("page") != null) {
                        try {
                            int page = Integer.parseInt(request.getParameter("page"));
                            pagerComputer.goTo(page);
                        } catch (NumberFormatException e) {
                            // TODO warn.
                        }
                    }
                    break;
                case "next":
                    pagerComputer.next();
                    break;
                case "prev":
                    pagerComputer.prev();
                    break;
                case "size":
                    if (request.getParameter("size") != null) {
                        try {
                            int size = Integer.parseInt(request.getParameter("size"));
                            pagerComputer.setPageSize(size);
                        } catch (NumberFormatException e) {
                            // TODO warn.
                        }
                    }
                    break;

                case "edit":
                    if (request.getParameter("id") != null) {
                        try {
                            int id = Integer.parseInt(request.getParameter("id"));
                            Optional<ComputerDto> computer = ComputerDtoMapper
                                    .mapComputerDtoFromComputer(computerServiceImpl.readById(Long.valueOf(id)));

                            if (computer.isPresent()) {
                                request.getSession().setAttribute("computer", computer.get());
                                int numberOfCompanies = computerServiceImpl.sizeOfTable("");
                                List<Company> companies = companyServiceImpl.readAllWithOffsetAndLimit(0,
                                        numberOfCompanies, "");
                                List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
                                request.getSession().setAttribute("companies", companiesDto);
                                pageToForward = "/editComputer.jsp";
                            } else {
                                LoggerFactory.getLogger("servlet").error("edit failed, computer dose not exist");
                            }
                        } catch (NumberFormatException e) {
                            LoggerFactory.getLogger("servlet").error("Edit, parse Int failed");
                        }
                    }
                    break;
                case "add":
                    int numberOfCompanies = companyServiceImpl.sizeOfTable("");
                    List<Company> companies = companyServiceImpl.readAllWithOffsetAndLimit(0, numberOfCompanies, "");
                    List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
                    request.getSession().setAttribute("companies", companiesDto);
                    pageToForward = "/addComputer.jsp";
                    break;

                case "filter":
                    if (request.getParameter("search") != null) {
                        LoggerFactory.getLogger("servlet").info("Filter on " + request.getParameter("search"));
                        pagerComputer.setFilter(request.getParameter("search"));
                    }
                    break;
                default:
                    // TODO Log..
                    break;
            }
        }
        if (pageToForward.equals("/dashboard.jsp")) {
            List<ComputerDto> list = ComputerDtoMapper.mapComputersDtoFromComputers(pagerComputer.getCurrentPage());
            request.getSession().setAttribute("computers", list);
            request.getSession().setAttribute("totalComputers", pagerComputer.getMax());
            request.getSession().setAttribute("currentIndexPage", pagerComputer.getCurrentPageIndex());
            request.getSession().setAttribute("maxIndexPage", pagerComputer.getNbPages());
            request.getSession().setAttribute("filter", pagerComputer.getFilter());
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageToForward);
        rd.forward(request, response);
    }

    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<ComputerDto> computerDto;
        Optional<Computer> computer;
        if (request.getParameter("action") != null) {
            switch (request.getParameter("action")) {
                case "edit":
                    computerDto = mapComputerDtoByRequestWithId(request);
                    computer = ComputerDtoMapper.mapComputerFromComputerDto(computerDto);
                    computerServiceImpl.update(computer);
                    response.sendRedirect("database");
                    break;
                case "add":
                    computerDto = mapComputerDtoByRequestWithoutId(request);
                    computer = ComputerDtoMapper.mapComputerFromComputerDto(computerDto);
                    computerServiceImpl.create(computer);
                    response.sendRedirect("database");
                    break;
                case "delete":
                    if (request.getParameter("selection") != null) {
                        String str = request.getParameter("selection");
                        
                        List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
                        for (String s: items) {
                            try {
                                long id = Long.parseLong(s);
                                Computer c = new Computer.ComputerBuilder(id, "").build();
                                computerServiceImpl.delete(Optional.of(c));
                                
                            } catch ( NumberFormatException numberFormatException){
                                
                            }
                        }
                        
                        
                    }
                    response.sendRedirect("database");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Gets the context pager.
     *
     * @param request the request
     * @return the context pager
     */
    private PagerComputer getContextPager(HttpServletRequest request) {

        if (request.getSession().getAttribute("pager") == null) {
            PagerComputer pager = new PagerComputer();
            request.getSession().setAttribute("pager", pager);
        }
        return (PagerComputer) request.getSession().getAttribute("pager");
    }

    /**
     * Map computer dto by request without id.
     *
     * @param request the request
     * @return the optional
     */
    public Optional<ComputerDto> mapComputerDtoByRequestWithoutId(HttpServletRequest request) {
        if (!Control.isRequestValidForMappingComputerDtoWithoutId(request)) {
            LoggerFactory.getLogger("servlet").error("Return optional empty");
            return Optional.empty();
        }

        String nameFromRequest = request.getParameter("name");
        String introducedFromRequest = request.getParameter("introduced");
        String discontinuedFromRequest = request.getParameter("discontinued");

        String companyFromRequest = request.getParameter("company");

        CompanyDto companyDto = null;
        if (StringUtils.isNotBlank(companyFromRequest)) {
            String companySplit[] = companyFromRequest.split(":");

            String companyIdFromRequest = companySplit[0];
            String companyNameFromRequest = companySplit[1];

            long companyId = Long.parseLong(companyIdFromRequest);
            companyDto = new CompanyDto.CompanyDtoBuilder(companyId, companyNameFromRequest).build();
        }

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder(1, nameFromRequest)
                .withIntroduced(introducedFromRequest)
                .withDiscontinued(discontinuedFromRequest)
                .withCompany(companyDto)
                .build();
        return Optional.of(computerDto);
    }

    /**
     * Map computer dto by request with id.
     *
     * @param request the request
     * @return the optional
     */
    public Optional<ComputerDto> mapComputerDtoByRequestWithId(HttpServletRequest request) {
        if (!Control.isRequestValidForMappingComputerDtoWithId(request)) {
            LoggerFactory.getLogger("servlet").error("Return optional empty");
            return Optional.empty();
        }

        String idFromRequest = request.getParameter("id");
        String nameFromRequest = request.getParameter("name");
        String introducedFromRequest = request.getParameter("introduced");
        String discontinuedFromRequest = request.getParameter("discontinued");

        long id = Long.parseLong(idFromRequest);
        if (id <= 0) {
            return Optional.empty();
        }

        String companyFromRequest = request.getParameter("company");
        CompanyDto companyDto = null;
        if (StringUtils.isNotBlank(companyFromRequest)) {
            String companySplit[] = companyFromRequest.split(":");

            String companyIdFromRequest = companySplit[0];
            String companyNameFromRequest = companySplit[1];

            long companyId = Long.parseLong(companyIdFromRequest);
            companyDto = new CompanyDto.CompanyDtoBuilder(companyId, companyNameFromRequest).build();
        }

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder(id, nameFromRequest)
                .withIntroduced(introducedFromRequest)
                .withDiscontinued(discontinuedFromRequest)
                .withCompany(companyDto)
                .build();
        return Optional.of(computerDto);
    }



    
}
