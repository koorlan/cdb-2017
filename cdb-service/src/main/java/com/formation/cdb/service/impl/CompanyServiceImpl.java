package com.formation.cdb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.exception.DAOException;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.service.CDBService;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyServiceImpl.
 */
@Service
public class CompanyServiceImpl implements CDBService<Company> {
    
    
    private CompanyDaoImpl companyDaoImpl;
    
    /**
     * Private constructor for singleton implementation.
     */
    public CompanyServiceImpl(CompanyDaoImpl companyDaoImpl) {
        this.companyDaoImpl = companyDaoImpl;
    }

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#create(java.util.Optional)
     */
    @Transactional
    @Override
    public Optional<Company> saveOrUpdate(Company e) {
        LOGGER.warn("Method saveOrUpdate is not implemented");
        throw new DAOException(ERROR_SERVICE, new NoSuchMethodException("Method saveOrUpdate is not implemented"));
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Transactional
    @Override
    public Optional<Company> findById(long id) {
        return companyDaoImpl.readById(id);
    }


    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Transactional
    @Override
    public void delete(long id) {
        LOGGER.warn("Method delete is not implemented");
        throw new DAOException(ERROR_SERVICE, new NoSuchMethodException("Method delete is not implemented"));
    }

    @Transactional
    @Override
    public void deleteMultiple(List<Long> ids) {
        LOGGER.warn("Method delete is not implemented");
        throw new DAOException(ERROR_SERVICE, new NoSuchMethodException("Method delete is not implemented"));
    };
    
    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Transactional
    @Override
    public List<Company> findAllWithOffsetAndLimit(int offset, int limit, String filter) {
        // TODO : use orderby 
        return companyDaoImpl.readAllWithOffsetAndLimit(offset, limit, '%' + filter +'%', "name", true);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#sizeOfTable(java.lang.String)
     */
    @Transactional
    @Override
    public int sizeOfTable(String filter) {
        return companyDaoImpl.rowCount('%'+filter+'%');
    }


    
}
