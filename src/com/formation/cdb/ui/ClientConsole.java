package com.formation.cdb.ui;

import java.util.Date;
import java.util.Scanner;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
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
			
			for(Computer c : pager.getPage(index)){
				System.out.println(c);
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
			
			for(Company c : pager.getPage(index)){
				System.out.println(c);
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
		ComputerDatabaseService service;
		String idString;
		long id;
		Computer computer;
		String answer;
		//New Computer var
		String newName;
		String newDateIntroducedString;
		Date newDateIntroduced = null;
		String newDateDiscontinuedString;
		Date newDateDiscontinued = null;
		String newIdCompanyString;
		long newIdCompany = 0;
		Company newCompany;
		
		service = ComputerDatabaseService.getInstance();
		System.out.println("Please enter the computer id you want to update");
		idString = scanner.nextLine();
		idString = idString.trim();
		if(!idString.isEmpty())
			id = Long.parseLong(idString);
		else
			return;
		computer = service.getComputerById(id);
		
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
			newCompany = service.getCompanyById(newIdCompany);
			
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
		service.updateComputer(id,computer);
		
	};
	
	public void closeClientConsole(){
		exit = true;
	}
}
