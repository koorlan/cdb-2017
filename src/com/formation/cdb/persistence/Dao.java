package com.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	
	void create(Optional<T> e);
	Optional<T> readById(long id);
	void update(Optional<T> e);
	void delete(Optional<T> e);

	Optional<List<Optional<T>>> readAllWithOffsetAndLimit(int offset,int limit);
	int rowCount();
}
