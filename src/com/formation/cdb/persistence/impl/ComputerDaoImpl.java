package com.formation.cdb.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;
import com.formation.cdb.util.DateUtil;
import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.ComputerRowMapper;

public enum ComputerDaoImpl implements Dao<Computer> {

	INSTANCE;
	private ComputerDaoImpl(){}

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) values (?,?,?,?);" ;
	
	@Override
	public void create(Optional<Computer> e) {
		
		if(!e.isPresent()) {
			LOGGER.warn("Create failed, null computer");
			return;
		}

		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return;
		}
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(INSERT);
			stmt.setString(1, e.flatMap(Computer::getName).orElseThrow(() -> new PersistenceException("Trying to bypass validation, name is required")));
			
			stmt.setTimestamp(2, e.flatMap(Computer::getIntroduced).map(DateUtil::dateToTimestamp).orElse(null) );
			
			stmt.setTimestamp(3, e.flatMap(Computer::getDiscontinued).map(DateUtil::dateToTimestamp).orElse(null) );
			
			stmt.setLong(4, e.flatMap(Computer::getCompany).map(Company::getId).orElse(Long.valueOf(Types.NULL)));
			
			stmt.execute();
			LOGGER.info("Query sucessfully executed " + stmt.toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private static final String READ_BY_ID = "SELECT computer.id AS id,computer.name AS name,introduced,discontinued,company_id, company.name AS c_name from computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
	
	@Override
	public Optional<Computer> readById(long id) {
		
		if(id <= 0) {
			LOGGER.warn("Id can't be negative or equal 0");
			return Optional.empty();
		}
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return Optional.empty();
		}
		
		try {
		
			PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
			stmt.setInt(1, (int) id);
			Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
			Optional<Computer> computer = ComputerRowMapper.INSTANCE.mapObjectFromOneRow(rs);
			LOGGER.info("Sucessfully readed: " + computer.toString());
			return computer;
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	
	private static final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=?";

	@Override
	public void update(Optional<Computer> e) {
		
		if(!e.isPresent()) {
			LOGGER.warn("Create failed, null computer");
			return;
		}

		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return;
		}
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(UPDATE);
			
			stmt.setString(1, e.flatMap(Computer::getName).orElseThrow(() -> new PersistenceException("Trying to bypass validation, name is required")));
			
			stmt.setTimestamp(2, e.flatMap(Computer::getIntroduced).map(DateUtil::dateToTimestamp).orElse(null) );
			
			stmt.setTimestamp(3, e.flatMap(Computer::getDiscontinued).map(DateUtil::dateToTimestamp).orElse(null) );

			stmt.setLong(4, e.flatMap(Computer::getCompany).map(Company::getId).orElse(Long.valueOf(Types.NULL)));
			
			stmt.setLong(5, e.map(Computer::getId).orElseThrow(() -> new PersistenceException("Trying to bypass validation, id is required")));
			
			stmt.execute();
			LOGGER.info("Sucessfully updated: " + e);
		} catch (SQLException e1) {
			throw new PersistenceException(e1);
		}
		
	}

	private static final String DELETE = "DELETE FROM computer WHERE id=?";
	
	@Override
	public void delete(Optional<Computer> e) {
		
		if(!e.isPresent()) {
			LOGGER.warn("Create failed, null computer");
			return;
		}

		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return;
		}
		
		
		try {
			PreparedStatement stmt = connection.get().prepareStatement(DELETE);
			stmt.setLong(1, e.map(Computer::getId).orElseThrow(() -> new PersistenceException("id is less or equal zero")) );
			stmt.execute();
			
			LOGGER.info("Succesfully deleted " + e);
		} catch (SQLException e1) {
			throw new PersistenceException(e1);
		}
		
		
	}
	
	//private static final String READ_ALL_LIMIT = "Select * from computer LIMIT ?,?";
	private static final String READ_ALL_LIMIT = "SELECT computer.id AS id,computer.name AS name,introduced,discontinued,company_id, company.name AS c_name from computer LEFT JOIN company ON computer.company_id=company.id LIMIT ?,?";
	@Override
	public Optional<List<Optional<Computer>>> readAllWithOffsetAndLimit(int offset, int limit) {

		if (offset < 0 || limit < 0) {
			LOGGER.warn("Offset and limit must be positive. Offset:" + offset + " Limit:" + limit);
			return Optional.empty();
		}

		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

		if (!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return Optional.empty();
		}
		try {
			PreparedStatement stmt = connection.get().prepareStatement(READ_ALL_LIMIT);
			stmt.setInt(1, offset);
			stmt.setInt(2, limit);

			Optional<ResultSet> rs;
			rs = Optional.ofNullable(stmt.executeQuery());

			Optional<List<Optional<Computer>>> computers;
			computers = ComputerRowMapper.INSTANCE.mapListOfObjectsFromMultipleRows(rs);

			return computers;

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			ConnectionManager.close(connection);
		}

	}

	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM computer";
	
	@Override
	public int rowCount() {
		int count = 0;

		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

		if (!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return 0;
		}

		try {
			Optional<ResultSet> rs;
			rs = Optional.ofNullable(connection.get().prepareStatement(ROW_COUNT).executeQuery());
			count = RowMapper.mapCountResult(rs);
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}

	}

	
}
