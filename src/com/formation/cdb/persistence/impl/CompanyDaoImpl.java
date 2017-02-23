package com.formation.cdb.persistence.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.exception.PersistenceException;
import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.mapper.impl.CompanyRowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.persistence.Dao;
import com.formation.cdb.persistence.connection.ConnectionManager;

public enum CompanyDaoImpl implements Dao<Company> {

	INSTANCE;
	
	private CompanyDaoImpl(){}
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public void create(Optional<Company> e) {
		LOGGER.warn("Method Create is not implemented");
	}
	
	private static final String READ_BY_ID = "SELECT * FROM company WHERE id=?";

	@Override
	public Optional<Company> readById(long id) {

		if(id <= 0){
			LOGGER.warn("id can't be negative id:"+id);
			return Optional.empty();
		}
		
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();
		
		if(!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return Optional.empty();
		}
		
			
		try {
			
			PreparedStatement stmt = connection.get().prepareStatement(READ_BY_ID);
			stmt.setLong(1, id);
			Optional<ResultSet> rs = Optional.ofNullable(stmt.executeQuery());
			Optional<Company> company = CompanyRowMapper.INSTANCE.mapObjectFromOneRow(rs);
			return company;
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(Optional<Company> e) {
		LOGGER.warn("Method update is not implemented");
	}

	@Override
	public void delete(Optional<Company> e) {
		LOGGER.warn("Method delete is not implemented");
	}

	private static final String READ_ALL_LIMIT = "SELECT * FROM company LIMIT ?,?";
	
	@Override
	public Optional<List<Optional<Company>>> readAllWithOffsetAndLimit(int offset, int limit) {

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

			Optional<List<Optional<Company>>> companies = CompanyRowMapper.INSTANCE
					.mapListOfObjectsFromMultipleRows(rs);

			return companies;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	private static final String ROW_COUNT = "SELECT COUNT(*) c FROM company";
	
	@Override
	public int rowCount() {
		int count = 0;
		Optional<ResultSet> rs;
		Optional<Connection> connection = ConnectionManager.INSTANCE.getConnection();

		if (!connection.isPresent()) {
			LOGGER.warn("can't get a connection");
			return 0;
		}

		try {
			rs = Optional.ofNullable(connection.get().prepareStatement(ROW_COUNT).executeQuery());
			count = RowMapper.mapCountResult(rs);
			return count;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	};
	
	
}
