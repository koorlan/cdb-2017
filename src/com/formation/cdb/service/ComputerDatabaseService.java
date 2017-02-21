package com.formation.cdb.service;

import java.util.Date;
import java.util.List;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
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

	public List<Computer> getAllComputers(int offset, int limit){
		ComputerDao dao = DaoFactory.getComputerDao();
		return dao.getAll(offset,limit);
	}
	
	public List<Company> getAllCompanies(int offset, int limit){
		CompanyDao dao = DaoFactory.getCompanyDao();
		return dao.getAll(offset,limit);
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
		ComputerDao dao = DaoFactory.getComputerDao();
		dao.delete(id);
	}
	
	public void updateComputer(long id, Computer computer){
		ComputerDao dao = DaoFactory.getComputerDao();
		dao.update(id, computer);
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
	
	public int rowCountComputer(){
		ComputerDao dao;
		dao = DaoFactory.getComputerDao();
		return dao.rowCount();
	}
	
	public int rowCountCompany(){
		CompanyDao dao;
		dao = DaoFactory.getCompanyDao();		
		return dao.rowCount();
	}
}
