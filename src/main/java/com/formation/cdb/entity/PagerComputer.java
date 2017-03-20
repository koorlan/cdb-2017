package com.formation.cdb.entity;

import java.util.List;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.service.impl.ComputerServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class PagerComputer.
 */
public class PagerComputer extends Pager<Computer> {

    /**
     * Constructor of PagerComputer. Dedicated Pager for Companies
     * (Page Computer).
     */
    public PagerComputer() {
        super();
        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        nbPages = (int) Math.ceil((double)max / pageSize);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getPage(int)
     */
    @Override
    public List<Computer> getPage(int page) {
        int index;
        int offset;
        int limit;

        index = (page -1 > nbPages) ? nbPages : page -1;
        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;

        offset = index * pageSize;
        limit = pageSize;
        return service.readAllWithOffsetAndLimit(offset, limit, filter);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#setFilter(java.lang.String)
     */
    @Override
    public void setFilter(String filter){
        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        nbPages = (int) Math.ceil((double)max / pageSize) ;
        page = 1;
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getMax()
     */
    @Override
    public int getMax() {
        ComputerServiceImpl service;
        service = ComputerServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        return max;
    }
}
