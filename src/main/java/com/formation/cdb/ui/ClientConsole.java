package com.formation.cdb.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.entities.PagerCompany;
import com.formation.cdb.entities.PagerComputer;
import com.formation.cdb.entities.impl.Company;
import com.formation.cdb.entities.impl.Computer;
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.formation.cdb.service.impl.ComputerServiceImpl;
import com.formation.cdb.util.DateUtil;


public class ClientConsole {
	
	private boolean exit;
	private final Scanner scanner = new Scanner(System.in);
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	
	/**
	 * Constructor
	 */
	
	public ClientConsole(){
		exit = false;
	}
	
	/**
	 * Main Loop
	 * @param args
	 */
	
	public static void main(String args[]){
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
		ClientConsole c = new ClientConsole();
		while(!c.isExit()){
			c.showMenu();
			c.clearScreen();
		}
	}
	
	/**
	 * Getters
	 */
	public boolean isExit(){
		return exit;
	}
	
	/**
	 * Methods
	 */
	public void showMenu(){
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
		clearScreen();
		switch(s){
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
	
	public void listComputers(){
		PagerComputer pager;
		String command;
		boolean exit;
		int index;
		int num = 0;
		String intString;
		
		pager = new PagerComputer();
		exit = false;
		index = pager.getCurrentPageIndex();
		do{
			clearScreen();
			if(!pager.getPage(index).isPresent()) {
				break;
			}
			for(Optional<Computer> c : pager.getPage(index).get()){
				c.ifPresent(x -> System.out.println(x));
			}
	
			System.out.println(index+" of "+ pager.getNbPages() +" (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
			command = scanner.nextLine();
			switch(command){
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
				if(	intString.isEmpty())
					num = Integer.parseInt(intString);
				index = num;
				break;
			case "x":
				exit = true;
				break;
			}
			
		}while(!exit);
		
	};
	
	public void listCompanies(){
		PagerCompany pager;
		String command;
		boolean exit;
		int index;
		int num = 0;
		String intString;
		
		pager = new PagerCompany();
		exit = false;
		index = pager.getCurrentPageIndex();
		do{
			clearScreen();
			if(!pager.getPage(index).isPresent()) {
				break;
			}
			for(Optional<Company> c : pager.getPage(index).get()){
				c.ifPresent(x -> System.out.println(x));
			}
	
			System.out.println(index+" of "+ pager.getNbPages() +" (g) Goto page (n) Next page (p) Previous page (x) Return to menu");
			command = scanner.nextLine();
			switch(command){
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
				if(	intString.isEmpty())
					num = Integer.parseInt(intString);
				index = num;
				break;
			case "x":
				exit = true;
				break;
			}
			
		}while(!exit);
	};
	
	public void showComputer(){
		ComputerServiceImpl service;
		long id;
		Computer c;
		
		service = ComputerServiceImpl.INSTANCE;
		System.out.println("Please enter computer id:");
		id = scanner.nextLong();
		scanner.nextLine();//Consume Enter
		service.readById(id).ifPresent(x -> System.out.println(x));
		
	};
	
	public void showCompany(){
		CompanyServiceImpl service;
		long id;
		Company c;
		
		service = CompanyServiceImpl.INSTANCE;
		System.out.println("Please enter company id:");
		id = scanner.nextLong();
		scanner.nextLine();//Consume Enter
		service.readById(id).ifPresent(x -> System.out.println(x));	
	};
	
	public void createComputer(){
		ComputerServiceImpl service ;
		String name;
		LocalDate introduced = null;
		LocalDate discontinued = null;
		long companyId = 0 ;
		String dateIntroducedString;
		String dateDiscontinuedString;
		String companyIdString;
		
		service = ComputerServiceImpl.INSTANCE;
		
		System.out.println("Please enter computer name [Required]:");
		name = scanner.nextLine();
		if(name.isEmpty())
			return;
		//TODO
		//if(name.trim().isEmpty())
		//	throw
		System.out.println("Please enter the date it was introduced (DD-MM-YYYY) [Optional]:");
		dateIntroducedString = scanner.nextLine();
		dateIntroducedString = dateIntroducedString.trim();
		if(!dateIntroducedString.isEmpty())		
			introduced = DateUtil.StringToDate(dateIntroducedString);
		
		System.out.println("Please enter the date it was discontinued (DD-MM-YYYY) [Optional]:");
		dateDiscontinuedString = scanner.nextLine();
		dateDiscontinuedString = dateDiscontinuedString.trim();
		if(!dateDiscontinuedString.isEmpty())
			discontinued = DateUtil.StringToDate(dateDiscontinuedString);
		
		System.out.println("Please enter the company id of the computer manufacturer [Optional]:");
		companyIdString = scanner.nextLine();
		companyIdString = companyIdString.trim();
		if(!companyIdString.isEmpty())
			companyId = Long.parseLong(companyIdString);
		
		
		Optional<Company> company = CompanyServiceImpl.INSTANCE.readById(companyId);
		Computer computer = new Computer(0, name, introduced, discontinued, company.orElse(null));

		//String test = Optional.ofNullable(computer).map(Computer::getCompany).map(Company::getName).orElse("UNKNOWN");
		
		service.create(Optional.ofNullable(computer));
	};
	
	public void deleteComputer(){
		ComputerServiceImpl service;
		long id;
		Computer computer;
		String answer;
		
		service = ComputerServiceImpl.INSTANCE;
		
		System.out.println("Please enter the computer id you want to deleted");
		id = scanner.nextLong();
		scanner.nextLine(); //consume newline;
		
		
		System.out.println("Are you sure you want delete (y/n)");
		service.readById(id).ifPresent(x -> System.out.println(x));
		
		answer = scanner.nextLine();
		switch(answer){
			case"y":
				service.delete(service.readById(id));
				break;
			case "n":
			default:
				break;
		}
		
		
	};
	
	public void updateComputer(){
		ComputerServiceImpl service;
		String idString;
		long id;
		Computer computer;
		String answer;
		//New Computer var
		String newName;
		String newDateIntroducedString;
		LocalDate newDateIntroduced = null;
		String newDateDiscontinuedString;
		LocalDate newDateDiscontinued = null;
		String newIdCompanyString;
		long newIdCompany = 0;
		Company newCompany;
		
		service = ComputerServiceImpl.INSTANCE;
		System.out.println("Please enter the computer id you want to update");
		idString = scanner.nextLine();
		idString = idString.trim();
		if(!idString.isEmpty())
			id = Long.parseLong(idString);
		else
			return;
		
		Optional<Computer> optionalComputer = service.readById(id);
		if(!optionalComputer.isPresent()) {
			return;
		}
		computer = optionalComputer.get();
		
		System.out.println("Name: ["+ computer.getName() +"] (e)");
		answer = null;
		answer = scanner.nextLine();
		switch(answer){
		case "e": //edit
			System.out.println("Enter new name");
			newName = scanner.nextLine();
			newName = newName.trim();
			if(!newName.isEmpty())
				computer.setName(newName);
			else 
				return;
			break;
		default:			
			break;
		}
		
		System.out.println("Date Introduced: ["+ computer.getIntroduced() +"] (e/d)");
		answer = null;
		answer = scanner.nextLine();
		switch(answer){
		case "e": //edit
			System.out.println("Enter new date introduced (DD-MM-YYYY)");
			newDateIntroducedString = scanner.nextLine();
			newDateIntroducedString = newDateIntroducedString.trim();
			if(!newDateIntroducedString.isEmpty())		
				newDateIntroduced = DateUtil.StringToDate(newDateIntroducedString);
			computer.setIntroduced(newDateIntroduced);
			break;
		case "d": //delete
			computer.setIntroduced(null);
			break;
		default:			
			break;
		}
		
		System.out.println("Date Discontinued: ["+ computer.getDiscontinued() +"] (e/d)");
		answer = null;
		answer = scanner.nextLine();
		switch(answer){
		case "e": //edit
			System.out.println("Enter new date discontinued (DD-MM-YYYY)");
			newDateDiscontinuedString = scanner.nextLine();
			newDateDiscontinuedString = newDateDiscontinuedString.trim();
			if(!newDateDiscontinuedString.isEmpty())		
				newDateDiscontinued = DateUtil.StringToDate(newDateDiscontinuedString);
			computer.setDiscontinued(newDateDiscontinued);
			break;
		case "d": //delete
			computer.setDiscontinued(null);
			break;
		default:			
			break;
		}
		
		System.out.println("Compagny (id): ["+ computer.getCompany() +"] (e/d)");
		answer = null;
		answer = scanner.nextLine();
		switch(answer){
		case "e": //edit
			System.out.println("Enter new company id");
			newIdCompanyString = scanner.nextLine();
			newIdCompanyString = newIdCompanyString.trim();
			if(!newIdCompanyString.isEmpty())
				newIdCompany = Long.parseLong(newIdCompanyString);
			
			Optional<Company> optionalCompany = CompanyServiceImpl.INSTANCE.readById(newIdCompany);
			newCompany = optionalCompany.get();
			
			System.out.println("Company :" +newCompany + " (y/n)");
			answer = null;
			answer = scanner.nextLine();
			switch(answer){
				case "y":
					computer.setCompany(newCompany);
					break;
				default:
					break;
			}
			
			break;
		case "d": //delete
			computer.setCompany(null);
			break;
		default:			
			break;
		}
		service.update(Optional.ofNullable(computer));
		
	};
	
	public void closeClientConsole(){
		exit = true;
	}
	
	public void clearScreen(){
		//Linux
		try {
			Runtime.getRuntime().exec("clear");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
