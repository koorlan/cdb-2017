package com.formation.cdb.ui;

import java.util.List;

import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.service.ComputerDatabaseService;


public class PagerComputer extends Pager<Computer> {
	
	public PagerComputer(){
		super();
			
		ComputerDatabaseService service;
		service = ComputerDatabaseService.getInstance();
		max = service.rowCountComputer();
		nbPages = max/pageSize;
	}
	
	public List<Computer> getPage(int page){
		int index;
		int offset;
		int limit;
		
		index = (page>nbPages) ? nbPages:page;
		ComputerDatabaseService service;
		service = ComputerDatabaseService.getInstance();
		
		offset = (index*pageSize);
		limit = pageSize;
		return service.getAllComputers(offset, limit);
	}
	
}
