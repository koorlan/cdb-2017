package com.formation.cdb.persistence;

import java.util.List;

public interface Dao<T> {
	
	void create(T e);
	T readById(long id);
	void update(T e);
	void delete(T e);

	List<T> readAllWithOffsetAndLimit(int offset,int limit);
	int rowCount();
}
