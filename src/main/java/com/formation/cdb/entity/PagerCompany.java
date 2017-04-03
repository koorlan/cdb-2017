package com.formation.cdb.entity;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.service.CDBService;


// TODO: Auto-generated Javadoc
/**
 * The Class PagerCompany.
 */
@SessionScope
@Component
public class PagerCompany extends Pager<Company> {

    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> service;
    
    /**
     * Constructor of PagerCompany.
     * Dedicated Pager for Companies (Page Companies).
     */
    public PagerCompany() {
        super();        
    }

    
    @PostConstruct
    public void init(){
        max = service.sizeOfTable(filter);
        nbPages = (int) Math.ceil((double)max / pageSize);
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

        offset = index * pageSize;
        limit = pageSize;
        return service.readAllWithOffsetAndLimit(offset-1, limit, filter);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#setFilter(java.lang.String)
     */
    @Override
    public void setFilter(String filter){
        max = service.sizeOfTable(filter);
        nbPages = max / pageSize;
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getMax()
     */
    @Override
    public int getMax() {
        max = service.sizeOfTable(filter);
        return max;
    }
}
