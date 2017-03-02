package com.formation.cdb.exception;

public class MapperException extends RuntimeException {

    private static final long serialVersionUID = -7860210964614081535L;

    /**
     * Default constructor for custom exception.
     */
    public MapperException() {
        super();
        System.out.println("MapperException");
    }

    /**
     * Constructor for PersistenceException.
     * @param s
     *            message for more informations
     */
    public MapperException(String s) {
        super(s);
        System.out.println("MapperException: " + s);
    }

    /**
     * Constructor for PersistenceException.
     * @param e
     *            more info with Throwable information
     */
    public MapperException(Throwable e) {
        super(e);
        e.printStackTrace();
    }
}
