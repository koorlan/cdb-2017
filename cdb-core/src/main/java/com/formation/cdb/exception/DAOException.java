package com.formation.cdb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PersistenceException.
 */
public class DAOException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2262868307263845216L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DAOException.class);
    /**
     * Default constructor for custom exception.
     */
    public DAOException() {
        super();
        System.out.println("PersistenceException");
    }

    /**
     * Constructor for PersistenceException.
     * @param s
     *            message for more informations
     */
    public DAOException(String s) {
        super(s);
        System.out.println("PersistenceException: " + s);
    }

    /**
     * Constructor for PersistenceException.
     * @param e
     *            more info with Throwable information
     */
    public DAOException(Throwable e) {
        super(e);
        e.printStackTrace();
    }
    
    public DAOException(String s,Throwable e) {
        super(e);
        LOGGER.error(s + " " + e.getMessage() + " " + e.getStackTrace().toString());
    }
    
}
