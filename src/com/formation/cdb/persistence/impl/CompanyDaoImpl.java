package com.formation.cdb.persistence.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

	@Override
	public Company readById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Company e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Company e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Company> readAllWithOffsetAndLimit(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
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
				count = CompanyRowMapper.INSTANCE.mapCount(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return count;
	};
	
	
}
