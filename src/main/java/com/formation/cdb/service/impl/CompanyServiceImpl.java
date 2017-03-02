package com.formation.cdb.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.persistence.impl.CompanyDaoImpl;
import com.formation.cdb.service.CDBService;

public enum CompanyServiceImpl implements CDBService<Company> {
    INSTANCE;
    /**
     * Private constructor for singleton implementation.
     */
    CompanyServiceImpl() {
    }

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void create(Optional<Company> e) {
        LOGGER.warn("Create method is not implemented");
    }

    @Override
    public Optional<Company> readById(long id) {
        return CompanyDaoImpl.INSTANCE.readById(id);
    }

    @Override
    public void update(Optional<Company> e) {
        LOGGER.warn("Update method is not implemented");
    }

    @Override
    public void delete(Optional<Company> e) {
        LOGGER.warn("Delete method is not implemented");
    }

    @Override
    public List<Company> readAllWithOffsetAndLimit(int offset, int limit) {
        return CompanyDaoImpl.INSTANCE.readAllWithOffsetAndLimit(offset, limit);
    }

    @Override
    public int sizeOfTable() {
        return CompanyDaoImpl.INSTANCE.rowCount();
    };

}
