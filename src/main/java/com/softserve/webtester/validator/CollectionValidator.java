package com.softserve.webtester.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.service.RequestCollectionService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link RequestCollection} instance. Checks the
 * unique of requestCollection's parameter name parameter.
 * 
 * @author Yura Lubinec
 *
 */
@Component
public class CollectionValidator implements Validator {

    @Autowired
    private RequestCollectionService requestCollectionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RequestCollection.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestCollection requestCollection = (RequestCollection) target;
        if (!requestCollectionService.isRequestCollectionNameFree(requestCollection.getName(),
                requestCollection.getId())) {
            errors.rejectValue("name", "NotUnique.requestCollection.name"); 
        }

    }
}
