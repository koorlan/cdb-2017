package com.formation.cdb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.DaoFactory;

public class ComputerDatabaseService {
	private static ComputerDatabaseService INSTANCE;
	private  Logger logger = LoggerFactory.getLogger(getClass());
	
	
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

	public List<Computer> getAllComputers(int offset, int limit){
		logger.info("Getting computer dao");
		Dao<Computer> dao = DaoFactory.getComputerDao();
		try {
			List<Computer> computers = dao.getAll(offset,limit);
			logger.info("Sucessfully retrieved computers from db");
			if(computers == null)
				logger.warn("Compters list is null");
			if(computers!= null && computers.isEmpty())
				logger.warn("Computer list is empty");
			return computers;
		} catch (PersistenceException e) {
			logger.error("There were and error while retrieving computers from db");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Company> getAllCompanies(int offset, int limit){
		logger.info("Getting company dao");
		Dao<Company> dao = DaoFactory.getCompanyDao();
		try {
			List<Company> companies = dao.getAll(offset,limit);
			if(companies == null)
				logger.info("Company list is null");
			if(companies!= null && companies.isEmpty())
				logger.warn("Company list is empty");
			logger.info("Sucessfully retrieved compagnies from db");
			return companies;
		} catch (PersistenceException e) {
			logger.error("There were and error while retrieving companies from db");
		}
		return null;
	}
	
	public Computer getComputerById(long id){
		logger.info("Getting computer dao");
		Dao<Computer> dao = DaoFactory.getComputerDao();
		Computer computer = dao.get(id);
		logger.info("Successfully retrieved computer from db");
		return computer;
	}
	
	public Company getCompanyById(long id){
		logger.info("Getting company dao");
		Dao<Company> dao = DaoFactory.getCompanyDao();
		Company company = dao.get(id);
		logger.info("Successfully retireved company from db");
		return company;
	}
	
	public void deleteComputer(long id){
		Dao<Computer> dao = DaoFactory.getComputerDao();
		dao.delete(id);
	}
	
	public void updateComputer(long id, Computer computer){
		Dao<Computer> dao = DaoFactory.getComputerDao();
		try {
			dao.update(id, computer);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createComputer(String name,Date introduced, Date discontinued,long companyId){
		Dao<Computer> computerDao;
		Computer computer;
		Dao<Company> companyDao;
		Company company;
		
		companyDao = DaoFactory.getCompanyDao();
		company = companyDao.get(companyId);
		computer = new Computer(0,name,introduced,discontinued,company);
		
		computerDao = DaoFactory.getComputerDao();
		try {
			computerDao.create(computer);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int rowCountComputer(){
		Dao<Computer> dao;
		dao = DaoFactory.getComputerDao();
		return dao.rowCount();
	}
	
	public int rowCountCompany(){
		Dao<Company> dao;
		dao = DaoFactory.getCompanyDao();		
		return dao.rowCount();
	}
}
