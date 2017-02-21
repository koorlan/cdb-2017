package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.model.impl.Company;

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

	private static final String GET_ID = "SELECT * FROM company WHERE id=?";
	
	@Override
	public Company get(long id) {
		Company company;
		
		try {
			PreparedStatement st = conn.prepareStatement(GET_ID);
			st.setInt(1, (int)id);
			ResultSet rs = st.executeQuery();
			company = companyRowMapper.mapRow(rs);
			return company;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private static final String GET_ALL = "SELECT * FROM company LIMIT ?,?";
	
	@Override
	public List<Company> getAll(int offset, int limit) {
		List<Company> companies;
		PreparedStatement st;
		ResultSet rs;
		
		try {
			st = conn.prepareStatement(GET_ALL);
			st.setInt(1, offset);
			st.setInt(2, limit);
			rs = st.executeQuery();
			companies = companyRowMapper.mapRows(rs);
			return companies;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return empty new arrayList if no result
		return null;
	}
	
	
	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM company";
	
	@Override
	public int rowCount(){
		int count = 0;
		PreparedStatement st;
		ResultSet rs;
		
		try {
			st = conn.prepareStatement(ROW_COUNT);
			rs = st.executeQuery();
			count = companyRowMapper.mapCount(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
