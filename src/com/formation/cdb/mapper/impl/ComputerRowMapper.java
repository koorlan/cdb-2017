package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import com.formation.cdb.mapper.RowMapper;
import com.formation.cdb.model.impl.Company;
import com.formation.cdb.model.impl.Computer;
import com.formation.cdb.service.impl.CompanyServiceImpl;
import com.sun.media.jfxmedia.logging.Logger;


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
				LocalDate introduced = Optional.ofNullable(r.getTimestamp("introduced")).map(Timestamp::toLocalDateTime)
						.map(LocalDateTime::toLocalDate).orElse(null);
				LocalDate discontinued = Optional.ofNullable(r.getTimestamp("discontinued"))
						.map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).orElse(null);

				long companyId = r.getLong("company_id");

				Optional<Company> company = (companyId != 0)
						? Optional.of(new Company(companyId, r.getString("c_name"))) : Optional.empty();

				Computer computer = new Computer(id, name, introduced, discontinued, company.orElse(null));

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
			LocalDate introduced =  Optional.ofNullable(r.getTimestamp("introduced")).map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).orElse(null);
			LocalDate discontinued = Optional.ofNullable(r.getTimestamp("discontinued")).map(Timestamp::toLocalDateTime).map(LocalDateTime::toLocalDate).orElse(null);

			long companyId = r.getLong("company_id");
			
			Optional<Company> company = (companyId!=0)?Optional.of(new Company(companyId,r.getString("c_name"))) : Optional.empty();
			Computer computer = new Computer(id, name, introduced, discontinued, company.orElse(null));
			
			return Optional.ofNullable(computer);

		} catch (SQLException e) {
			
			e.printStackTrace();

		}
		
		return Optional.empty();
	}

}
