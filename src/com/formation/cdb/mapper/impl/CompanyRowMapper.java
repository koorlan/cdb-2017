package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.exception.MapperException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public List<Company> mapRows(ResultSet rs) throws MapperException{
		
		List<Company> companies;
		long id;
		String name;
		Company company;
		
		if(rs == null)
			throw new MapperException("Null ResultSet");
		
		companies = new ArrayList<Company>();
		try {
			while(rs.next()){
				id = rs.getLong("id");
				name = rs.getString("name");
				company = new Company(id,name);		
				companies.add(company);
			}
			return companies;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Company mapRow(ResultSet rs) throws MapperException{
		
		if(rs == null)
			throw new MapperException("Null ResultSet");
		//TODO Assert 1 result
		try {
			if(!rs.isBeforeFirst()) //Empty ResultSet
				return null;
			rs.next();
			long id = rs.getLong("id");
			String name = rs.getString("name");
			Company company;
			company = new Company(id,name);
			return company;
			
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public int mapCount(ResultSet rs) throws MapperException{
		
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
			return 0;
		}	
	}
}
