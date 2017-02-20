package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.util.List;

import com.formation.cdb.model.Company;

public class CompanyDaoImpl implements com.formation.cdb.persistence.CompanyDao {

	private Connection conn;
	
	public CompanyDaoImpl(Connection conn){
		this.conn = conn;
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

	@Override
	public Company get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getAll() {
		return null;
	}

}
