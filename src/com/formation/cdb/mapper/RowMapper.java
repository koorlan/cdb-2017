package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface RowMapper <T>{
	
	List<T> mapRows(ResultSet rs);
	T mapRow(ResultSet rs);
	int mapCount(ResultSet rs);
}
