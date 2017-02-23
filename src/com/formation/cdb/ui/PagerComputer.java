package com.formation.cdb.ui;

import java.util.List;

import com.formation.cdb.model.impl.Computer;

import com.formation.cdb.service.impl.ComputerServiceImpl;


public class PagerComputer extends Pager<Computer> {
	
	public PagerComputer(){
		super();
			
		ComputerServiceImpl service;
		service = ComputerServiceImpl.INSTANCE;
		max = service.sizeOfTable();
		nbPages = max/pageSize;
	}
	
	public List<Computer> getPage(int page){
		int index;
		int offset;
		int limit;
		
		index = (page>nbPages) ? nbPages:page;
		ComputerServiceImpl service;
		service = ComputerServiceImpl.INSTANCE;
		
		offset = (index*pageSize);
		limit = pageSize;
		return service.readAllWithOffsetAndLimit(offset, limit);
	}
	
}
