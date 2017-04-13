package com.formation.cdb.service.pager;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.formation.cdb.entity.impl.Company;
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
    private CDBService<Company> companyService;
    
    /**
     * Constructor of PagerCompany. Dedicated Pager for Companies
     * (Page Company).
     */
    public PagerCompany() {
        super();

    }
    
    @PostConstruct
    public void init(){
        max = companyService.sizeOfTable(filter);
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

        index = (page -1 > nbPages) ? nbPages : page -1;

        offset = index * pageSize;
        limit = pageSize;
        return companyService.findAllWithOffsetAndLimit(offset, limit, filter);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#setFilter(java.lang.String)
     */
    @Override
    public void setFilter(String filter){
        max = companyService.sizeOfTable(filter);
        nbPages = (int) Math.ceil((double)max / pageSize) ;
        page = 1;
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getMax()
     */
    @Override
    public int getMax() {
        max = companyService.sizeOfTable(filter);
        return max;
    }
}
