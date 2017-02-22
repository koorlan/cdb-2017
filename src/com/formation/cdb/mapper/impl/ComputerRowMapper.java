package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.exception.MapperException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.service.ComputerDatabaseService;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Optional<List<Computer>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {
		
		if( !rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}		
		
		try {
			
			ResultSet 		r			=	rs.get()					;
			List<Computer> 	computers	=	new ArrayList<Computer>()	;
			
			while(r.next()){
				
				long	id				=	r.getLong("id")					;
				String	name			=	r.getString("name")				;
				Date	introduced		=	r.getTimestamp("introduced")	;
				Date	discontinued	=	r.getTimestamp("discontinued")	;
				
				//Try to retrieve a company from the database with this id
				long						companyId		=	r.getLong("company_id")									;
				ComputerDatabaseService		cdbService		= 	ComputerDatabaseService.getInstance()					;
				Company 					company 		=	cdbService.getCompanyById(companyId)					;
				Computer 					computer		=	new Computer(id,name,introduced,discontinued,company)	;
				
				computers.add(computer);
			}
	
			return Optional.ofNullable(computers);
		} catch (SQLException e) {
			
			//TODO
			
		}
		
		return Optional.empty();
	}

	@Override
	public Computer mapObjectFromOneRow(Optional<ResultSet> rs) {
		if(rs == null)
			throw new MapperException("Null ResultSet");
		
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
		if(rs == null)
			throw new MapperException("Null ResultSet");
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
