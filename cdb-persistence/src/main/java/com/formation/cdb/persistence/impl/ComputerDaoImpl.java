package com.formation.cdb.persistence.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.persistence.Dao;

// TODO: Auto-generated Javadoc
/**
 * The Enum ComputerDaoImpl.
 */
@Repository
public class ComputerDaoImpl implements Dao<Computer> {

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final SessionFactory sessionFactory;

    public ComputerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#create(java.util.Optional)
     */
    @Override
    public Computer create(Computer e) {

        if (e == null) {
            LOGGER.error("Create failed, null computer ");
            throw new DAOException(ERROR_DAO, new NullPointerException("computer can't be null"));
        }
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(e);
            return e;
        } catch (IllegalStateException | IllegalArgumentException | PersistenceException ex) {
            throw new DAOException(ERROR_DAO, ex);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Computer> readById(long id) {

        if (id < 0) {
            LOGGER.error("Read by id failed, invalid id provided " + id);
            throw new DAOException(ERROR_DAO,
                    new IllegalArgumentException("Read by id failed, invalid id provided " + id));
        }

        TypedQuery<Computer> query = sessionFactory.getCurrentSession().createNamedQuery("Computer.findById",
                Computer.class);
        query.setParameter("id", id);
        try {
            List<Computer> results = query.getResultList();

            Computer foundEntity = null;
            if (!results.isEmpty()) {
                // ignores multiple results
                foundEntity = results.get(0);
            }
            return Optional.ofNullable(foundEntity);
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException e) {
            throw new DAOException(ERROR_DAO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#update(java.util.Optional)
     */
    @Override
    public Computer update(Computer e) {


        if (e == null) {
            LOGGER.error("Create failed, null computer ");
            throw new DAOException(ERROR_DAO, new NullPointerException("computer can't be null"));
        }
        try {
            Query query = sessionFactory.getCurrentSession().createNamedQuery("Computer.update");

            query.setParameter("id", e.getId());
            query.setParameter("name", e.getName().orElse(""));
            query.setParameter("introduced", e.getIntroduced().orElse(null));
            query.setParameter("discontinued", e.getDiscontinued().orElse(null));
            query.setParameter("company", e.getCompany().orElse(null));

            int rowsAffected = query.executeUpdate();
            if (!(rowsAffected > 0)) {
                LOGGER.info("No rows was affected by the update : " + e);
            }
            return e;
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException ex) {
            throw new DAOException(ERROR_DAO, ex);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#delete(java.util.Optional)
     */
    @Override
    public void delete(long id) {

        if (id < 0) {
            LOGGER.warn("Delete failed id: " + id);
            throw new DAOException(ERROR_DAO, new IllegalArgumentException("id must be grater or equals than zero"));
        }
        try {
            Query query = sessionFactory.getCurrentSession().createNamedQuery("Computer.deleteById");
            query.setParameter("id", id);
            int rowsAffected = query.executeUpdate();
            if (!(rowsAffected > 0)) {
                LOGGER.info("No rows was affected by the delete : " + id);
            }
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException e) {
            throw new DAOException(ERROR_DAO, e);
        }
        return;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#readAllWithOffsetAndLimit(int,
     * int, java.lang.String)
     */
    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter, String orderBy, boolean asc) {

        if (offset < 0 || limit < 0) {
            LOGGER.error("readAllWithOffsetAndLimit, offset(" + offset + ") limit(" + limit + ") can't be negative");
            throw new DAOException(ERROR_DAO, new IllegalArgumentException("limit and offset can't be negative"));
        }

        
        String queryStr = mapperOrderByQuery(orderBy, asc);
        try {
            TypedQuery<Computer> query = sessionFactory.getCurrentSession()
                    .createNamedQuery(queryStr, Computer.class);
            query.setParameter("filter", filter);
            //query.setParameter(":sens", asc);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            LOGGER.debug(query.unwrap(Query.class).getQueryString());

            List<Computer> results = query.getResultList();
            return results;
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException e) {
            throw new DAOException(ERROR_DAO, e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.formation.cdb.persistence.Dao#rowCount(java.lang.String)
     */
    @Override
    public int rowCount(String filter) {
        try {
            TypedQuery<Long> query = sessionFactory.getCurrentSession().createNamedQuery("Computer.countWithFilter",
                    Long.class);
            query.setParameter("filter", filter);

            List<Long> results = query.getResultList();

            long foundCount = 0;
            if (!results.isEmpty()) {
                // ignores multiple results
                foundCount = results.get(0);
            }
            return (int) foundCount;
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException e) {
            throw new DAOException(ERROR_DAO, e);
        }
    }
    
    /**
     * Return the NamedQuery with the orderBy given default is Ordered by name
     * @param orderBy .
     * @param asc true for ascending and false for descending
     * @return
     */
    private String mapperOrderByQuery(String orderBy, boolean asc) {
        switch (orderBy) {
        case "name":
            return (asc) ? "Computer.findAllwithFilterOrderByNameASC" : "Computer.findAllwithFilterOrderByNameDESC";
        case "introduced":
            return (asc) ? "Computer.findAllwithFilterOrderByDateIntroASC" : "Computer.findAllwithFilterOrderByDateIntroDESC";
        case "discontinued":
            return (asc) ? "Computer.findAllwithFilterOrderByDateDiscontinuedASC" : "Computer.findAllwithFilterOrderByDateDiscontinuedDESC";
        case "company":
            return (asc) ? "Computer.findAllwithFilterOrderByCompanyNameASC" : "Computer.findAllwithFilterOrderByCompanyNameDESC";
        default:
            return "Computer.findAllwithFilterOrderByNameASC";
        }
    }

}
