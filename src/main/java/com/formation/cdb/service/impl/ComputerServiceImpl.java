package com.formation.cdb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;
import com.formation.cdb.service.CDBService;

// TODO: Auto-generated Javadoc
/**
 * The Enum ComputerServiceImpl.
 */

@Service
public class ComputerServiceImpl implements CDBService<Computer> {

    @Autowired
    private ComputerDaoImpl computerDaoImpl;
    
    /**
     * Private constructor for singleton implementation.
     */
    public ComputerServiceImpl() {
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#create(java.util.Optional)
     */
    @Override
    public void saveOrUpdate(Optional<Computer> c) {
        
        if ( c.isPresent() ) {
            if ( findById(c.get().getId()).isPresent() ) {
                computerDaoImpl.update(c);
            } else {
                computerDaoImpl.create(c);
            }
            
        }
         return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Override
    public Optional<Computer> findById(long id) {
        return computerDaoImpl.readById(id);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Override
    public void delete(long id) {
        computerDaoImpl.delete(id);
        return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Computer> findAllWithOffsetAndLimit(int offset, int limit, String filter) {
        return computerDaoImpl.readAllWithOffsetAndLimit(offset, limit, filter+'%');
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#sizeOfTable(java.lang.String)
     */
    @Override
    public int sizeOfTable(String filter) {
        return computerDaoImpl.rowCount(filter+'%');
    }

}
