package com.formation.cdb.entities.impl;

import java.util.Optional;

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
	public Optional<String> getName() {
		return Optional.ofNullable(name);
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
