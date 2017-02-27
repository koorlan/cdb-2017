package com.formation.cdb.entity;

import java.util.List;
import java.util.Optional;

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
        max = service.sizeOfTable();
        nbPages = max / pageSize;
    }

    @Override
    public Optional<List<Optional<Company>>> getPage(int page) {
        int index;
        int offset;
        int limit;

        index = (page > nbPages) ? nbPages : page;
        CompanyServiceImpl service;
        service = CompanyServiceImpl.INSTANCE;

        offset = (index * pageSize);
        limit = pageSize;
        return service.readAllWithOffsetAndLimit(offset, limit);
    }

}
