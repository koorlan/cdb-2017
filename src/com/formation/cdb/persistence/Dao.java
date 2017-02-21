package com.formation.cdb.persistence;

import java.util.List;

import com.formation.cdb.exception.PersistenceException;

public interface Dao<T> {
	
	void create(T e) throws PersistenceException;
	void update(long id, T e) throws PersistenceException;
	void delete(long id);
	T get(long id);
	List<T> getAll(int offset,int limit) throws PersistenceException;
	int rowCount();
}
