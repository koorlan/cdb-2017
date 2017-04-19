package com.formation.cdb.ui;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.formation.cdb.mapper.CompanyDtoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.*;

import com.formation.cdb.dto.*;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.service.pager.Pager;
import com.formation.cdb.util.DateUtil;
import com.formation.cdb.mapper.ComputerDtoMapper;

// TODO: Auto-generated Javadoc

/**
 * The Class ClientConsole.
 */
public class ClientConsole {

    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String BASE_COMPUTER = "/computers";
    private static final String BASE_COMPANY = "/companies";

    /**
     * The exit.
     */
    private boolean exit;

    /**
     * The scanner.
     */
    private final Scanner scanner = new Scanner(System.in);

    private RestTemplate restTemplate = new RestTemplate();

    private Pager pagerComputer = new Pager(Optional.of(new String("")), Optional.of(new Integer(10)), Optional.of(new Integer(1)), 0);
    private Pager pagerCompany = new Pager(Optional.of(new String("")), Optional.of(new Integer(10)), Optional.of(new Integer(1)), 0);

    /**
     * Constructor of ClientConsole.
     */
    private ClientConsole() {
        exit = false;
    }

    /**
     * Main Loop.
     *
     * @param args arguments of cdb. No need.
     */
    public static void main(String[] args) {
        //System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        ClientConsole c = new ClientConsole();

        while (!c.isExit()) {
            c.showMenu();
        }
    }

    /**
     * Simple test to see if we have to close the program.
     *
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
        System.out.println("(n) Delete a Company");
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
            case "n":
                deleteCompany();
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
    private void listComputers() {
        String command;
        boolean exit;
        int num = 0;
        String intString;

        exit = false;
        do {

            String url = BASE_URL + BASE_COMPUTER + "?page=" + pagerComputer.getCurrentPageIndex() + "&size=" + pagerComputer.getPageSize() + "&filter=" + pagerComputer.getFilter();
            try {
                ComputersApiDto computers = restTemplate.getForObject(url, ComputersApiDto.class);
                pagerComputer.setMax(computers.getTotal());

                for (ComputerDto computerDto : computers.getComputers()) {
                    Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
                    computer.ifPresent(System.out::println);
                }

            } catch (HttpClientErrorException | ResourceAccessException err) {
                System.out.println("Error: " + err.getMessage());
                break;
            }


            System.out.println(pagerComputer.getCurrentPageIndex() + " of " + pagerComputer.getNbPages() + " (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
            command = scanner.nextLine();
            switch (command) {
                case "n":
                    pagerComputer.next();

                    break;
                case "p":
                    pagerComputer.prev();

                    break;
                case "g":
                    System.out.println("enter the page number");
                    intString = scanner.nextLine();
                    intString = intString.trim();
                    try {
                        num = Integer.parseInt(intString);
                        pagerComputer.goTo(num);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid");
                    }
                    break;
                case "x":
                    exit = true;
                    break;
            }

        } while (!exit);
    }

    /**
     * Action, list all companie with page.
     */
    public void listCompanies() {
        String command;
        boolean exit;
        int num = 0;
        String intString;

        exit = false;
        do {
            try {
                String url = BASE_URL + BASE_COMPANY + "?page=" + pagerCompany.getCurrentPageIndex() + "&size=" + pagerCompany.getPageSize() + "&filter=" + pagerCompany.getFilter();
                CompaniesApiDto companies = restTemplate.getForObject(url, CompaniesApiDto.class);

                pagerCompany.setMax(companies.getTotal());

                for (CompanyDto companyDto : companies.getCompanies()) {
                    Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
                    company.ifPresent(System.out::println);
                }
            } catch (HttpClientErrorException | ResourceAccessException err) {
                System.out.println("Error: " + err.getMessage());
                break;
            }


            System.out.println(pagerCompany.getCurrentPageIndex() + " of " + pagerCompany.getNbPages() + " (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
            command = scanner.nextLine();
            switch (command) {
                case "n":
                    pagerCompany.next();

                    break;
                case "p":
                    pagerCompany.prev();

                    break;
                case "g":
                    System.out.println("enter the page number");
                    intString = scanner.nextLine();
                    intString = intString.trim();
                    try {
                        num = Integer.parseInt(intString);
                        pagerCompany.goTo(num);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid");
                    }
                    break;
                case "x":
                    exit = true;
                    break;
            }

        } while (!exit);
    }

    /**
     * Show detail of a specific computer.
     */
    public void showComputer() {
        long id;
        System.out.println("Please enter computer id:");
        try {
            id = scanner.nextLong();
            scanner.nextLine(); // Consume Enter
        } catch (InputMismatchException e) {
            System.out.println("Not a valid input, exiting.");
            return;
        }

        try {
            ComputerDto computerDto = restTemplate.getForObject("http://127.0.0.1:8080/computers/" + id,
                    ComputerDto.class);
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> System.out.println(c));
        } catch (HttpClientErrorException | ResourceAccessException err) {
            System.out.println("Error : " + err.getMessage());
        }
    }

