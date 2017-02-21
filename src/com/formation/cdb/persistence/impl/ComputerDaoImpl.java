package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.exception.MapperException;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.impl.ComputerRowMapper;
import com.formation.cdb.model.impl.Computer;
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
	public void create(Computer c) throws PersistenceException {
		if(c == null)
			throw new PersistenceException("Null Computer");
		
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
			//TODO log warn can't create
		}	
	}

	private static final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?";
	

	@Override
	public void update(long id, Computer c) throws PersistenceException {
		if(c == null)
			throw new PersistenceException("Null Computer");
		try {
			PreparedStatement st = conn.prepareStatement(UPDATE);
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
			
			st.setLong(5, id);
			
			st.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String DELETE = "DELETE FROM computer WHERE id=?";
	
	@Override
	public void delete(long id) {
		try {
			PreparedStatement st = conn.prepareStatement(DELETE);
			st.setLong(1, id);
			st.execute();
		} catch (SQLException e) {
			//TODO log warn can't delete
		}
		
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
			return null;
		} catch (MapperException e) {
			return null;
		}
	}

	private static final String GET_ALL = "Select * from computer LIMIT ?,?"; 
	
	@Override
	public List<Computer> getAll(int offset, int limit) {
		List<Computer> computers;
		PreparedStatement st;
		ResultSet rs;
		
		try {
			st = conn.prepareStatement(GET_ALL);
			st.setInt(1, offset);
			st.setInt(2, limit);
			rs = st.executeQuery();
			computers = computerRowMapper.mapRows(rs);
			return computers;
		} catch (SQLException e) {
			return new ArrayList<Computer>();
		} catch (MapperException e) {
			return new ArrayList<Computer>();
		}
	}
	
	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM computer";
	
	@Override
	public int rowCount(){
		int count = 0;
		PreparedStatement st;
		ResultSet rs;
		
		try {
			st = conn.prepareStatement(ROW_COUNT);
			rs = st.executeQuery();
			count = computerRowMapper.mapCount(rs);
		} catch (SQLException e) {
		} catch (MapperException e) {
		}
		return count;
	}
	
}
