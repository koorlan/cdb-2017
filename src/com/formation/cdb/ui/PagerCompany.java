package com.formation.cdb.ui;

import java.util.List;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.service.ComputerDatabaseService;

public class PagerCompany extends Pager<Company> {

	public PagerCompany(){
		super();
		
		ComputerDatabaseService service;
		service = ComputerDatabaseService.getInstance();
		max = service.rowCountCompany();
		nbPages = max/pageSize;
	}
	
	@Override
	public List<Company> getPage(int page) {
		int index;
		int offset;
		int limit;
		
		index = (page>nbPages) ? nbPages:page;
		ComputerDatabaseService service;
		service = ComputerDatabaseService.getInstance();
		
		offset = (index*pageSize);
		limit = pageSize;
		return service.getAllCompanies(offset, limit);
	}

}
