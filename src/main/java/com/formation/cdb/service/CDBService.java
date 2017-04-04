package com.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;


// TODO: Auto-generated Javadoc
/**
 * The Interface CDBService.
 *
 * @param <T> the generic type
 */
public interface CDBService<T> {
    /**
     * CRUD , create an element.
     * @param e
     *            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     */
    @Transactional
    void saveOrUpdate(Optional<T> e);
    /**
     * CRUD , read an element.
     * @param id
     *            the id to fetch on the DataBase.
     * @return an object T id found , representation of the database object.
     */
    @Transactional
    Optional<T> findById(long id);
    /**
     * CRUD , update an element on the database.
     * @param e
     *            a T entity representation of the new element to insert.
     *            Be carefull of the e.getId().
     */
    @Transactional
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
    @Transactional
    List<T> findAllWithOffsetAndLimit(int offset, int limit, String filter);
    
    /**
     * COUNT(*).
     *
     * @param filter the filter
     * @return how many rows on the table.
     */
    @Transactional
    int sizeOfTable(String filter);
}
