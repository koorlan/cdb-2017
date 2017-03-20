package com.formation.cdb.entity;

import java.util.List;


import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.service.impl.CompanyServiceImpl;


// TODO: Auto-generated Javadoc
/**
 * The Class PagerCompany.
 */
public class PagerCompany extends Pager<Company> {

    /**
     * Constructor of PagerCompany.
     * Dedicated Pager for Companies (Page Companies).
     */
    public PagerCompany() {
        super();

        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        nbPages = max / pageSize;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getPage(int)
     */
    @Override
    public List<Company> getPage(int page) {
        int index;
        int offset;
        int limit;

        index = (page > nbPages) ? nbPages : page;
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;

        offset = index * pageSize;
        limit = pageSize;
        return service.readAllWithOffsetAndLimit(offset-1, limit, filter);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#setFilter(java.lang.String)
     */
    @Override
    public void setFilter(String filter){
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        nbPages = max / pageSize;
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getMax()
     */
    @Override
    public int getMax() {
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        return max;
    }
}
