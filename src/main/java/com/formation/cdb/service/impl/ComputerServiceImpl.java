package com.formation.cdb.service.impl;

import java.util.List;
import java.util.Optional;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.persistence.impl.ComputerDaoImpl;
import com.formation.cdb.service.CDBService;

public enum ComputerServiceImpl implements CDBService<Computer> {

    INSTANCE;
    /**
     * Private constructor for singleton implementation.
     */
    ComputerServiceImpl() {
    }

    @Override
    public void create(Optional<Computer> e) {
         ComputerDaoImpl.INSTANCE.create(e);
         return;
    }

    @Override
    public Optional<Computer> readById(long id) {
        return ComputerDaoImpl.INSTANCE.readById(id);
    }

    @Override
    public void update(Optional<Computer> e) {
        ComputerDaoImpl.INSTANCE.update(e);
    }

    @Override
    public void delete(Optional<Computer> e) {
        ComputerDaoImpl.INSTANCE.delete(e);
        return;
    }

    @Override
    public List<Computer> readAllWithOffsetAndLimit(int offset, int limit) {
        return ComputerDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit);
    }

    @Override
    public int sizeOfTable() {
        return ComputerDaoImpl.INSTANCE.rowCount();
    }

}
