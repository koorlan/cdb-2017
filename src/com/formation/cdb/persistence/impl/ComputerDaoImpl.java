package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.ComputerDao;

public class ComputerDaoImpl implements ComputerDao {

	private Connection conn;
	
	public ComputerDaoImpl(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public void create(Computer e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long id, Computer e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Computer get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String GET_ALL = "Select * from computer"; 
	
	@Override
	public List<Computer> getAll() {
		List<Computer> computers;
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(GET_ALL);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}

}
