package com.formation.cdb.persistence;

import java.util.List;

public interface Dao<T> {
	
	void create(T e);
	void update(long id, T e);
	void delete(long id);
	T get(long id);
	List<T> getAll(int offset,int limit);
	int rowCount();
}
