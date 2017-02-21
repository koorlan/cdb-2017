package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public List<Company> mapRows(ResultSet rs) {
		
		List<Company> companies = new ArrayList<Company>();
		
		try {
			while(rs.next()){
				long id = rs.getLong("id");
				String name = rs.getString("name");
				Company company = new Company(id,name);		
				companies.add(company);
			}
			return companies;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Company mapRow(ResultSet rs) {
		//TODO Assert 1 result
		try {
			if(!rs.isBeforeFirst())
				return null;
			rs.next();
			long id = rs.getLong("id");
			String name = rs.getString("name");
			Company company;
			company = new Company(id,name);
			return company;
			
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
