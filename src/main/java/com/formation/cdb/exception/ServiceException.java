package com.formation.cdb.exception;

public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1499048516108095645L;


	public ServiceException(){
		super();
		System.out.println("ServiceException");
	}
	
	public ServiceException(String s){
		super(s);
		System.out.println("ServiceException: " + s);
	}
}
