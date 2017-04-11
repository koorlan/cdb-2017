package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.formation.cdb.entity.impl.Company;

public class CompanyMapper implements RowMapper<Company>{

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Company.CompanyBuilder(rs.getLong("id"), rs.getString("name")).build();
    }

}
