package com.formation.cdb.entities.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import com.formation.cdb.entities.Entity;


public class Computer implements Entity{
	
	long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	/**
	 * Constructor
	 */
	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	
	/**
	 * Getters and Setters
	 */

	public long getId(){
		return id;
	}
	
	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
	public Optional<LocalDate> getIntroduced() {
		return Optional.ofNullable(introduced);
	}
	public Optional<LocalDate> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}
	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}


	/**
	 * Override
	 */
	
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append(name +" (id: "+id+")");
		
		if(company != null){
			sb.append(newLine);
			sb.append("| Company -> " + company);
			
		}
		if(introduced != null){
			sb.append(newLine);
			sb.append("| Introduced -> " + introduced);
			
		}
		if(discontinued != null){
			sb.append(newLine);
			sb.append("| Discontinued -> " + discontinued);
		}
		
		return sb.toString();
	}
	
	
}
