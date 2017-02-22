package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface RowMapper <T>{
	
	Optional<List<T>> 	mapListOfObjectsFromMultipleRows	(Optional<ResultSet> rs);
	Optional<T> 		mapObjectFromOneRow					(Optional<ResultSet> rs);
	int 				countRowsOfResultSet				(Optional<ResultSet> rs);
}
