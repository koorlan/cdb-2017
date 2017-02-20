package com.formation.cdb.service;

import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.persistence.DaoFactory;

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

	/**
	 * Methods
	 */

	public List<Computer> getAllComputers(){
		ComputerDao dao = DaoFactory.getComputerDao();
		dao.getAll();
		return null;
	}
	
	public List<Company> getAllCompanies(){
		return null;
	}
	
	public Computer getComputerById(long id){
		return null;
	}
	
	public Company getCompanyById(long id){
		return null;
	}
	
	public void deleteComputer(long id){
		
	}
	
	public void updateComputer(long id){
		
	}
	
	public void createComputer(long id){
		
	}
}
