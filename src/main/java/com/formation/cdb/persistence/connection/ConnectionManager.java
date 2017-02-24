package com.formation.cdb.persistence.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.exception.PersistenceException;

public enum ConnectionManager {
    INSTANCE;

    private Optional<Connection> connection = Optional.empty();

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Private constructor for the singleton implementation.
     */
    ConnectionManager() {
    };

    // TODO Load this parameter from a configuration file
    private String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    private String login = "admincdb";
    private String password = "qwerty1234";

    /**
     * try to retrieve a connection from the Driver.
     * @return an optional connection.. maybe yes.. maybe no :).
     */
    public Optional<Connection> getConnection() {
        if (!connection.isPresent()) {
            try {
                connection = Optional.ofNullable(DriverManager.getConnection(url, login, password));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
            }
        }
        return connection;
    }

    /**
     * Try to close a given connection.
     * @param connection
     *            the connection to close.
     */
    public static void close(Optional<Connection> connection) {
        connection.ifPresent((x) -> {
            try {
                x.close();
                ConnectionManager.INSTANCE.connection = Optional.empty();
                ConnectionManager.INSTANCE.LOGGER.info("Closed Connection");
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        });
    }
}
