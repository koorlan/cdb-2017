package com.formation.cdb.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;

public enum ConnectionManager {
	INSTANCE;
	
	private Optional<Connection> connection;
	
	private ConnectionManager(){};
	
	//TODO Load this parameter from a configuration file
	private String url ="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String login = "admincdb";
	private String password = "qwerty1234";
	
	public Optional<Connection> getConnection(){
		if(!connection.isPresent()){
			connection = Optional.ofNullable(DriverManager.getConnection(url, login, password));
		}
		return connection;
	}
}
