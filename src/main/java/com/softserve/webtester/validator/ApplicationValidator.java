package com.softserve.webtester.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.Application;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link Application} instance. Checks the unique of
 * Application`s name.
 * 
 * @author Roman Zolotar
 *
 */

@Component
public class ApplicationValidator implements Validator {
    
    private static final String NAME = "name";

    @Autowired
    private MetaDataService metaDataService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Application.class.equals(clazz);
    }

    /**
     * Checks if Application object with this name is already exist in the DB.
     *
     * @param object that checked for validity.
     * @param errors which appeared when Application with this name already exist.
     */
    
    @Override
    public void validate(Object target, Errors errors) {
        Application application = (Application) target;
        if (!metaDataService.isApplicationNameFree(application.getName(), application.getId())) {
            errors.rejectValue(NAME, null, "Name should be unique");
        }

    }

}
