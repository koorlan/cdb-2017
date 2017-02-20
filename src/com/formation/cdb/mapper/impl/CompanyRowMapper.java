package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sound.midi.Soundbank;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public List<Company> mapRows(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company mapRow(ResultSet rs) {
		int rowCount;
		
		
		try {
			//rs.last();
			//rowCount = rs.getRow();
			//rs.beforeFirst();
			
			//if(rowCount > 1)
			// Throw Exception
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

}
