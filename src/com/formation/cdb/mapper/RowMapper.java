package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RowMapper<T> {

	Optional<List<T>>	mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs)	;

	Optional<T> 		mapObjectFromOneRow(Optional<ResultSet> rs)					;

	public static int countRowsOfResultSet(Optional<ResultSet> rs){
		
		int	count	=	0	;
	
		if(!rs.isPresent()) {
			return count;
		}
		
		ResultSet r = rs.get();
		
		try {
		
			r.last()			;
			count = r.getRow()	;
			r.first()			;
		
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			
		}
		return count;
	}
}
