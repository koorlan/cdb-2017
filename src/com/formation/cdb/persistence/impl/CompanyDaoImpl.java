package com.formation.cdb.persistence.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;

public enum CompanyDaoImpl implements Dao<Company> {

	INSTANCE;
	
	private CompanyDaoImpl(){}

	@Override
	public void create(Company e) {
		// TODO Auto-generated method stub
		
	}
	
	private static final String READ_BY_ID = "SELECT * FROM company WHERE id=?";

	@Override
	public Company readById(long id) {
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			return new Company(0, null);
		}
			
		try {
			
			PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
			stmt.setLong(1, id);
			Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
			Optional<Company> optionalCompany = CompanyRowMapper.INSTANCE.mapObjectFromOneRow(rs);
			if(optionalCompany.isPresent()) {
				return optionalCompany.get();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	
		
		return new Company(0, null);
	}

	@Override
	public void update(Company e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Company e) {
		// TODO Auto-generated method stub
		
	}

	private static final String READ_ALL_LIMIT = "SELECT * FROM company LIMIT ?,?";
	
	@Override
	public List<Company> readAllWithOffsetAndLimit(int offset, int limit) {
		
		List<Company> companies =  new ArrayList<>();
		
		if(offset < 0 || limit < 0) {
			return companies;
		}
		
		Optional<ResultSet> rs;
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(connection.isPresent()) {
			try {
				PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
				stmt.setInt(1, offset);
				stmt.setInt(2, limit);
				
				rs = Optional.ofNullable(stmt.executeQuery());
				
				Optional<List<Optional<Company>>> optionalCompanies = CompanyRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(rs);
				
				if(optionalCompanies.isPresent()){
					for(Optional<Company> c : optionalCompanies.get()) {
						if(!c.isPresent()) {
							companies = null;
							companies = new ArrayList<>();
						} else {
							companies.add(c.get());
						}
					}
					
					return companies;
				}

			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				
			}
			
		}
		return companies;
	}

	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM company";
	
	@Override
	public int rowCount() {
		int count = 0;
		Optional<ResultSet> rs;
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

		if(connection.isPresent()){
			try {
				rs = Optional.ofNullable(connection.get().prepareStatement(ROW_COUNT).executeQuery());
				count = RowMapper.mapCountResult(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return count;
	};
	
	
}
