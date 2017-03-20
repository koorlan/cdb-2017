package com.formation.cdb.entity;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Pager.
 *
 * @param <T> the generic type
 */
public abstract class Pager<T> {

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
    public Pager() {
        pageSize = DEFAULT_PAGE_SIZE;
        max = 0;
        nbPages = (int) Math.ceil((double) max / pageSize);
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
     * Gets the current page.
     *
     * @return the current page
     */
    public List<T> getCurrentPage() {
        return getPage(page);
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
        page = (int) Math.floor( (double) ((page - 1) * this.pageSize) / pageSize);
        page++;
        this.pageSize = pageSize;
        nbPages = (int) Math.ceil((double) max / pageSize);
    }

    /**
     * Gets the max.
     *
     * @return the max
     */
    public abstract int getMax();

    /**
     * Sets the filter.
     *
     * @param filter the new filter
     */
    public abstract void setFilter(String filter);

    /**
     * Gets the filter.
     *
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }
}
