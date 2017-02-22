package com.formation.cdb.model.impl;

import java.util.Date;

import com.formation.cdb.model.Entity;


public class Computer implements Entity{
	
	long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	/**
	 * Constructor
	 */
	public Computer(long id, String name, Date introduced, Date discontinued, Company company) {
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
	
	public String getName() {
		return name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public Company getCompany() {
		return company;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(Date discontinued) {
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
