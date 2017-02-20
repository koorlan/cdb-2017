package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.mapper.impl.ComputerRowMapper;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.ComputerDao;

public class ComputerDaoImpl implements ComputerDao {

	private Connection conn;
	private ComputerRowMapper computerRowMapper;
	
	public ComputerDaoImpl(Connection conn){
		this.conn = conn;
		this.computerRowMapper = new ComputerRowMapper();
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

	private static final String GET_ID = "SELECT * FROM computer WHERE id=";
	
	@Override
	public Computer get(long id) {
		Computer computer;
		

		try {
			ResultSet rs = conn.createStatement().executeQuery(GET_ID+Long.toString(id));
			computer = computerRowMapper.mapRow(rs);
			return computer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private static final String GET_ALL = "Select * from computer"; 
	
	@Override
	public List<Computer> getAll() {
		List<Computer> computers;
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(GET_ALL);
			computers = computerRowMapper.mapRows(rs);
			return computers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Return empty arrayList if no result
		return new ArrayList<Computer>();
	}

}
