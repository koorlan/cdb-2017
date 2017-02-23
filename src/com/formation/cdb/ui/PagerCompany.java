package com.formation.cdb.ui;

import java.util.List;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.service.impl.CompanyServiceImpl;

public class PagerCompany extends Pager<Company> {

	public PagerCompany(){
		super();
		
		CompanyServiceImpl service;
		service = CompanyServiceImpl.INSTANCE;
		max = service.sizeOfTable();
		nbPages = max/pageSize;
	}
	
	@Override
	public List<Company> getPage(int page) {
		int index;
		int offset;
		int limit;
		
		index = (page>nbPages) ? nbPages:page;
		CompanyServiceImpl service;
		service = CompanyServiceImpl.INSTANCE;
		
		offset = (index*pageSize);
		limit = pageSize;
		return service.readAllWithOffsetAndLimit(offset, limit);
	}

}
