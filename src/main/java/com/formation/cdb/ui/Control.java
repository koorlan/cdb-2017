package com.formation.cdb.ui;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.entities.impl.Company;
import com.formation.cdb.entities.impl.Computer;
import com.formation.cdb.util.DateUtil;


public class Control {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(Control.class);
	
		public static boolean isValidComputer(Optional<Computer> c){
			if(!c.isPresent() ) {
				LOGGER.warn("Sorry your computer object is empty");
				return false;
			}
			
			Computer computer = c.get();
			
			boolean valid = true;
			
			valid = valid && isIdGreaterThanZero(computer.getId());
			valid = valid && isValidString(computer.getName());
			valid = valid && isIntroducedDateBeforeDiscontinuedDate(computer.getIntroduced(),computer.getDiscontinued());
			
			if(computer.getCompany().isPresent()){
				Company company = computer.getCompany().get();
				valid = valid && isIdGreaterThanZero(company.getId());
				valid = valid && isValidString(company.getName());
			}
			
			if(valid) {
				LOGGER.info("Computer is valid. " + computer);
			} else {
				LOGGER.info("Computer is invalid. " + computer);
			}
			
			return valid;
		}
		
		public static boolean isValidStringDate(Optional<String> date){
			
			if(!date.isPresent() && isValidString(date)) {
				LOGGER.warn("String provided is empty or invalid");
				return false;
			}
			
			try {
				Optional<LocalDate> testDate = date.map(DateUtil::StringToDate);
				return testDate.isPresent();
			} catch (DateTimeParseException e){
				LOGGER.warn("There were a problem parsing the date: " + date.get() );
				return false;
			}
		}
		
		private static boolean isValidString(Optional<String> name){
			if(!name.isPresent()) {
				LOGGER.warn("Optional provided is empty.");
				return false;
			}
			
			boolean valid = !name.get().trim().isEmpty();
			
			if(!valid) {
				LOGGER.warn("String is empty. " + name.get() );
			}
					
			return valid;
		}

		private static boolean isIntroducedDateBeforeDiscontinuedDate(Optional<LocalDate> introduced, Optional<LocalDate> discontinued){
			if(!introduced.isPresent() || !discontinued.isPresent()) {
				LOGGER.warn("Introduced date or discontinued date is missing. Introduced: "+ introduced + " ,Discontinued: " + discontinued);
				return false;
			}
			
			return introduced.get().isBefore(discontinued.get());	
		}
		
		private static boolean isIdGreaterThanZero(long id){
			if(id <= 0) {
				LOGGER.warn("id must be greater than 0 id: " + id);
			}
			return id > 0;
		}
}
