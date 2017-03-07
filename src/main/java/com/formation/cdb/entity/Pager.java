package com.formation.cdb.entity;

import java.util.List;

public abstract class Pager<T> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    protected int pageSize;
    protected int max;
    protected int nbPages;

    protected int page;
    protected String filter;
    /**
     * Default constructor of the un-typed Pager.
     */
    public Pager() {
        pageSize = DEFAULT_PAGE_SIZE;
        max = 0;
        nbPages = (int) Math.ceil((double)max / pageSize);
        page = 1;
        filter = "";
    }

    /**
     * Used to get a specified page of a entity book.
     * You need to implements this method for child.
     * @param page an int to represent which page you want to reach
     * @return A list of the type of the pager.
     */
    public abstract List<T> getPage(int page);

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
        if (page > 1) {
            page--;
        }
    }
    
    public void goTo(int index){
        if (index <= nbPages && index >= 1) {
            page = index;
        }
    }
    
    public List<T> getCurrentPage() {
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
        page = (int) Math.floor( (double) ( (page-1) * this.pageSize) / pageSize);
        page ++;
        this.pageSize = pageSize;
        nbPages = (int) Math.ceil((double) max / pageSize);
    }

    public abstract int getMax();
    
    public abstract void setFilter(String filter);
    
    public String getFilter() {
        return filter;
    }
}
