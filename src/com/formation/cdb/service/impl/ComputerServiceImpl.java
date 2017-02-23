package com.formation.cdb.service.impl;

import java.util.List;

import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;
import com.formation.cdb.service.CDBService;

public enum ComputerServiceImpl implements CDBService<Computer> {

	INSTANCE;
	private ComputerServiceImpl(){};
	
	@Override
	public void create(Computer e) {
		 ComputerDaoImpl.INSTANCE.create(e);
		 return;
	}

	@Override
	public Computer readById(long id) {
		return ComputerDaoImpl.INSTANCE.readById(id);
	}

	@Override
	public void update(Computer e) {
		ComputerDaoImpl.INSTANCE.update(e);
	}

	@Override
	public void delete(Computer e) {
		ComputerDaoImpl.INSTANCE.delete(e);
		return;
	}

	@Override
	public List<Computer> readAllWithOffsetAndLimit(int offset, int limit) {
		return ComputerDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit);
	}

	@Override
	public int sizeOfTable() {
		return ComputerDaoImpl.INSTANCE.rowCount();
	}

}
