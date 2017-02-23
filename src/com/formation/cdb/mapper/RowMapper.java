package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RowMapper<T> {

	Optional<List<Optional<T>>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs);

	Optional<T> mapObjectFromOneRow(Optional<ResultSet> rs);

	public static int countRowsOfResultSet(Optional<ResultSet> rs) {

		int count = 0;

		if (!rs.isPresent()) {
			return count;
		}

		ResultSet r = rs.get();

		try {

			r.last();
			count = r.getRow();
			r.beforeFirst();

		} catch (SQLException e) {

			// TODO Auto-generated catch block

		}
		return count;
	}

	public static int mapCountResult(Optional<ResultSet> rs) {

		int count = 0;

		if (!rs.isPresent()) {
			
			return count;
		}

		ResultSet r = rs.get();

		try {
			r.next();
			count = r.getInt("c");
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block

		}
		return count;
	}
}
