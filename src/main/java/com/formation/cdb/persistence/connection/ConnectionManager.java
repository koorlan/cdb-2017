package com.formation.cdb.persistence.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.exception.PersistenceException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionManager {
    INSTANCE;

    private ThreadLocal<Optional<Connection>> connection;
    private HikariDataSource dataSource;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String url;
    private String user;
    private String password;

    /**
     * Private constructor for the singleton implementation.
     */
    ConnectionManager() {

        connection = new ThreadLocal<Optional<Connection>>() {
            @Override
            protected Optional<Connection> initialValue() {
                return Optional.empty();
            }
        };

        String filename = "hikari.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            LOGGER.error("Sorry, unable to find " + filename);
            throw new PersistenceException("Unable to acces config file at " + filename);
        }
        try {
            prop.load(input);
            HikariConfig config = new HikariConfig(prop);
            dataSource = new HikariDataSource(config);
            dataSource.setMaximumPoolSize(1);

        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    };

    /**
     * try to retrieve a connection from the Driver.
     * 
     * @return an optional connection.. maybe yes.. maybe no :).
     */
    public Optional<Connection> getConnection() {

        if (!connection.get().isPresent()) {
            try {
                Optional<Connection> connectionO = Optional.ofNullable(dataSource.getConnection());
                if (connectionO.isPresent()) {
                    connection.set(connectionO);
                }
            } catch (SQLException e) {
                LOGGER.error("Can't get a connection", e);
            }
        }
        return connection.get();
    }

    /**
     * Try to close a given connection.
     * 
     * @param connection
     *            the connection to close.
     */
    public static void close(Optional<Connection> connection) {
        connection.ifPresent((x) -> {
            try {
                x.close();
                ConnectionManager.INSTANCE.connection.set(Optional.empty());
                //ConnectionManager.INSTANCE.LOGGER.info("Closed Connection");
            } catch (SQLException e) {
                throw new PersistenceException(e);
            }
        });
    }
}
