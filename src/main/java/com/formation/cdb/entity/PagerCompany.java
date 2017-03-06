package com.formation.cdb.entity;

import java.util.List;


import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.service.impl.CompanyServiceImpl;


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

    @Override
    public void setFilter(String filter){
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        nbPages = max / pageSize;
        this.filter = filter;
    }

    @Override
    public int getMax() {
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;
        max = service.sizeOfTable(filter);
        return max;
    }
}
