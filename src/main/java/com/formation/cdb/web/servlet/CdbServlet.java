package com.formation.cdb.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.formation.cdb.entity.CompanyDto;
import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.PagerComputer;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.service.impl.CompanyServiceDto;
import com.formation.cdb.service.impl.ComputerServiceDto;
import com.formation.cdb.service.impl.ComputerServiceImpl;

/**
 * Servlet implementation class CdbServlet
 */
@WebServlet(name = "CdbServlet", urlPatterns = "/database")
public class CdbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PagerComputer pager = new PagerComputer();

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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/dashboard.jsp");
        if (!(request.getParameter("action") == null)) {
            switch (request.getParameter("action")) {

            case "goto":
                if (request.getParameter("page") != null) {
                    try {
                        int page = Integer.parseInt(request.getParameter("page"));
                        pager.goTo(page);
                    } catch (NumberFormatException e) {
                        //TODO warn.
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
                        //TODO warn.
                    } 
                }
                break;
            default:
                System.out.println(request.getParameter("test"));
                break;
            }
        }
        List<ComputerDto> list = ComputerServiceDto.fromOptionalListOfOptionaltoSimpleList(pager.getCurrentPage());
        request.getSession().setAttribute("computers", list);
        request.getSession().setAttribute("currentIndexPage", pager.getCurrentPageIndex());
        request.getSession().setAttribute("maxIndexPage", pager.getNbPages());
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    


}
