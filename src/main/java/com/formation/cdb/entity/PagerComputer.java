package com.formation.cdb.entity;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.service.impl.ComputerServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class PagerComputer.
 */
@SessionScope
@Component
public class PagerComputer extends Pager<Computer> {

    @Autowired
    @Qualifier("computerServiceImpl")
    private CDBService<Computer> computerService;
    
    /**
     * Constructor of PagerComputer. Dedicated Pager for Companies
     * (Page Computer).
     */
    public PagerComputer() {
        super();

    }
    
    @PostConstruct
    public void init(){
        max = computerService.sizeOfTable(filter);
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

        offset = index * pageSize;
        limit = pageSize;
        return computerService.readAllWithOffsetAndLimit(offset, limit, filter);
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#setFilter(java.lang.String)
     */
    @Override
    public void setFilter(String filter){
        max = computerService.sizeOfTable(filter);
        nbPages = (int) Math.ceil((double)max / pageSize) ;
        page = 1;
        this.filter = filter;
    }

    /* (non-Javadoc)
     * @see com.formation.cdb.entity.Pager#getMax()
     */
    @Override
    public int getMax() {
        max = computerService.sizeOfTable(filter);
        return max;
    }
}
