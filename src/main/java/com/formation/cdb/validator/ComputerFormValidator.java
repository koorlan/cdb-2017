package com.formation.cdb.validator;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.formation.cdb.entity.ComputerDto;
import com.formation.cdb.entity.impl.Computer;
import com.formation.cdb.util.DateUtil;

@Component
public class ComputerFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return ComputerDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ComputerDto computer = (ComputerDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.computerForm.name");
        
        if ( StringUtils.isNotBlank(computer.getDiscontinued()) && StringUtils.isNotBlank(computer.getIntroduced()) ) {
            LocalDate introduced = DateUtil.stringToDateDashSeparatedYYYYMMDD(computer.getIntroduced());
            LocalDate discontinued = DateUtil.stringToDateDashSeparatedYYYYMMDD(computer.getDiscontinued());
            
            if ( discontinued.isBefore(introduced)) {
                errors.rejectValue("discontinued", "NotBeforeIntroduced.computerForm.discontinued");
            }   
        }
    }

}
