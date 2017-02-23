package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;

public enum CompanyRowMapper implements RowMapper<Company> {

	INSTANCE;

	private CompanyRowMapper() {
	};

	@Override
	public Optional<List<Optional<Company>>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {

		if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}

		try {

			List<Optional<Company>> companies;
			long id;
			String name;
			Company company;

			companies = new ArrayList<>();

			while (rs.get().next()) {
				
				id = rs.get().getLong("id");
				
				name = rs.get().getString("name");
				company = new Company(id, name);

				companies.add(Optional.ofNullable(company));
			}

			return Optional.ofNullable(companies);

		} catch (SQLException e) {

			// TODO

		}
		return Optional.empty();
	}

	@Override
	public Optional<Company> mapObjectFromOneRow(Optional<ResultSet> rs) {

		if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}

		try {
			
			ResultSet r = rs.get();
			r.next();
			long id = r.getLong("id");
			String name = r.getString("name");
			Company company = new Company(id, name);

			return Optional.ofNullable(company);

		} catch (SQLException e) {

		}
		return Optional.empty();
	}

}
