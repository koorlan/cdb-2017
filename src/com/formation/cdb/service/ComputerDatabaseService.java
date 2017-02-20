package com.formation.cdb.service;

public class ComputerDatabaseService {
	private static ComputerDatabaseService INSTANCE;
	
	public ComputerDatabaseService(){
	}

	public static ComputerDatabaseService getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ComputerDatabaseService();
		}
		return INSTANCE;
	}
}
