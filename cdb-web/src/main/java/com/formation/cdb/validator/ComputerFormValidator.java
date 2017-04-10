package com.formation.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.formation.cdb.dto.CompanyDto;
import com.formation.cdb.dto.ComputerDto;
import com.formation.cdb.entity.impl.Company;
import com.formation.cdb.mapper.CompanyDtoMapper;
import com.formation.cdb.service.CDBService;
import com.formation.cdb.util.DateUtil;

@Component
public class ComputerFormValidator implements Validator {

    Logger LOGGER = LoggerFactory.getLogger(ComputerFormValidator.class);

    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ComputerDto computer = (ComputerDto) target;
        LOGGER.debug(computer.toString());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.computerForm.name");

        LocalDate introduced = null;
        LocalDate discontinued = null;
        if ( StringUtils.isNotBlank(computer.getIntroduced()) ) {
            try {
                introduced = DateUtil.stringToDateDashSeparatedYYYYMMDD(computer.getIntroduced());
            } catch (DateTimeParseException e) {
                errors.rejectValue("introduced", "BadFormat.computerForm.date");
            }
        }
        
        if ( StringUtils.isNotBlank(computer.getDiscontinued()) ) {
            try {
                discontinued = DateUtil.stringToDateDashSeparatedYYYYMMDD(computer.getDiscontinued());
            } catch (DateTimeParseException e) {
                errors.rejectValue("discontinued", "BadFormat.computerForm.date");
            }
        }
        
        if (introduced != null && discontinued != null && discontinued.isBefore(introduced)) {
            LOGGER.debug("discontinued is before introduced " + computer);
            errors.rejectValue("discontinued", "NotBeforeIntroduced.computerForm.discontinued");
        }


        if (computer.getCompany() != null && computer.getCompany().getId() < 0) {
            LOGGER.debug("Company is null or bad id" + computer.getCompany());
            errors.rejectValue("company", "InvalidId.computerForm.company");
        } else {
            // TODO not here, prefer formatter..
            Optional<Company> company = companyService.findById(computer.getCompany().getId());
            Optional<CompanyDto> companyDto = CompanyDtoMapper.mapCompanyDtoFromCompany(company);
            if (companyDto.isPresent()) {
                computer.setCompany(companyDto.get());
            }
        }

    }

}
