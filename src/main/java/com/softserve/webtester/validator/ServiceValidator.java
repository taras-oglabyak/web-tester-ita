package com.softserve.webtester.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.Service;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link Service} instance. Checks the unique of
 * Service`s name.
 * 
 * @author Roman Zolotar
 *
 */

@Component
public class ServiceValidator implements Validator {
    
    private static final String NAME = "name";

    @Autowired
    private MetaDataService metaDataService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Service.class.equals(clazz);
    }


    /**
     * Checks if Service object with this name is already exist in the DB.
     *
     * @param object that checked for validity.
     * @param errors which appeared when Service with this name already exist.
     */
    
    @Override
    public void validate(Object target, Errors errors) {
        Service service = (Service) target;
        if (!metaDataService.isServiceNameFree(service.getName(), service.getId())) {
            errors.rejectValue(NAME, null, "Name should be unique");
        }
    }
}
