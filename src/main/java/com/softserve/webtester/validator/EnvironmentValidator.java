package com.softserve.webtester.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.service.EnvironmentService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link Environment} instance. Checks the unique of
 * environment's parameter name and parameter <code>dbPort</code>.
 */
@Component
public class EnvironmentValidator implements Validator {

    /**
     * Pattern for checking validity of {@link Environment} insance's property dbPort
     */
    private static final String DB_PORT_REGEX_PATTERN = "\\d{1,5}";

    @Autowired
    private EnvironmentService environmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Environment.class.equals(clazz);
    }

    /**
     * Checks if {@link Environment} instance with this name is already exist in the DB and if parameter <code>dbPort</code>
     * matches to pattern
     */
    @Override
    public void validate(Object target, Errors e) {
        Environment environment = (Environment) target;
        if (environmentService.isNameFree(environment) > 0) {
            e.rejectValue("name", "NotUnique.environment.name", "Name should be unique");
        }
        
        if (!isMatched(Integer.toString(environment.getDbPort()))) {
            e.rejectValue("dbPort", "Pattern.environment.dbPort", "Database port is not valid");
        }

    }

    /**
     * Checks if {@link Environment} instance's property dbPort is matched to {@link DB_PORT_REGEX_PATTERN}
     * @param dbPort should be checked
     */
    private boolean isMatched(String dbPort) {
        Pattern pattern = Pattern.compile(DB_PORT_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(dbPort);
        return matcher.matches();
    }

}
