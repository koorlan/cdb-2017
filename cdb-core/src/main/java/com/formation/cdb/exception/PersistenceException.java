package com.formation.cdb.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class PersistenceException.
 */
public class PersistenceException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2262868307263845216L;

    /**
     * Default constructor for custom exception.
     */
    public PersistenceException() {
        super();
        System.out.println("PersistenceException");
    }

    /**
     * Constructor for PersistenceException.
     * @param s
     *            message for more informations
     */
    public PersistenceException(String s) {
        super(s);
        System.out.println("PersistenceException: " + s);
    }

    /**
     * Constructor for PersistenceException.
     * @param e
     *            more info with Throwable information
     */
    public PersistenceException(Throwable e) {
        super(e);
        e.printStackTrace();
    }
}
