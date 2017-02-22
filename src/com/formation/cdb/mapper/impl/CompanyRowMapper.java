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
	
	private CompanyRowMapper(){};
	
	@Override
	public Optional<List<Company>> mapListOfObjectsFromMultipleRows( Optional<ResultSet> rs ){

		if( !rs.isPresent() || countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}
		
		try {
			
			List<Company>	companies	;
			long			id			; 
			String			name		;
			Company			company		;
			
			companies	=	new ArrayList<Company>() ;
			
			while	(rs.get().next() ) {
				
				id 			= 	rs.get().getLong("id")		;
				name 		= 	rs.get().getString("name")	;
				company 	= 	new Company(id,name)		;		
					
				companies.add( company )					;
			}
			
			return Optional.ofNullable( companies )			;
			
		} catch (SQLException e) {
			
			//TODO
			
		}
		return Optional.empty();
	}

	@Override
	public Optional<Company> mapObjectFromOneRow( Optional<ResultSet> rs ){
		
		if( !rs.isPresent() || countRowsOfResultSet(rs) <= 0) {
			return Optional.empty();
		}

		try {

			ResultSet	r		=	rs.get()				;
			long 		id		=	r.getLong("id")			;
			String		name	=	r.getString("name")		; 	
			Company		company =	new Company(id,name)	;
			
			return Optional.ofNullable(company);
			
		} catch (SQLException e) {
			
			//TODO
			
		}
		return Optional.empty();
	}

	public int countRowsOfResultSet(Optional<ResultSet> rs){
		
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
