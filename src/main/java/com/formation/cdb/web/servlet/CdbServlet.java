package com.formation.cdb.web.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
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
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.formation.cdb.service.impl.ComputerServiceImpl;
import com.formation.cdb.ui.Control;
import com.formation.cdb.util.DateUtil;

/**
 * Servlet implementation class CdbServlet
 */
@WebServlet(name = "CdbServlet", urlPatterns = "/database")
public class CdbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CdbServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PagerComputer pager;
        pager = getContextPager(request);
        
        String pageToForward = "/dashboard.jsp";

        if (request.getParameter("action") != null) {
            switch (request.getParameter("action")) {

            case "goto":
                if (request.getParameter("page") != null) {
                    try {
                        int page = Integer.parseInt(request.getParameter("page"));
                        pager.goTo(page);
                    } catch (NumberFormatException e) {
                        // TODO warn.
                    }
                }
                break;
            case "next":
                pager.next();
                break;
            case "prev":
                pager.prev();
                break;
            case "size":
                if (request.getParameter("size") != null) {
                    try {
                        int size = Integer.parseInt(request.getParameter("size"));
                        pager.setPageSize(size);
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
                                .mapComputerDtoFromComputer(ComputerServiceImpl.INSTANCE.readById(Long.valueOf(id)));

                        if (computer.isPresent()) {
                            request.getSession().setAttribute("computer", computer.get());
                            int numberOfCompanies = ComputerServiceImpl.INSTANCE.sizeOfTable();
                            List<Company> companies = CompanyServiceImpl.INSTANCE.readAllWithOffsetAndLimit(0,
                                    numberOfCompanies);
                            List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
                            request.getSession().setAttribute("companies", companiesDto);
                            pageToForward = "/editComputer.jsp";
                        }
                    } catch (NumberFormatException e) {
                        // TODO warn.
                    }
                }
                break;
            case "add":
                int numberOfCompanies = CompanyServiceImpl.INSTANCE.sizeOfTable();
                List<Company> companies = CompanyServiceImpl.INSTANCE.readAllWithOffsetAndLimit(0, numberOfCompanies);
                List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
                request.getSession().setAttribute("companies", companiesDto);
                pageToForward = "/addComputer.jsp";
                break;
                
            case "filter":
                if (request.getParameter("search") != null) {
                    
                }
                break;
            default:
                // TODO Log..
                break;
            }
        }
        if (pageToForward.equals("/dashboard.jsp")) {
            List<ComputerDto> list = ComputerDtoMapper.mapComputersDtoFromComputers(pager.getCurrentPage());
            request.getSession().setAttribute("computers", list);
            request.getSession().setAttribute("totalComputers", pager.getMax());
            request.getSession().setAttribute("currentIndexPage", pager.getCurrentPageIndex());
            request.getSession().setAttribute("maxIndexPage", pager.getNbPages());
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageToForward);
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("action") != null) {
            switch (request.getParameter("action")) {
            case "edit":
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    String name = request.getParameter("name");

                    LocalDate introduced = null;
                    if (Control.isValidStringDateDashSeparatedYYYYMMDD(Optional.of(request.getParameter("introduced")))
                            && StringUtils.isNotBlank(request.getParameter("introduced"))) {
                        introduced = DateUtil.stringToDateDashSeparatedYYYYMMDD(request.getParameter("introduced"));
                    }

                    LocalDate discontinued = null;
                    if (Control
                            .isValidStringDateDashSeparatedYYYYMMDD(Optional.of(request.getParameter("discontinued")))
                            && StringUtils.isNotBlank(request.getParameter("discontinued"))) {
                        discontinued = DateUtil.stringToDateDashSeparatedYYYYMMDD(request.getParameter("discontinued"));
                    }
                    int companyId = Integer.parseInt(request.getParameter("companyId"));

                    String companyName = null;
                    if (CompanyServiceImpl.INSTANCE.readById(Long.valueOf(companyId)).isPresent()) {
                        companyName = CompanyServiceImpl.INSTANCE.readById(Long.valueOf(companyId)).get().getName()
                                .orElse("UNKOWN");
                    }

                    Company company = null;

                    if (companyId != 0) {
                        company = new Company.CompanyBuilder(companyId, companyName).build();
                    }
                    Computer c = new Computer.ComputerBuilder(id, name)
                            .withIntroduced(introduced)
                            .withDiscontinued(discontinued)
                            .withCompany(company)
                            .build();
                    // TODO IS valid computer

                    ComputerServiceImpl.INSTANCE.update(Optional.of(c));

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
                break;
            case "add":
                try {
                    String name = request.getParameter("name");

                    LocalDate introduced = null;
                    if (Control.isValidStringDateDashSeparatedYYYYMMDD(Optional.of(request.getParameter("introduced")))
                            && StringUtils.isNotBlank(request.getParameter("introduced"))) {
                        introduced = DateUtil.stringToDateDashSeparatedYYYYMMDD(request.getParameter("introduced"));
                    }

                    LocalDate discontinued = null;
                    if (Control
                            .isValidStringDateDashSeparatedYYYYMMDD(Optional.of(request.getParameter("discontinued")))
                            && StringUtils.isNotBlank(request.getParameter("discontinued"))) {
                        discontinued = DateUtil.stringToDateDashSeparatedYYYYMMDD(request.getParameter("discontinued"));
                    }
                    int companyId = Integer.parseInt(request.getParameter("companyId"));

                    String companyName = null;
                    if (CompanyServiceImpl.INSTANCE.readById(Long.valueOf(companyId)).isPresent()) {
                        companyName = CompanyServiceImpl.INSTANCE.readById(Long.valueOf(companyId)).get().getName()
                                .orElse("UNKOWN");
                    }

                    Company company = null;

                    if (companyId != 0) {
                        company = new Company.CompanyBuilder(companyId, companyName).build();
                    }
                    Computer c = new Computer.ComputerBuilder(1, name)
                            .withIntroduced(introduced)
                            .withDiscontinued(discontinued)
                            .withCompany(company)
                            .build();
                    // TODO IS valid computer

                    ComputerServiceImpl.INSTANCE.create(Optional.of(c));

                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            default:
                break;
            }
        }
        doGet(request, response);
    }
    
    private Optional<ComputerDto> mapComputerDtoFromRequest(HttpServletRequest request){
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        String name = request.getParameter("name");
        
        if( id == 0 || name == null || StringUtils.isBlank(name)) {
            return Optional.empty();
        }
        
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
       
        long companyId;
        try {
        companyId = Long.parseLong(request.getParameter("companyId"));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
        
        Optional<Company> company = CompanyServiceImpl.INSTANCE.readById(companyId);
        Optional<CompanyDto> companyDto = CompanyDtoMapper.mapCompanyDtoFromCompany(company);
        
        ComputerDto computer = new ComputerDto.ComputerDtoBuilder(id, name)
                .withIntroduced(introduced)
                .withDiscontinued(discontinued)
                .withCompany(companyDto.orElse(null))
                .build();
        return Optional.of(computer);
    }
    
    private PagerComputer getContextPager(HttpServletRequest request){
        
        if(request.getSession().getAttribute("pager") == null ) {
            PagerComputer pager = new PagerComputer();
            request.getSession().setAttribute("pager", pager);
        }
        return(PagerComputer) request.getSession().getAttribute("pager");
    }

}
