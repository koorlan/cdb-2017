package com.formation.cdb.exception;

public class MapperException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5421106821790415336L;
	
	public MapperException(){
		super();
		System.out.println("MapperException");
	}
	
	public MapperException(String s){
		super(s);
		System.out.println("MapperException: " + s);
	}
}
