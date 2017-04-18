package com.formation.cdb.service.pager;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

public class Pager {

    /** The Constant DEFAULT_PAGE_SIZE. */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /** The page size. */
    protected int pageSize;

    /** The max. */
    protected int max;

    /** The nb pages. */
    protected int nbPages;

    /** The page. */
    protected int page;

    /** The filter. */
    protected String filter;
    /**
     * Default constructor of the un-typed Pager.
     */
    public Pager(Optional<String> filter, Optional<Integer> size, Optional<Integer> page, int numberOfItems) {
        pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        max = numberOfItems;
        nbPages = (int) Math.ceil((double) max / pageSize);
        this.page = page.orElse(1);
        this.filter = filter.orElse("");
    }

    /**
     * Used to get a specified page of a entity book.
     * You need to implements this method for child.
     * @param page an int to represent which page you want to reach
     * @return A list of the type of the pager.
     */
    public int getOffset(){
        int index;
        int offset;

        index = (page -1 > nbPages) ? nbPages : page -1;

        offset = index * pageSize;
        return offset;
    }

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

    /**
     * Go to.
     *
     * @param index the index
     */
    public void goTo(int index) {
        if (index <= nbPages && index >= 1) {
            page = index;
        }
    }

    /**
     * Gets the current page index.
     *
     * @return the current page index
     */
    public int getCurrentPageIndex() {
        return page;
    }

    /**
     * Gets the nb pages.
     *
     * @return the nb pages
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * Gets the page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize the new page size
     */
    public void setPageSize(int pageSize) {
        page = (int) Math.floor((double) ((page - 1) * this.pageSize) / pageSize);
        page++;
        this.pageSize = pageSize;
        nbPages = (int) Math.ceil((double) max / pageSize);
    }

    /**
     * Gets the max.
     *
     * @return the max
     */
    public void setMax(int max){
        this.max = max;
        nbPages = (int) Math.ceil((double)max / pageSize);
    }

    public int getMax(){
        return this.max;
    }
    /**
     * Sets the filter.
     *
     * @param filter the new filter
     */
    public void setFilter(String filter){
        this.filter = filter;
    }

    /**
     * Gets the filter.
     *
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }
}
