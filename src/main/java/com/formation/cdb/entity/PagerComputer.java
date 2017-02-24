package com.formation.cdb.entity;

import java.util.List;
import java.util.Optional;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.service.impl.ComputerServiceImpl;

public class PagerComputer extends Pager<Computer> {

    /**
     * Constructor of PagerComputer. Dedicated Pager for Companies
     * (Page<Computer>).
     */
    public PagerComputer() {
        super();

        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;
        max = service.sizeOfTable();
        nbPages = max / pageSize;
    }

    @Override
    public Optional<List<Optional<Computer>>> getPage(int page) {
        int index;
        int offset;
        int limit;

        index = (page > nbPages) ? nbPages : page;
        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;

        offset = (index * pageSize);
        limit = pageSize;
        return service.readAllWithOffsetAndLimit(offset, limit);
    }

}
