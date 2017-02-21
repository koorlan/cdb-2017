package com.formation.cdb.persistence;

import java.sql.Connection;

import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;

public class DaoFactory {
	

	protected static Connection getConn() throws PersistenceException{
		return ConnectionManager.getInstance().getConnection();
	}
	
	public static ComputerDao getComputerDao(){
		try {
			return new ComputerDaoImpl(getConn());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	};
	public static CompanyDao getCompanyDao(){
		try {
			return new CompanyDaoImpl(getConn());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	};
}
