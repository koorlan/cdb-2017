package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.CompanyDao;
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
		return dao.getAll();
	}
	
	public List<Company> getAllCompanies(){
		CompanyDao dao = DaoFactory.getCompanyDao();
		return dao.getAll();
	}
	
	public Computer getComputerById(long id){
		ComputerDao dao = DaoFactory.getComputerDao();
		return dao.get(id);
	}
	
	public Company getCompanyById(long id){
		CompanyDao dao = DaoFactory.getCompanyDao();
		return dao.get(id);
	}
	
	public void deleteComputer(long id){
		
	}
	
	public void updateComputer(long id){
		
	}
	
	public void createComputer(String name,Date introduced, Date discontinued,long companyId){
		ComputerDao computerDao;
		Computer computer;
		CompanyDao companyDao;
		Company company;
		
		companyDao = DaoFactory.getCompanyDao();
		company = companyDao.get(companyId);
		computer = new Computer(0,name,introduced,discontinued,company);
		
		computerDao = DaoFactory.getComputerDao();
		computerDao.create(computer);
	}
}
