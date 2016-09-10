package com.softserve.webtester.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.Variable;
import com.softserve.webtester.service.RequestService;

/**
 * Implementation of {@link Validator} interface for additional checking {@link Request} instance. Checks the unique of
 * request's parameter name and valiable's parameter <code>length</code>.
 * 
 * @author Taras Oglabyak
 *
 */
@Component
public class RequestValidator implements Validator {

    @Autowired
    private RequestService requestService;

    @Autowired
    private VariableValidator variableValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request request = (Request) target;
        if (!requestService.isRequestNameFree(request.getName(), request.getId())) {
            errors.rejectValue("name", "NotUnique.request.name", "Name should be unique");
        }

        List<Variable> variables = request.getVariables();
        if (!CollectionUtils.isEmpty(variables)) {
            for (int i = 0; i < variables.size(); i++) {
                try {
                    errors.pushNestedPath("variables[" + i + "]");
                    ValidationUtils.invokeValidator(variableValidator, variables.get(i), errors);
                } finally {
                    errors.popNestedPath();
                }
            }
        }
    }
}