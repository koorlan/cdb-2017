package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.model.Company;

public class CompanyDaoImpl implements com.formation.cdb.persistence.CompanyDao {

	private Connection conn;
	private CompanyRowMapper companyRowMapper;
	
	public CompanyDaoImpl(Connection conn){
		this.conn = conn;
		this.companyRowMapper = new CompanyRowMapper();
	}
	
	@Override
	public void create(Company e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long id, Company e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	private static final String GET_ID = "SELECT * FROM company WHERE id=";
	
	@Override
	public Company get(long id) {
		Company company;
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(GET_ID+Long.toString(id));
			company = companyRowMapper.mapRow(rs);
			return company;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Company> getAll() {
		return null;
	}

}
