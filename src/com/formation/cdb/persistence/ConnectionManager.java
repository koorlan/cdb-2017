package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.exception.PersistenceException;

public class ConnectionManager {
	private static ConnectionManager INSTANCE;
	
	//TODO Load this parameter from a configuration file
	private String url ="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String login = "admincdb";
	private String password = "qwerty1234";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Connection conn;
	
	public ConnectionManager() throws PersistenceException{
		try {
			this.conn = DriverManager.getConnection(url, login, password);
			logger.info("Connection created");
		} catch (SQLException e) {
			throw new PersistenceException("Can't get a connection to database");
		}
	}
	
	public static ConnectionManager getInstance() throws PersistenceException{
		if(INSTANCE == null){
			INSTANCE = new ConnectionManager();
		}
		return INSTANCE;
	}
	
	/**
	 * Methods
	 */
	public Connection getConnection(){
		if(this.conn == null ) logger.warn("Connection object is null");
		return this.conn;
	}
}
