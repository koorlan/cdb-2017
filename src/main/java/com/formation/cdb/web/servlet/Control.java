package com.formation.cdb.web.servlet;

import com.formation.cdb.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Control {

    private static final Logger LOGGER = LoggerFactory.getLogger(Control.class);

    public static boolean isRequestParameterPresent(HttpServletRequest request, String parameter) {
        return request.getParameter(parameter) != null;
    }

    public static boolean isSessionAttributePresent(HttpServletRequest request, String attribute) {
        return request.getSession().getAttribute(attribute) != null;
    }

    public static boolean isRequestValidForMappingComputerDtoWithoutId(HttpServletRequest request) {
        boolean isRequestValid;

        isRequestValid = isRequestParameterPresent(request, "name");
        isRequestValid &= isRequestParameterPresent(request, "introduced");
        isRequestValid &= isRequestParameterPresent(request, "discontinued");
        isRequestValid &= isRequestParameterPresent(request, "company");

        if ( isRequestValid == false) {
            LOGGER.warn("Request not valid, one parameter is null");
            return false; // One parameter is null.
        }

        String nameFromRequest = request.getParameter("name");

        isRequestValid &= isNameValid(nameFromRequest);
        
        return isRequestValid && isRequestValidForMappingComputerDto(request);
    }

    public static boolean isRequestValidForMappingComputerDtoWithId(HttpServletRequest request) {
        boolean isRequestValid;
        isRequestValid = isRequestParameterPresent(request, "id");
        isRequestValid &= isRequestParameterPresent(request, "name");
        isRequestValid &= isRequestParameterPresent(request, "introduced");
        isRequestValid &= isRequestParameterPresent(request, "discontinued");
        isRequestValid &= isRequestParameterPresent(request, "company");

        if ( isRequestValid == false) {
            LOGGER.warn("Request not valid, one parameter is null");
            return false; // One parameter is null.
        }

        String idFromRequest = request.getParameter("id");
        String nameFromRequest = request.getParameter("name");

        isRequestValid &= isIdValid(idFromRequest);
        isRequestValid &= isNameValid(nameFromRequest);

        return isRequestValid && isRequestValidForMappingComputerDto(request);
    }

    private static boolean isRequestValidForMappingComputerDto(HttpServletRequest request) {

        boolean isRequestValid = true;

        String introducedFromRequest = request.getParameter("introduced");
        if( StringUtils.isNotBlank(introducedFromRequest)) {
            isRequestValid &= isDateValid(introducedFromRequest);
        }

        String discontinuedFromRequest = request.getParameter("discontinued");
        if( StringUtils.isNotBlank(discontinuedFromRequest)) {
            isRequestValid &= isDateValid(discontinuedFromRequest);
        }

        if (StringUtils.isNotBlank(discontinuedFromRequest)
                && StringUtils.isNotBlank(introducedFromRequest)
                && isDateValid(introducedFromRequest)
                && isDateValid(discontinuedFromRequest)
                ) {
            isRequestValid &= isIntroducedBeforeDiscontinued(introducedFromRequest, discontinuedFromRequest);
        }

        String companyFromRequest = request.getParameter("company");
        if (StringUtils.isNotBlank(companyFromRequest)) {
            String companySplit[] = companyFromRequest.split(":");

            if (companySplit.length != 2) {
                return false;
            }

            String companyIdFromRequest = companySplit[0];
            String companyNameFromRequest = companySplit[1];
            isRequestValid &= isIdValid(companyIdFromRequest);
            isRequestValid &= isNameValid(companyNameFromRequest);
        }
        LOGGER.warn(Boolean.toString(isRequestValid));
        return isRequestValid;
    }

    private static boolean isIdValid(String idFromRequest) {
        try {
            long id = Long.parseLong(idFromRequest);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNameValid(String nameFromRequest) {
        
        boolean valid = StringUtils.isNotBlank(nameFromRequest);
        
        Pattern p = Pattern.compile("^[\\w\\d\\. \\-\\(\\)\\[\\]\u00C0-\u00ff]*$");
       
        Matcher m = p.matcher(nameFromRequest);
        
        valid &= m.matches();
        
        return valid;
    }

    private static boolean isDateValid(String dateFromRequest) {
        try {
            LocalDate date = DateUtil.stringToDateDashSeparatedYYYYMMDD(dateFromRequest);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isIntroducedBeforeDiscontinued(String introducedFromRequest, String discontinuedFromRequest) {
        try {
            LocalDate introduced = DateUtil.stringToDateDashSeparatedYYYYMMDD(introducedFromRequest);
            LocalDate discontinued = DateUtil.stringToDateDashSeparatedYYYYMMDD(discontinuedFromRequest);
            return introduced.isBefore(discontinued);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
