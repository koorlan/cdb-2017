package com.formation.cdb.persistence.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.persistence.Dao;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyDaoImpl.
 */
@Repository
public class CompanyDaoImpl implements Dao<Company> {

    @Autowired
    private SessionFactory sessionFactory;

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Private constructor for Singleton Implementation.
     */
    public CompanyDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#create(java.util.Optional)
     */
    @Override
    public void create(Optional<Company> company) {
        LOGGER.warn("Method Create is not implemented");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Company> readById(long id) {
        if (id <= 0) {
            return Optional.empty();
        }

        Query query = sessionFactory.getCurrentSession().createQuery("Select c from Company c where id = :id");
        query.setParameter("id", id);
        Company company = (Company) query.getSingleResult();
        return Optional.of(company);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Company> company) {
        LOGGER.warn("Method update is not implemented");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#delete(java.util.Optional)
     */
    @Override
    public void delete(long id) {
        LOGGER.warn("Method delete is not implemented");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#readAllWithOffsetAndLimit(int,
     * int, java.lang.String)
     */
    @Override
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        TypedQuery<Company> query = sessionFactory.getCurrentSession().createNamedQuery("Company.findAllwithFilter",
                Company.class);
        query.setParameter("filter", filter);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<Company> results = query.getResultList();

        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#rowCount(java.lang.String)
     */
    @Override
    public int rowCount(String filter) {

        TypedQuery<Long> query = sessionFactory.getCurrentSession().createNamedQuery("Company.countWithFilter",
                Long.class);
        query.setParameter("filter", filter);

        List<Long> results = query.getResultList();

        long foundCount = 0;
        if (!results.isEmpty()) {
            // ignores multiple results
            foundCount = results.get(0);
        }
        return (int) foundCount;
    };

}
