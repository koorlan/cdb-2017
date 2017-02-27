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

public enum ConnectionManager {
    INSTANCE;

    private Optional<Connection> connection = Optional.empty();

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String url;
    private String user;
    private String password;

    /**
     * Private constructor for the singleton implementation.
     */
    ConnectionManager() {
        String filename = "config.properties";
        Properties prop = new Properties();
        InputStream input = null;
        input = ConnectionManager.class.getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            LOGGER.error("Sorry, unable to find " + filename);
        return;
        }
        try {
            prop.load(input);
            StringBuilder sb = new StringBuilder();
            sb.append("jdbc:mysql://");
            sb.append(prop.getProperty("db_addr"));
            sb.append(':');
            sb.append(prop.getProperty("db_port"));
            sb.append('/');
            sb.append(prop.getProperty("db_name"));
            sb.append("?zeroDateTimeBehavior=convertToNull");

            url = sb.toString();
            user = prop.getProperty("db_user");
            password = prop.getProperty("db_password");

        } catch (IOException e) {
            LOGGER.error("Error on config file");
            throw new PersistenceException(e);
        }
    };

    /**
     * try to retrieve a connection from the Driver.
     * @return an optional connection.. maybe yes.. maybe no :).
     */
    public Optional<Connection> getConnection() {
        if (!connection.isPresent()) {
            try {
                connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
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
