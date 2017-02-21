package com.formation.cdb.model.impl;


public class Company {
	long id;
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
	public String getName() {
		return name;
	}
	
	public long getId(){
		return id;
	}
	
	/**
	 * Override
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name +" (id: "+ id +")");
		return sb.toString();
	}
	
	
	
	
}
