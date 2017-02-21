package com.formation.cdb.persistence;

import java.sql.Connection;

import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;

public class DaoFactory {
	

	protected static Connection getConn() throws PersistenceException{
		return ConnectionManager.getInstance().getConnection();
	}
	
	public static Dao<Computer> getComputerDao(){
		try {
			ComputerDaoImpl dao = new ComputerDaoImpl(getConn());
			return dao;
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	};
	public static Dao<Company> getCompanyDao(){
		try {
			return new CompanyDaoImpl(getConn());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	};
}
