package com.formation.cdb.service.impl;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.exception.ServiceException;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;
import com.formation.cdb.service.CDBService;

// TODO: Auto-generated Javadoc
/**
 * The Enum ComputerServiceImpl.
 */

@Service
public class ComputerServiceImpl implements CDBService<Computer> {

    
    Logger LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);
    
    ComputerDaoImpl computerDaoImpl;
    
    /**
     * Private constructor for singleton implementation.
     */
    public ComputerServiceImpl(ComputerDaoImpl computerDaoImpl) {
        this.computerDaoImpl = computerDaoImpl;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#create(java.util.Optional)
     */
    @Transactional
    @Override
    public Optional<Computer> saveOrUpdate(Computer c) {
        
        if ( c == null ) {
            throw new ServiceException("Can't create a null computer");
        }
        
            if ( findById(c.getId()).isPresent() ) {
                computerDaoImpl.update(c);
            } else {
                computerDaoImpl.create(c);
            }
         //TODO hibernate same object.   
         return Optional.empty();
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Transactional
    @Override
    public Optional<Computer> findById(long id) {
        return computerDaoImpl.readById(id);   
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Transactional
    @Override
    public void delete(long id) {
        computerDaoImpl.delete(id);
        return;
    }
    
    @Transactional
    @Override
    public void deleteMultiple(List<Long> ids ){
        for (long id: ids){
            delete(id);
        }
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Transactional
    @Override
    public List<Computer> findAllWithOffsetAndLimit(int offset, int limit, String filter,String orderBy, String sens ) {
        boolean asc = sens.equals("ASC");
        return computerDaoImpl.readAllWithOffsetAndLimit(offset, limit, filter+'%', orderBy, asc);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#sizeOfTable(java.lang.String)
     */
    @Transactional
    @Override
    public int sizeOfTable(String filter) {
        return computerDaoImpl.rowCount(filter+'%');
    }

}
