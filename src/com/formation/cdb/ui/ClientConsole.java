package com.formation.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.service.ComputerDatabaseService;

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
		
		s = scanner.next();
		
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
				createComputer();
				break;
			case "u":
				updateComputer();
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
		c = service.getComputerById(id);
		System.out.println(c);
		
	};
	
	public void showCompany(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
	};
	
	public void createComputer(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
	};
	
	public void deleteComputer(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
	};
	
	public void updateComputer(){
		ComputerDatabaseService service = ComputerDatabaseService.getInstance();
	};
	
}
