package com.formation.cdb.ui;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.formation.cdb.entity.PagerCompany;
import com.formation.cdb.entity.PagerComputer;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.persistence.impl.CompanyJDBCTemplate;
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.formation.cdb.service.impl.ComputerServiceImpl;
import com.formation.cdb.util.DateUtil;



// TODO: Auto-generated Javadoc
/**
 * The Class ClientConsole.
 */
public class ClientConsole {
    
    /** The exit. */
    //TODO Refactor all..Ugly Impl.
    private boolean exit;
    
    /** The scanner. */
    private final Scanner scanner = new Scanner(System.in);
    
    /** The logger. */
    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Autowired
    private CompanyServiceImpl serviceCompany;
    
    @Autowired
    private ComputerServiceImpl service;
    
    @Autowired
    private PagerComputer pagerComputer;
    
    @Autowired
    private PagerCompany pagerCompany;

    /**
     * Constructor of ClientConsole.
     */
    public ClientConsole() {
        exit = false;
    }

    /**
     * Main Loop.
     * @param args arguments of cdb. No need.
     */
    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        ClientConsole c = new ClientConsole();
              
        while (!c.isExit()) {
            c.showMenu();
        }
    }

    /**
     * Simple test to see if we have to close the program.
     * @return true or false.
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Default print. Base menu to select action to perform on CDB.
     */
    public void showMenu() {     
        String s = null;

        System.out.println("Welcome to the Swedish Computer Database");
        System.out.println("Please select an option:");
        System.out.println("(a) List All Computers");
        System.out.println("(z) List All Companies");
        System.out.println("(e) Show Computer Details");
        System.out.println("(r) Show Company Details");
        System.out.println("(c) Create a Computer");
        System.out.println("(d) Delete a Computer");
        System.out.println("(u) Update a Computer");
        System.out.println("(x) Close application, adjö!");

        s = scanner.nextLine();
        switch (s) {
            case "a":
                listComputers();
                break;
            case "z":
                listCompanies();
                break;
            case "e":
                showComputer();
                break;
            case "r":
                showCompany();
                break;
            case "c":
                createComputer();
                break;
            case "d":
                deleteComputer();
                break;
            case "u":
                updateComputer();
                break;
            case "x":
                closeClientConsole();
                break;
            default:
                System.out.print("Not a valid input please retry.");
                break;
        }
    }
    /**
     * Action, list computer by page.
     */
    public void listComputers() {
        String command;
        boolean exit;
        int index;
        int num = 0;
        String intString;

        exit = false;
        index = pagerComputer.getCurrentPageIndex();
        do {
            
            for (Computer c : pagerComputer.getPage(index)) {
               System.out.println(c);
            }

            System.out.println(index + " of " + pagerComputer.getNbPages() + " (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
            command = scanner.nextLine();
            switch (command) {
            case "n":
                pagerComputer.next();
                index = pagerComputer.getCurrentPageIndex();
                break;
            case "p":
                pagerComputer.prev();
                index = pagerComputer.getCurrentPageIndex();
                break;
            case "g":
                System.out.println("enter the page number");
                intString = scanner.nextLine();
                intString = intString.trim();
                if (intString.isEmpty()) {
                    num = Integer.parseInt(intString);
                }
                index = num;
                break;
            case "x":
                exit = true;
                break;
            }

        }  while (!exit);

    };
    /**
     * Action, list all companie with page.
     */
    public void listCompanies() {
        PagerCompany pager;
        String command;
        boolean exit;
        int index;
        int num = 0;
        String intString;

        pager = new PagerCompany();
        exit = false;
        index = pager.getCurrentPageIndex();
        do {
             for (Company c : pager.getPage(index)) {
               System.out.println(c);
            }

            System.out.println(index + " of " + pager.getNbPages() + " (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
            command = scanner.nextLine();
            switch (command) {
            case "n":
                pager.next();
                index = pager.getCurrentPageIndex();
                break;
            case "p":
                pager.prev();
                index = pager.getCurrentPageIndex();
                break;
            case "g":
                System.out.println("enter the page number");
                intString = scanner.nextLine();
                intString = intString.trim();
                if (intString.isEmpty()) {
                    num = Integer.parseInt(intString);
                }
                index = num;
                break;
            case "x":
                exit = true;
                break;
            }

        } while (!exit);
    };
    /**
     * Show detail of a specific computer.
     */
    public void showComputer() {
        long id;
        Computer c;

        System.out.println("Please enter computer id:");
        id = scanner.nextLong();
        scanner.nextLine(); //Consume Enter
        service.readById(id).ifPresent(x -> System.out.println(x));

    };
    /**
     * Show a company details.
     */
    public void showCompany() {

        long id;
        Company c;

        System.out.println("Please enter company id:");
        id = scanner.nextLong();
        scanner.nextLine(); //Consume Enter
        service.readById(id).ifPresent(x -> System.out.println(x));
    };
    /**
     * Action to create a computer.
     */
    public void createComputer() {
        String name;
        LocalDate introduced = null;
        LocalDate discontinued = null;
        long companyId = 0;
        String dateIntroducedString;
        String dateDiscontinuedString;
        String companyIdString;

        System.out.println("Please enter computer name [Required]:");
        name = scanner.nextLine();

        System.out.println("Please enter the date it was introduced (DD-MM-YYYY) [Optional]:");
        dateIntroducedString = scanner.nextLine();
        if (Control.isValidStringDate(Optional.ofNullable(dateIntroducedString))) {
            introduced = DateUtil.stringToDate(dateIntroducedString);
        }
        System.out.println("Please enter the date it was discontinued (DD-MM-YYYY) [Optional]:");
        dateDiscontinuedString = scanner.nextLine();
        if (Control.isValidStringDate(Optional.ofNullable(dateDiscontinuedString))) {
            discontinued = DateUtil.stringToDate(dateDiscontinuedString);
        }
        System.out.println("Please enter the company id of the computer manufacturer [Optional]:");
        companyIdString = scanner.nextLine();
        companyIdString = companyIdString.trim();
        if (!companyIdString.isEmpty()) {
            companyId = Long.parseLong(companyIdString);
        }

        Optional<Company> company = serviceCompany.readById(companyId);
        Computer computer = new Computer.ComputerBuilder(1,name).withIntroduced(introduced).withDiscontinued(discontinued).withCompany(company.orElse(null)).build();
                
                
                
                //new Computer(1, name, introduced, discontinued, company.orElse(null));
        if (Control.isValidComputer(Optional.ofNullable(computer))) {
            service.create(Optional.ofNullable(computer));
        }
    };

    /**
     * Action, delete a computer.
     */
    public void deleteComputer() {
        long id;
        String answer;

        System.out.println("Please enter the computer id you want to deleted");
        id = scanner.nextLong();
        scanner.nextLine(); //consume newline;


        System.out.println("Are you sure you want delete (y/n)");
        service.readById(id).ifPresent(x -> System.out.println(x));

        answer = scanner.nextLine();
        switch (answer) {
            case"y":
                service.delete(service.readById(id));
                break;
            case "n":
            default:
                break;
        }


    };
    /**
     * Update a computer.
     */
    public void updateComputer() {
        String idString;
        long id;
        String answer;
        Computer computer;
        //New Computer var
        String newName = null;
        String newDateIntroducedString;
        LocalDate newDateIntroduced = null;
        String newDateDiscontinuedString;
        LocalDate newDateDiscontinued = null;
        String newIdCompanyString;
        long newIdCompany = 0;
        Company newCompany = null;

        System.out.println("Please enter the computer id you want to update");
        idString = scanner.nextLine();
        idString = idString.trim();
        if (!idString.isEmpty()) {
            id = Long.parseLong(idString);
        } else {
            return;
        }
        Optional<Computer> optionalComputer = service.readById(id);
        if (!optionalComputer.isPresent()) {
            return;
        }
        computer = optionalComputer.get();

        System.out.println("Name: [" + computer.getName() + "] (e)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
        case "e": //edit
            System.out.println("Enter new name");
            newName = scanner.nextLine();
            newName = newName.trim();
            if (StringUtils.isBlank(newName)) {
                return;
            } 
            break;
        default:
            break;
        }

        System.out.println("Date Introduced: [" + computer.getIntroduced() + "] (e/d)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
        case "e": //edit
            System.out.println("Enter new date introduced (DD-MM-YYYY)");
            newDateIntroducedString = scanner.nextLine();
            newDateIntroducedString = newDateIntroducedString.trim();
            if (!newDateIntroducedString.isEmpty()) {
                newDateIntroduced = DateUtil.stringToDate(newDateIntroducedString);
            }
            
            break;
        case "d": //delete
            newDateIntroduced = null;
            break;
        default:
            break;
        }

        System.out.println("Date Discontinued: [" + computer.getDiscontinued() + "] (e/d)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
        case "e": //edit
            System.out.println("Enter new date discontinued (DD-MM-YYYY)");
            newDateDiscontinuedString = scanner.nextLine();
            newDateDiscontinuedString = newDateDiscontinuedString.trim();
            if (!newDateDiscontinuedString.isEmpty()) {
                newDateDiscontinued = DateUtil.stringToDate(newDateDiscontinuedString);
            }
            
            break;
        case "d": //delete
            newDateDiscontinued = null;
            break;
        default:
            break;
        }

        System.out.println("Compagny (id): [" + computer.getCompany() + "] (e/d)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
        case "e": //edit
            System.out.println("Enter new company id");
            newIdCompanyString = scanner.nextLine();
            newIdCompanyString = newIdCompanyString.trim();
            if (!newIdCompanyString.isEmpty()) {
                newIdCompany = Long.parseLong(newIdCompanyString);
            }
            Optional<Company> optionalCompany = serviceCompany.readById(newIdCompany);
            newCompany = optionalCompany.get();

            System.out.println("Company :" + newCompany + " (y/n)");
            answer = null;
            answer = scanner.nextLine();
            switch (answer) {
                case "n":
                    newCompany = null;
                    break;
                default:
                    break;
            }

            break;
        case "d": //delete
            newCompany = null;
            break;
        default:
            break;
        }
        
        computer = new Computer.ComputerBuilder(computer.getId(),newName).withIntroduced(newDateIntroduced).withDiscontinued(newDateDiscontinued).withCompany(newCompany).build();
        if (Control.isValidComputer(Optional.ofNullable(computer))) {
            service.update(Optional.ofNullable(computer));
        }
    };
    /**
     * Quit the program.
     */
    public void closeClientConsole() {
        exit = true;
    }
}