package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static ConnectionManager INSTANCE;
	
	//TODO Load this parameter from a configuration file
	private String url ="jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String login = "admincdb";
	private String password = "qwerty1234";
	
	private Connection conn;
	
	public ConnectionManager(){
		try {
			this.conn = DriverManager.getConnection(url, login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ConnectionManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ConnectionManager();
		}
		return INSTANCE;
	}
	
	/**
	 * Methods
	 */
	public Connection getConnection(){
		return this.conn;
	}
}
