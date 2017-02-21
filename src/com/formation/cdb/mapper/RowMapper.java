package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.util.List;

import com.formation.cdb.exception.MapperException;

public interface RowMapper <T>{
	
	List<T> mapRows(ResultSet rs) throws MapperException;
	T mapRow(ResultSet rs) throws MapperException;
	int mapCount(ResultSet rs) throws MapperException;
}
