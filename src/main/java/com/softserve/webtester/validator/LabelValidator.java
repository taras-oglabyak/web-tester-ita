package com.softserve.webtester.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.Label;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link Label} instance. Checks the unique of
 * label's parameter name.
 *
 * @author Anton Mykytiuk
 */
@Component
public class LabelValidator implements Validator {

    private static final String NAME_FIELD = "name";

    @Autowired
    private MetaDataService metaDataService;

    /**
     * Compare incoming class with Label class if they are equals.
     *
     * @param clazz compared with Label class.
     * @return true, when compared classes are equals.
     */
    @Override
    public boolean supports(Class clazz) {
        return Label.class.equals(clazz);
    }

    /**
     * Checks if Label object with this name is already exist in the DB.
     *
     * @param object that checked for validity.
     * @param errors appeared when label with this name already exist.
     */
    @Override
    public void validate(Object object, Errors errors) {

        Label label = (Label) object;
        if (!metaDataService.isLabelNameFree(label.getName(), label.getId())) {
            errors.rejectValue(NAME_FIELD, "NotUnique.label.name");
        }
    }
}
