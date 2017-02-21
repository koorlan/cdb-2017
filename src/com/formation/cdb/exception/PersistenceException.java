package com.formation.cdb.exception;

public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262868307263845216L;
	
	public PersistenceException(){
		super();
		System.out.println("PersistenceException");
	}
	
	public PersistenceException(String s){
		super(s);
		System.out.println("PersistenceException: " + s);
	}
}
