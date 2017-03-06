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
import com.formation.cdb.util.DateUtil;
import org.slf4j.LoggerFactory;

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
     * response)
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
                                int numberOfCompanies = ComputerServiceImpl.INSTANCE.sizeOfTable("");
                                List<Company> companies = CompanyServiceImpl.INSTANCE.readAllWithOffsetAndLimit(0,
                                        numberOfCompanies, "");
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
                    int numberOfCompanies = CompanyServiceImpl.INSTANCE.sizeOfTable("");
                    List<Company> companies = CompanyServiceImpl.INSTANCE.readAllWithOffsetAndLimit(0, numberOfCompanies, "");
                    List<CompanyDto> companiesDto = CompanyDtoMapper.mapCompaniesDtoFromCompanies(companies);
                    request.getSession().setAttribute("companies", companiesDto);
                    pageToForward = "/addComputer.jsp";
                    break;

                case "filter":
                    if (request.getParameter("search") != null) {
                        pager.setFilter(request.getParameter("search"));
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
            request.getSession().setAttribute("filter", pager.getFilter());
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(pageToForward);
        rd.forward(request, response);
    }

    /**
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
                    ComputerServiceImpl.INSTANCE.update(computer);
                    break;
                case "add":
                    computerDto = mapComputerDtoByRequestWithoutId(request);
                    computer = ComputerDtoMapper.mapComputerFromComputerDto(computerDto);
                    ComputerServiceImpl.INSTANCE.create(computer);
                    response.sendRedirect("database");
                    break;
                default:
                    break;
            }
        }
    }

    private PagerComputer getContextPager(HttpServletRequest request) {

        if (request.getSession().getAttribute("pager") == null) {
            PagerComputer pager = new PagerComputer();
            request.getSession().setAttribute("pager", pager);
        }
        return (PagerComputer) request.getSession().getAttribute("pager");
    }

    public Optional<ComputerDto> mapComputerDtoByRequestWithoutId(HttpServletRequest request) {
        if (!Control.isRequestValidForMappingComputerDtoWithoutId(request)) {
            LoggerFactory.getLogger("servlet").error("REturn optional empty");
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

    public Optional<ComputerDto> mapComputerDtoByRequestWithId(HttpServletRequest request) {
        if (!Control.isRequestValidForMappingComputerDtoWithId(request)) {
            LoggerFactory.getLogger("servlet").error("REturn optional empty");
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
