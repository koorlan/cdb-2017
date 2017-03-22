package com.formation.cdb.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.entity.impl.Computer;

public class ComputerMapper implements RowMapper<Computer>{

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate introduced = Optional.ofNullable(rs.getTimestamp("introduced")).map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate).orElse(null);
        LocalDate discontinued = Optional.ofNullable(rs.getTimestamp("discontinued")).map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate).orElse(null);

        long companyId = rs.getLong("company_id");
        String companyName = rs.getString("company_name");
        
        Optional<Computer> computer = constructComputerFromResultSetvalues(id, name, introduced, discontinued, companyId, companyName);
        return computer.get();
    }
    
    private Optional<Computer> constructComputerFromResultSetvalues(long id, String name, LocalDate introduced, LocalDate discontinued, long companyId, String companyName){
        if ( id == 0 || name == null || StringUtils.isBlank(name) ) {
            return Optional.empty();
        }
        
        Optional<Company> company;
        
        if ( companyId == 0 || companyName == null || StringUtils.isBlank(companyName)) {
            company = Optional.empty();
        } else {
            company = Optional.of(new Company.CompanyBuilder(companyId, companyName).build());
        }
        
        Computer.ComputerBuilder computerBuilder = new Computer.ComputerBuilder(id, name);
        if ( introduced != null ) {
            computerBuilder.withIntroduced(introduced);
        }
        
        if ( discontinued != null ) {
            computerBuilder.withDiscontinued(discontinued);
        }
        
        if ( company.isPresent() ) {
            computerBuilder.withCompany(company.get());
        }
        
        Computer computer = computerBuilder.build();
        return Optional.of(computer);
    }

}
