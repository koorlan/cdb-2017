package com.formation.cdb.persistence;

import java.sql.Connection;

import com.formation.cdb.model.Computer;

public class DaoFactory {
	
	protected static Connection conn ;
	
	public static ComputerDao getComputerDao(){
		return new ComputerDao();
	};
	public static CompanyDao getCompanyDao(){
		return new CompanyDao();
	};
}
