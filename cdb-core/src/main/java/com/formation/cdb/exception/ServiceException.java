package com.formation.cdb.exception;

public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1499048516108095645L;
    
    
    /**
     * Default constructor for custom exception.
     */
    public ServiceException() {
        super();
        System.out.println("PersistenceException");
    }

    /**
     * Constructor for PersistenceException.
     * @param s
     *            message for more informations
     */
    public ServiceException(String s) {
        super(s);
        System.out.println("PersistenceException: " + s);
    }

    /**
     * Constructor for PersistenceException.
     * @param e
     *            more info with Throwable information
     */
    public ServiceException(Throwable e) {
        super(e);
        e.printStackTrace();
    }
}
