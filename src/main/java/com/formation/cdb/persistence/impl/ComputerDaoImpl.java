package com.formation.cdb.persistence.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.PersistenceException;
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
    public void create(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.error("Create failed, null computer " + e);
            throw new PersistenceException("Create failed, null computer " + e);
        }

        Computer c = e.get();
        sessionFactory.getCurrentSession().saveOrUpdate(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Computer> readById(long id) {

        if ( id <= 0) {
            LOGGER.error("Read by id failed, invalid id provided " + id);
            throw new PersistenceException("Read by id failed, invalid id provided " + id );
        }
        
        TypedQuery<Computer> query = sessionFactory.getCurrentSession().createNamedQuery("Computer.findById",
                Computer.class);
        query.setParameter("id", id);

        List<Computer> results = query.getResultList();

        Computer foundEntity = null;
        if (!results.isEmpty()) {
            // ignores multiple results
            foundEntity = results.get(0);
        }
        return Optional.ofNullable(foundEntity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Computer> e) {

        if (!e.isPresent()) {
            LOGGER.warn("Update failed, null computer");
            return;
        }

        Computer c = e.get();
        Query query = sessionFactory.getCurrentSession().createNamedQuery("Computer.update");

        query.setParameter("id", c.getId());
        query.setParameter("name", c.getName().orElse(""));
        query.setParameter("introduced", c.getIntroduced().orElse(null));
        query.setParameter("discontinued", c.getDiscontinued().orElse(null));
        query.setParameter("company", c.getCompany().orElse(null));

        int rowsAffected = query.executeUpdate();
        if (!(rowsAffected > 0)) {
            LOGGER.info("No rows was affected by the update : " + c);
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#delete(java.util.Optional)
     */
    @Override
    public void delete(long id) {

        if (id < 0) {
            LOGGER.warn("Create failed, null computer");
            return;
        }
        Query query = sessionFactory.getCurrentSession().createNamedQuery("Computer.deleteById");
        query.setParameter("id", id);

        int rowsAffected = query.executeUpdate();
        if (!(rowsAffected > 0)) {
            LOGGER.info("No rows was affected by the delete : " + id);
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
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter) {

        TypedQuery<Computer> query = sessionFactory.getCurrentSession().createNamedQuery("Computer.findAllwithFilter",
                Computer.class);
        query.setParameter("filter", filter);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<Computer> results = query.getResultList();

        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#rowCount(java.lang.String)
     */
    @Override
    public int rowCount(String filter) {

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
    }

}
