package com.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    /**
     * CRUD , create an element.
     * @param e
     *            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     */
    void create(Optional<T> e);

    /**
     * CRUD , read an element.
     * @param id
     *            the id to fetch on the DataBase.
     * @return an object T id found , representation of the database object.
     */
    Optional<T> readById(long id);

    /**
     * CRUD , update an element on the database.
     * @param e
     *            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     */
    void update(Optional<T> e);

    /**
     * CRUD , Delete an element on the database.
     * @param e
     *            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     */
    void delete(Optional<T> e);

    /**
     * Construct a list of T by creating T object from the database
     * informations.
     * @param offset
     *            the number of the row to start (the first of the table = 0,
     *            as arrays).
     * @param limit
     *            how many result to have (e.g 0,10 for row 0-9).
     * @return list of type T
     */
    List<T> readAllWithOffsetAndLimit(int offset, int limit, String filter);

    /**
     * COUNT(*).
     * @return how many rows on the table.
     */
    int rowCount(String filter);
}
