package com.formation.cdb.persistence;

import java.sql.Connection;

import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;

public class DaoFactory {
	
	protected static Connection conn = ConnectionManager.getInstance().getConnection() ;
	
	public static ComputerDao getComputerDao(){
		return new ComputerDaoImpl(conn);
	};
	public static CompanyDao getCompanyDao(){
		return new CompanyDaoImpl(conn);
	};
}
