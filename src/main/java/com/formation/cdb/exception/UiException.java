package com.formation.cdb.exception;

public class UiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8842295662544120454L;
	
	public UiException(){
		super();
		System.out.println("UiException");
	}
	
	public UiException(String s){
		super(s);
		System.out.println("UiException: " + s);
	}
}
