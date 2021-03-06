package com.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface Dao.
 *
 * @param <T> the generic type
 */
public interface Dao<T> {
    
    /** The error dao. */
    String ERROR_DAO = "There were an error on dao layer";
    
    /**
     * CRUD , create an element.
     *
     * @param e            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     * @return the t
     */
    T create(T e);

    /**
     * CRUD , read an element.
     * @param id
     *            the id to fetch on the DataBase.
     * @return an object T id found , representation of the database object.
     */
    Optional<T> readById(long id);

    /**
     * CRUD , update an element on the database.
     *
     * @param e            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     * @return the t
     */
    T update(T e);

    /**
     * CRUD , Delete an element on the database.
     *
     * @param id the id
     */
    void delete(long id);

    /**
     * Construct a list of T by creating T object from the database
     * informations.
     *
     * @param offset            the number of the row to start (the first of the table = 0,
     *            as arrays).
     * @param limit            how many result to have (e.g 0,10 for row 0-9).
     * @param filter the filter
     * @return list of type T
     */
    List<T> readAllWithOffsetAndLimit(int offset, int limit, String filter);

    /**
     * COUNT(*).
     *
     * @param filter the filter
     * @return how many rows on the table.
     */
    int rowCount(String filter);
}
