package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.service.ComputerDatabaseService;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public List<Computer> mapRows(ResultSet rs) {
		//TODO
		//if(rs == null)
		//	throw new Exception();
		
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			while(rs.next()){
				long id = rs.getLong("id");
				String name = rs.getString("name");
				Date introduced = rs.getTimestamp("introduced");
				Date discontinued = rs.getTimestamp("discontinued");
				
				long companyId = rs.getLong("company_id");
				//Try to retrieve a company from the database with this id
				ComputerDatabaseService cdbService = ComputerDatabaseService.getInstance();
				
				Company company = cdbService.getCompanyById(companyId);
				Computer c = new Computer(id,name,introduced,discontinued,company);
				
				computers.add(c);
			}
			return computers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Computer mapRow(ResultSet rs) {
		//TODO assert 1 result
		try {
			if(!rs.isBeforeFirst())
				return null;
			rs.next();
			long id = rs.getLong("id");
			String name = rs.getString("name");
			Date introduced = rs.getTimestamp("introduced");
			Date discontinued = rs.getTimestamp("discontinued");
			
			
			long companyId = rs.getLong("company_id");
			//Try to retrieve a company from the database with this id
			ComputerDatabaseService cdbService = ComputerDatabaseService.getInstance();
			
			Company company = cdbService.getCompanyById(companyId);
			Computer computer = new Computer(id,name,introduced,discontinued,company);
			return computer;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public int mapCount(ResultSet rs){
		int count;
		try {
			if(!rs.isBeforeFirst())
				return 0;
			rs.next();
			count = rs.getInt("c");
			
			return count;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return 0;
	}

}
