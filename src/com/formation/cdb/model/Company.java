package com.formation.cdb.model;

public class Company {
	private long id;
	private String name;

	/**
	 * Constructor
	 */
	
	public Company(long id, String name){
		this.id = id;
		this.name = name;
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
	
	/**
	 * Override
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name +" (id: "+id+")");
		return sb.toString();
	}
	
	
	
	
}
