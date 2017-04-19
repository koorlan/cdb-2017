package com.formation.cdb.validator;

import java.time.LocalDate;
import java.util.Optional;

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

@Component
public class ComputerFormValidator implements Validator {

    Logger LOGGER = LoggerFactory.getLogger(ComputerFormValidator.class);

    @Autowired
    @Qualifier("companyServiceImpl")
    private CDBService<Company> companyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ComputerDto computer = (ComputerDto) target;
        LOGGER.debug(computer.toString());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.computerForm.name");



        if (computer.getIntroduced() != null && computer.getDiscontinued() != null && computer.getDiscontinued().isBefore(computer.getIntroduced())) {
            LOGGER.debug("discontinued is before introduced " + computer);
            errors.rejectValue("discontinued", "NotBeforeIntroduced.computerForm.discontinued");
        }

        if (computer.getCompany() != null) {
            if (computer.getCompany().getId() < 0) {
                LOGGER.debug("Company is null or bad id" + computer.getCompany());
                errors.rejectValue("company", "InvalidId.computerForm.company");
            } else {

                Optional<Company> company = companyService.findById(computer.getCompany().getId());
                LOGGER.debug("Company fetch from database : " + company);
                Optional<CompanyDto> companyDto = CompanyDtoMapper.mapCompanyDtoFromCompany(company);
                LOGGER.debug("CompanyDto after mapping : " + companyDto);
                if (companyDto.isPresent()) {
                    computer.setCompany(companyDto.get());
                }
            }
        }
    }

}
