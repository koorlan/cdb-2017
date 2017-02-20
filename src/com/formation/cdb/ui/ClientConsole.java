package com.formation.cdb.ui;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.service.ComputerDatabaseService;
import com.formation.cdb.util.DateUtil;

public class ClientConsole {
	
	private boolean exit;
	private final Scanner scanner = new Scanner(System.in);
	
	
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
		ClientConsole c = new ClientConsole();
		while(!c.isExit()){
			c.showMenu();
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
		ComputerDatabaseService service;
		List<Computer> computers;
		
		service = ComputerDatabaseService.getInstance();
		computers = service.getAllComputers();
		
		for(Computer c: computers){
			System.out.println(c);
		}
	};
	
	public void listCompanies(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
		List<Company> companies = service.getAllCompanies();
		for(Company c: companies){
			System.out.println(c);
		}
	};
	
	public void showComputer(){
		ComputerDatabaseService service;
		long id;
		Computer c;
		
		service = ComputerDatabaseService.getInstance();
		System.out.println("Please enter computer id:");
		id = scanner.nextLong();
		scanner.nextLine();//Consume Enter
		c = service.getComputerById(id);
		System.out.println(c);
		
	};
	
	public void showCompany(){
		ComputerDatabaseService service;
		long id;
		Company c;
		
		service = ComputerDatabaseService.getInstance();
		System.out.println("Please enter company id:");
		id = scanner.nextLong();
		scanner.nextLine();//Consume Enter
		c = service.getCompanyById(id);
		System.out.println(c);
	};
	
	public void createComputer(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
		String name;
		Date introduced = null;
		Date discontinued = null;
		long companyId = 0 ;
		String dateIntroducedString;
		String dateDiscontinuedString;
		String companyIdString;
		
		service = ComputerDatabaseService.getInstance();
		
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
		
		service.createComputer(name, introduced, discontinued, companyId);
	};
	
	public void deleteComputer(){
		ComputerDatabaseService service;
		long id;
		Computer computer;
		String answer;
		
		service = ComputerDatabaseService.getInstance();
		
		System.out.println("Please enter the computer id you want to deleted");
		id = scanner.nextLong();
		scanner.nextLine(); //consume newline;
		
		computer = service.getComputerById(id);
		
		System.out.println("Are you sure you want delete (y/n)");
		System.out.println(computer);
		
		answer = scanner.nextLine();
		switch(answer){
			case"y":
				service.deleteComputer(id);
				break;
			case "n":
			default:
				break;
		}
		
		
	};
	
	public void updateComputer(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
	};
	
	public void closeClientConsole(){
		exit = true;
	}
}
