package com.formation.cdb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.service.CDBService;

// TODO: Auto-generated Javadoc
/**
 * The Enum CompanyServiceImpl.
 */
@Named
public class CompanyServiceImpl implements CDBService<Company> {
    

    
    @Inject
    private CompanyDaoImpl dao;
    
    /**
     * Private constructor for singleton implementation.
     */
    public CompanyServiceImpl() {
    }

    /** The logger. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#create(java.util.Optional)
     */
    @Override
    public void create(Optional<Company> e) {
        LOGGER.warn("Create method is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Override
    public Optional<Company> readById(long id) {
        return dao.readById(id);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Company> e) {
        LOGGER.warn("Update method is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Override
    public void delete(Optional<Company> e) {
        LOGGER.warn("Delete method is not implemented");
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        return dao.readAllWithOffsetAndLimit(offset, limit, '%' + filter +'%');
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#sizeOfTable(java.lang.String)
     */
    @Override
    public int sizeOfTable(String filter) {
        return dao.rowCount('%'+filter+'%');
    };
    
}
