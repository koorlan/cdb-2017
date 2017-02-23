package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.service.impl.CompanyServiceImpl;


public enum ComputerRowMapper implements RowMapper<Computer> {

	INSTANCE;
	
	private ComputerRowMapper(){};
	
	@Override
	public Optional<List<Optional<Computer>>> mapListOfObjectsFromMultipleRows(Optional<ResultSet> rs) {
		if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}

		try {

			ResultSet r = rs.get();
			List<Optional<Computer>> computers = new ArrayList<>();
			while (r.next()) {
				
				long id = r.getLong("id");

				String name = r.getString("name");
				Date introduced = r.getTimestamp("introduced");
				Date discontinued = r.getTimestamp("discontinued");

				// Try to retrieve a company from the database with this id
				//TODO Join AND REfactor method call mapObjectFromOneRow ?
				long companyId = r.getLong("company_id");
				CompanyServiceImpl cdbService = CompanyServiceImpl.INSTANCE;
				Company company = cdbService.readById(companyId);
				
				Computer computer = new Computer(id, name, introduced, discontinued, company);

				computers.add(Optional.ofNullable(computer));
			}

			return Optional.ofNullable(computers);
		} catch (SQLException e) {

			e.printStackTrace();

		}

		return Optional.empty();
	}

	@Override
	public Optional<Computer> mapObjectFromOneRow(Optional<ResultSet> rs) {
		
		if (!rs.isPresent() || RowMapper.countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}

		try {
			
			ResultSet r = rs.get();
			
			r.next();
			
			long id = r.getLong("id");
			String name = r.getString("name");
			Date introduced = r.getTimestamp("introduced");
			Date discontinued = r.getTimestamp("discontinued");
			long companyId = r.getLong("company_id");
			
			// TODO JOIN
			CompanyServiceImpl cdbService = CompanyServiceImpl.INSTANCE;
			Company company = cdbService.readById(companyId);
			Computer computer = new Computer(id, name, introduced, discontinued, company);
			
			return Optional.ofNullable(computer);

		} catch (SQLException e) {
			
			// TODO 

		}
		
		return Optional.empty();
	}

}
