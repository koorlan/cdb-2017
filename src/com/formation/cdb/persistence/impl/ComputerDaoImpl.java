package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?);" ;
	
	@Override
	public void create(Computer c) {
		PreparedStatement st;
		
		try {
			st = conn.prepareStatement(INSERT);
			st.setString(1, c.getName());
			
			if(c.getIntroduced() != null)
				st.setTimestamp(2, new Timestamp(c.getIntroduced().getTime()));
			else
				st.setTimestamp(2, null);
			
			if(c.getDiscontinued() != null)
				st.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
			else
				st.setTimestamp(3, null);
			
			if(c.getCompany() != null)
				st.setLong(4, c.getCompany().getId());
			else
				st.setTimestamp(4, null);
			
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void update(long id, Computer e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	private static final String GET_ID = "SELECT * FROM computer WHERE id=?";
	
	@Override
	public Computer get(long id) {
		Computer computer;
		

		try {
			PreparedStatement st = conn.prepareStatement(GET_ID);
			st.setInt(1, (int) id);
			ResultSet rs = st.executeQuery();
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
