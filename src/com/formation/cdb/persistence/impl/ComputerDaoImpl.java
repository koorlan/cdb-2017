package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.mapper.impl.ComputerRowMapper;

public enum ComputerDaoImpl implements Dao<Computer> {

	INSTANCE;
	private ComputerDaoImpl(){}

	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?);" ;
	
	@Override
	public void create(Computer e) {
		// TODO Optional ?
		if(e == null ) {
			//TODO LOG
			return;
		}
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			return;
		}
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(INSERT);
			stmt.setString(1, e.getName());

			if(e.getIntroduced() != null)
				stmt.setTimestamp(2, new Timestamp(e.getIntroduced().getTime()));
			else
				stmt.setTimestamp(2, null);

			if(e.getDiscontinued() != null)
				stmt.setTimestamp(3, new Timestamp(e.getDiscontinued().getTime()));
			else
				stmt.setTimestamp(3, null);

			
			if(e.getCompany() != null)
				stmt.setLong(4, e.getCompany().getId());
			else
				stmt.setNull(4, java.sql.Types.NULL);

			
			stmt.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private static final String READ_BY_ID = "SELECT * FROM computer WHERE id=?";
	
	@Override
	public Computer readById(long id) {
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			return new Computer(0, null, null, null, null);
		}
		
		try {
			
			PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
			stmt.setInt(1, (int) id);
			Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
			Optional<Computer> optionalComputer= ComputerRowMapper.INSTANCE.mapObjectFromOneRow(rs);
			if(optionalComputer.isPresent()) {
				return optionalComputer.get();
			}
			
		} catch (SQLException e) {
			
			//TODO
			e.printStackTrace();
		}
	
		
		
		return new Computer(0, null, null, null, null);
	}
	
	private static final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?";

	@Override
	public void update(Computer e) {
		//TODO Optional ?
		
		if(e == null) {
			return;
		}
		
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			//TODO LOG
			return;
		}
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(UPDATE);

			stmt.setString(1, e.getName());

			if(e.getIntroduced() != null)
				stmt.setTimestamp(2, new Timestamp(e.getIntroduced().getTime()));
			else
				stmt.setTimestamp(2, null);

			if(e.getDiscontinued() != null)
				stmt.setTimestamp(3, new Timestamp(e.getDiscontinued().getTime()));
			else
				stmt.setTimestamp(3, null);

			if(e.getCompany() != null)
				stmt.setLong(4, e.getCompany().getId());
			else
				stmt.setTimestamp(4, null);

			stmt.setLong(5, e.getId());

			stmt.execute();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private static final String DELETE = "DELETE FROM computer WHERE id=?";
	
	@Override
	public void delete(Computer e) {
		//TODO Optional ?
		if( e == null) {
			return;
		}
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			//TODO LOG
			return;
		}
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(DELETE);
			stmt.setLong(1, e.getId());
			stmt.execute();
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	private static final String READ_ALL_LIMIT = "Select * from computer LIMIT ?,?";

	@Override
	public List<Computer> readAllWithOffsetAndLimit(int offset, int limit) {
		
		List<Computer> computers =  new ArrayList<>();
		
		if(offset < 0 || limit < 0) {
			return computers;
		}
		
		Optional<ResultSet> rs;
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(connection.isPresent()) {
			try {
				PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
				stmt.setInt(1, offset);
				stmt.setInt(2, limit);
				
				rs = Optional.ofNullable(stmt.executeQuery());
				
				Optional<List<Optional<Computer>>> optionalComputer = ComputerRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(rs);
				
				if(optionalComputer.isPresent()){
					for(Optional<Computer> c : optionalComputer.get()) {
						if(!c.isPresent()) {
							computers = null;
							computers = new ArrayList<>();
						} else {
							computers.add(c.get());
						}
					}
					
					return computers;
				}

			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return computers;
	}

	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM computer";
	
	@Override
	public int rowCount() {
		int count = 0;
		Optional<ResultSet> rs;
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

		if(connection.isPresent()){
			try {
				rs = Optional.ofNullable(connection.get().prepareStatement(ROW_COUNT).executeQuery());
				count = RowMapper.mapCountResult(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return count;
	};

	
}
