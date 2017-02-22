package com.formation.cdb.service.impl;

import java.util.List;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.service.CDBService;

public enum CompanyServiceImpl implements CDBService<Company> {
	INSTANCE;
	
	private CompanyServiceImpl(){}

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
	public List<Company> readAllLimit(int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	};
	
}
