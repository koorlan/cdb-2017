package com.formation.cdb.exception;

public class ModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9113759331188994990L;

	public ModelException(){
		super();
		System.out.println("ModelException");
	}
	
	public ModelException(String s){
		super(s);
		System.out.println("ModelException: " + s);
	}
}
