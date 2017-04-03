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
    public void create(Optional<Computer> e) {
        computerDaoImpl.create(e);
         return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Override
    public Optional<Computer> readById(long id) {
        return computerDaoImpl.readById(id);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Computer> e) {
        computerDaoImpl.update(e);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Override
    public void delete(Optional<Computer> e) {
        computerDaoImpl.delete(e);
        return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
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
