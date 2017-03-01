package com.formation.cdb.entity;

import java.util.List;
import java.util.Optional;

public abstract class Pager<T> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    protected int pageSize;
    protected int max;
    protected int nbPages;

    protected int page;

    /**
     * Default constructor of the un-typed Pager.
     */
    public Pager() {
        pageSize = DEFAULT_PAGE_SIZE;
        max = 0;
        nbPages = max / pageSize;
        page = 0;
    }

    /**
     * Used to get a specified page of a entity book.
     * You need to implements this method for child.
     * @param page an int to represent which page you want to reach
     * @return A list of the type of the pager.
     */
    public abstract Optional<List<Optional<T>>> getPage(int page);

    /**
     * Increments the page index.
     */
    public void next() {
        if (page < nbPages) {
            page++;
        }

    }

    /**
     * Decrements the page index.
     */
    public void prev() {
        if (page > 0) {
            page--;
        }
    }
    
    public void goTo(int index){
        if (index < nbPages && index > 0) {
            page = index;
        }
    }
    
    public Optional<List<Optional<T>>> getCurrentPage() {
        return getPage(page);
    }

    public int getCurrentPageIndex() {
        return page;
    }

    public int getNbPages() {
        return nbPages;
    }
    
    public int getPageSize(){
        return pageSize;
    }
    
    public void setPageSize(int pageSize){
        page = (page * this.pageSize) / pageSize;   
        this.pageSize = pageSize;
        nbPages = max / pageSize;
    }

    public int getMax(){
        return max;
    }
}
