package com.formation.cdb.service;

import java.util.Date;
import java.util.List;

import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.persistence.Dao;
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

	public List<Computer> getAllComputers(int offset, int limit){
		Dao<Computer> dao = DaoFactory.getComputerDao();
		try {
			return dao.getAll(offset,limit);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Company> getAllCompanies(int offset, int limit){
		Dao<Company> dao = DaoFactory.getCompanyDao();
		try {
			return dao.getAll(offset,limit);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Computer getComputerById(long id){
		Dao<Computer> dao = DaoFactory.getComputerDao();
		return dao.get(id);
	}
	
	public Company getCompanyById(long id){
		Dao<Company> dao = DaoFactory.getCompanyDao();
		return dao.get(id);
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
