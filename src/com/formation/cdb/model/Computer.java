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
	
	
}
