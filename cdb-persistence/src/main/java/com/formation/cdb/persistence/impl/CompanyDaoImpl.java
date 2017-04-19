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

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.persistence.Dao;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyDaoImpl.
 */
@Repository
public class CompanyDaoImpl implements Dao<Company> {

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
    public Company create(Company company) {
      if (company == null) {
        LOGGER.error("Create failed, null company ");
        throw new DAOException(ERROR_DAO, new NullPointerException("company can't be null"));
      }
      try {
          
          sessionFactory.getCurrentSession().saveOrUpdate(company);
          LOGGER.info("Create success, company ");
          return company;
      } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException ex) {
          throw new DAOException(ERROR_DAO, ex);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.formation.cdb.persistence.Dao#readById(long)
     */
    @Override
    public Optional<Company> readById(long id) {
        if (id < 0) {
            LOGGER.error("Read by id failed, invalid id provided " + id);
            throw new DAOException(ERROR_DAO,
                    new IllegalArgumentException("Read by id failed, invalid id provided " + id));
        }
        try {
            TypedQuery<Company> query = sessionFactory.getCurrentSession().createNamedQuery("Company.findById",
                    Company.class);
            query.setParameter("id", id);
                List<Company> results = query.getResultList();

                Company foundEntity = null;
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
    public Company update(Company company) {
        LOGGER.warn("Method Update is not implemented");
        throw new DAOException(ERROR_DAO, new NoSuchMethodException("Method Update is not implemented "));
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
          Query query = sessionFactory.getCurrentSession().createNamedQuery("Company.deleteById");
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
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit, String filter, String orderBy, boolean asc) {
        if (offset < 0 || limit < 0) {
            LOGGER.error("readAllWithOffsetAndLimit, offset(" + offset + ") limit(" + limit + ") can't be negative");
            throw new DAOException(ERROR_DAO, new IllegalArgumentException("limit and offset can't be negative"));
        }

        try {
            TypedQuery<Company> query = sessionFactory.getCurrentSession().createNamedQuery("Company.findAllwithFilterByNameASC",
                    Company.class);
            query.setParameter("filter", filter);
            //query.setParameter(":asc", asc);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            List<Company> results = query.getResultList();

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
        } catch (IllegalStateException | IllegalArgumentException | DAOException | PersistenceException e) {
            throw new DAOException(ERROR_DAO, e);
        }
    };
    
    
    

}