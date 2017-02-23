package com.formation.cdb.ui;

import java.util.List;
import java.util.Optional;

public abstract class Pager<T> {

	public static final int DEFAULT_PAGE_SIZE = 10;

	protected int pageSize;	
	protected int max;
	protected int nbPages;
	
	protected int page;
	
	/**
	 * Constructor
	 */

	public Pager() {
		pageSize = DEFAULT_PAGE_SIZE;
		max = 0;
		nbPages = max/pageSize;
		page = 0;
	}
	
	public abstract Optional<List<Optional<T>>> getPage(int page);
	
	public void next(){
		if(page < nbPages)
			page ++;
	}
	
	public void prev(){
		if(page > 0)
			page --;
	}
	
	public Optional<List<Optional<T>>> getCurrentPage(){
		return getPage(page);
	}
	
	public int getCurrentPageIndex(){
		return page;
	}
	
	public int getNbPages(){
		return nbPages;
	}
}
