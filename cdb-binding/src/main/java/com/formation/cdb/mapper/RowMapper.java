package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface RowMapper.
 *
 * @param <T> the generic type
 */
public interface RowMapper<T> {

    /**
     * Help to map database column to object.
     * @param rs a result set from the query.
     * @return Constructed objects on a list.
     */
    List<T> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs);

    /**
     * Same a multiple but for one row.
     * @param rs a result set from the query.
     * @return Constructed objects on a list.
     */
    Optional<T> mapObjectFromOneRow(Optional<ResultSet> rs);

    /**
     * Count how result are on the result set.
     * @param rs the result set.
     * @return how many rows on the result set.
     */
    static int countRowsOfResultSet(Optional<ResultSet> rs) {

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

    /**
     * Used to have the result of a COUNT(*) query.
     * @param rs the result set.
     * @return the count() SQL function result.
     */
    static int mapCountResult(Optional<ResultSet> rs) {

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