    ;

    /**
     * Show a company details.
     */
    public void showCompany() {

        long id;

        System.out.println("Please enter company id:");
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Not a valid input, exiting.");
            return;
        }
        scanner.nextLine(); // Consume Enter
        try {
            String url = BASE_URL + BASE_COMPANY + "/" + id;
            CompanyDto companyDto = restTemplate.getForObject(url, CompanyDto.class);
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> System.out.println(c));
        } catch (HttpClientErrorException | ResourceAccessException err) {
            System.out.println("Error : " + err.getMessage());
        }
    }

    ;

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
        if (StringUtils.isBlank(name)) {
            System.out.println("Error: name is required");
            return;
        }

        System.out.println("Please enter the date it was introduced (DD-MM-YYYY) [Optional]:");
        dateIntroducedString = scanner.nextLine();
        if (Control.isValidStringDate(Optional.ofNullable(dateIntroducedString))) {
            introduced = DateUtil.stringToDate(dateIntroducedString);
        } else if (!dateIntroducedString.isEmpty()) {
            System.out.println("Invalid date format, computer not created");
            return;
        }
        System.out.println("Please enter the date it was discontinued (DD-MM-YYYY) [Optional]:");
        dateDiscontinuedString = scanner.nextLine();
        if (Control.isValidStringDate(Optional.ofNullable(dateDiscontinuedString))) {
            discontinued = DateUtil.stringToDate(dateDiscontinuedString);
        } else if (!dateIntroducedString.isEmpty()) {
            System.out.println("Invalid date format, computer not created");
            return;
        }
        System.out.println("Please enter the company id of the computer manufacturer [Optional]:");
        companyIdString = scanner.nextLine();
        companyIdString = companyIdString.trim();
        if (!companyIdString.isEmpty()) {
            try {
                companyId = Long.parseLong(companyIdString);
            } catch (NumberFormatException e) {
                System.out.println("Invalid company id format, computer not created");
                return;
            }

        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = BASE_URL + BASE_COMPUTER;


        Computer computer = new Computer.ComputerBuilder(0, name).withIntroduced(introduced)
                .withDiscontinued(discontinued).build();
        if (Control.isValidComputer(Optional.ofNullable(computer))) {
            Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(Optional.of(computer));
            if (computerDto.isPresent() && companyId != 0) {
                CompanyDto companyDto = new CompanyDto();
                companyDto.setId(companyId);
                computerDto.get().setCompany(companyDto);
            }
            //System.out.println(computerDto.get());
            HttpEntity<ComputerDto> request = new HttpEntity<ComputerDto>(computerDto.get(), headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            } catch (HttpClientErrorException | ResourceAccessException err) {
                System.out.println("Error : " + err.getMessage());
            }

            // new Computer(1, name, introduced, discontinued,
            // company.orElse(null));

            // service.saveOrUpdate(computer);
        } else {
            System.out.println("Invalid computer:" + computer);
            System.out.println("Computer not created...");
        }
    }

    ;

    /**
     * Action, delete a computer.
     */
    public void deleteComputer() {
        long id;
        String answer;

        System.out.println("Please enter the computer id you want to deleted");
        try {
            id = scanner.nextLong();
            scanner.nextLine(); // consume newline;
        } catch (InputMismatchException e) {
            System.out.println("Not a valid input, exiting computer deletion.");
            return;
        }
        System.out.println("Are you sure you want delete (y/n)");

        try {
            String url = BASE_URL + BASE_COMPUTER + "/" + id;
            ComputerDto computerDto = restTemplate.getForObject(url, ComputerDto.class);
            Optional<Computer> computer = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            computer.ifPresent(c -> System.out.println(c));
        } catch (HttpClientErrorException | ResourceAccessException err) {
            System.out.println("Error : " + err.getMessage());
            return;
        }

        answer = scanner.nextLine();
        switch (answer) {
            case "y":
                try {
                    String url = BASE_URL + BASE_COMPUTER + "/" + id;
                    restTemplate.delete(url);
                    System.out.println("Success !");
                } catch (HttpClientErrorException | ResourceAccessException err) {
                    System.out.println("Error : " + err.getMessage());
                }

                // service.delete(service.findById(id).get().getId());
                break;
            case "n":
            default:
                break;
        }

    }

    /**
     * Update a computer.
     */
    public void updateComputer() {
        String idString;
        long id;
        String answer;
        Computer computer = null;
        // New Computer var
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
            try {
                id = Long.parseLong(idString);
            } catch (NumberFormatException e) {
                System.out.println("Invalid id format provided. Exiting...");
                return;
            }
        } else {
            System.out.println("Error" +
                    ", empty id.");
            return;
        }
        try {
            String url = BASE_URL + BASE_COMPUTER + "/" + id;
            ComputerDto computerDto = restTemplate.getForObject(url, ComputerDto.class);
            Optional<Computer> computerO = ComputerDtoMapper.mapComputerFromComputerDto(Optional.of(computerDto));
            if (computerO.isPresent()) {
                computer = computerO.get();
            } else {
                System.out.println("Empty computer");
                return;
            }
        } catch (HttpClientErrorException | ResourceAccessException err) {
            System.out.println("Error : " + err.getMessage());
            return;
        }


        System.out.println("Name: [" + computer.getName().get() + "] (e)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
            case "e": // edit
                System.out.println("Enter new name");
                newName = scanner.nextLine();
                newName = newName.trim();
                if (StringUtils.isBlank(newName)) {
                    System.out.println("Error: Empty name (required).");
                    return;
                }
                break;
            default:
                newName = computer.getName().get();
                break;
        }

        if (computer.getIntroduced().isPresent()) {
            System.out.println("Date Introduced: [" + computer.getIntroduced().get() + "] (e/d)");
        } else {
            System.out.println("Date Introduced: [no date] (e)");
        }

        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
            case "e": // edit
                System.out.println("Enter new date introduced (DD-MM-YYYY)");
                newDateIntroducedString = scanner.nextLine();
                newDateIntroducedString = newDateIntroducedString.trim();
                if (!newDateIntroducedString.isEmpty()) {
                    try {
                        newDateIntroduced = DateUtil.stringToDate(newDateIntroducedString);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error invalid date");
                        return;
                    }
                }
                break;
            case "d": // delete
                newDateIntroduced = null;
                break;
            default:
                if (computer.getIntroduced().isPresent()) {
                    newDateIntroduced = computer.getIntroduced().get();
                }
                break;
        }

        System.out.println("Date Discontinued: [" + computer.getDiscontinued() + "] (e/d)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
            case "e": // edit
                System.out.println("Enter new date discontinued (DD-MM-YYYY)");
                newDateDiscontinuedString = scanner.nextLine();
                newDateDiscontinuedString = newDateDiscontinuedString.trim();
                if (!newDateDiscontinuedString.isEmpty()) {
                    try {
                        newDateDiscontinued = DateUtil.stringToDate(newDateDiscontinuedString);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error invalid date");
                        return;
                    }
                }
                break;
            case "d": // delete
                newDateDiscontinued = null;
                break;
            default:
                if (computer.getDiscontinued().isPresent()) {
                    newDateIntroduced = computer.getDiscontinued().get();
                }
                break;
        }

        System.out.println("Compagny (id): [" + computer.getCompany() + "] (e/d)");
        answer = null;
        answer = scanner.nextLine();
        switch (answer) {
            case "e": // edit
                System.out.println("Enter new company id");
                newIdCompanyString = scanner.nextLine();
                newIdCompanyString = newIdCompanyString.trim();
                if (!newIdCompanyString.isEmpty()) {
                    try {
                        newIdCompany = Long.parseLong(newIdCompanyString);
                    } catch (NumberFormatException e) {
                        System.out.println("Error invalid company id");
                        return;
                    }
                }


                try {
                    String url = BASE_URL + BASE_COMPANY + "/" + newIdCompany;
                    CompanyDto companyDto = restTemplate.getForObject(url, CompanyDto.class);
                    Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));

                    if (company.isPresent()) {
                        newCompany = company.get();
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
                    } else {
                        System.out.println("Company not found.");
                        break;
                    }
                    company.ifPresent(c -> System.out.println(c));
                } catch (HttpClientErrorException | ResourceAccessException err) {
                    System.out.println("Error : " + err.getMessage());
                }


                break;
            case "d": // delete
                newCompany = null;
                break;
            default:
                break;
        }


        computer = new Computer.ComputerBuilder(computer.getId(), newName).withIntroduced(newDateIntroduced)
                .withDiscontinued(newDateDiscontinued).withCompany(newCompany).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = BASE_URL + BASE_COMPUTER;

        Optional<ComputerDto> computerDto = ComputerDtoMapper.mapComputerDtoFromComputer(Optional.of(computer));

        System.out.println(computerDto.get());
        HttpEntity<ComputerDto> request = new HttpEntity<ComputerDto>(computerDto.get(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);


        if (Control.isValidComputer(Optional.ofNullable(computer))) {
            // service.saveOrUpdate(computer);
        }
    }

    public void deleteCompany() {
        long id;
        String answer;

        System.out.println("Please enter the company id you want to deleted");
        try {
            id = scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Not a valid input, exiting.");
            return;
        }
        scanner.nextLine(); // consume newline;

        System.out.println("Are you sure you want delete (y/n)");

        try {
            String url = BASE_URL + BASE_COMPANY + "/" + id;
            CompanyDto companyDto = restTemplate.getForObject(url, CompanyDto.class);
            Optional<Company> company = CompanyDtoMapper.mapCompanyFromCompanyDto(Optional.of(companyDto));
            company.ifPresent(c -> System.out.println(c));
        } catch (HttpClientErrorException | ResourceAccessException err) {
            System.out.println("Error : " + err.getMessage());
        }

        answer = scanner.nextLine();
        switch (answer) {
            case "y":
                try {
                    String url = BASE_URL + BASE_COMPANY + "/" + id;
                    restTemplate.delete(url);
                    System.out.println("Success !");
                } catch (HttpClientErrorException | ResourceAccessException err) {
                    System.out.println("Error : " + err.getMessage());
                }

                // service.delete(service.findById(id).get().getId());
                break;
            case "n":
            default:
                break;
        }
    }

    /**
     * Quit the program.
     */
    public void closeClientConsole() {
        exit = true;
    }
}