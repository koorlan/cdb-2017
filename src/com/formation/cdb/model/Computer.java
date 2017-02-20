package com.formation.cdb.model;

import java.util.Date;

public class Computer {
	
	//BIGINT on SQL DB
	private long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	/**
	 * Constructor
	 */
	public Computer(long id, String name, Date introduced, Date discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	
	/**
	 * Getters
	 */

	public long getId() {
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
	
	/**
	 * Override
	 */
	
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append(name +" (id: "+id+")");
		sb.append(newLine);
		sb.append("| Company -> " + company);
		sb.append(newLine);
		sb.append("| Introduced -> " + introduced);
		sb.append(newLine);
		sb.append("| Discontinued -> " + discontinued);
		return sb.toString();
	}
	
	
}