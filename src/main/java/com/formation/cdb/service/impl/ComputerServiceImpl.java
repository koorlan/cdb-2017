package com.formation.cdb.service.impl;

import java.util.List;
import java.util.Optional;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;
import com.formation.cdb.service.CDBService;

// TODO: Auto-generated Javadoc
/**
 * The Enum ComputerServiceImpl.
 */
public enum ComputerServiceImpl implements CDBService<Computer> {

    /** The instance. */
    INSTANCE;
    /**
     * Private constructor for singleton implementation.
     */
    ComputerServiceImpl() {
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#create(java.util.Optional)
     */
    @Override
    public void create(Optional<Computer> e) {
         ComputerDaoImpl.INSTANCE.create(e);
         return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readById(long)
     */
    @Override
    public Optional<Computer> readById(long id) {
        return ComputerDaoImpl.INSTANCE.readById(id);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#update(java.util.Optional)
     */
    @Override
    public void update(Optional<Computer> e) {
        ComputerDaoImpl.INSTANCE.update(e);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#delete(java.util.Optional)
     */
    @Override
    public void delete(Optional<Computer> e) {
        ComputerDaoImpl.INSTANCE.delete(e);
        return;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#readAllWithOffsetAndLimit(int, int, java.lang.String)
     */
    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit, String filter) {
        return ComputerDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit, filter+'%');
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.service.CDBService#sizeOfTable(java.lang.String)
     */
    @Override
    public int sizeOfTable(String filter) {
        return ComputerDaoImpl.INSTANCE.rowCount(filter+'%');
    }

}
