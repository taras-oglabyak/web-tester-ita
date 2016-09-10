package com.softserve.webtester.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.softserve.webtester.model.User;
import com.softserve.webtester.service.UserService;

/**
 * Implementation of {@link Validator} interface for checking the unique of {@link User} instance's
 * <code>username</code> property.
 * 
 * @author Taras Oglabyak
 *
 */
@Component
public class UsernameUniqueValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = currentAuthentication.getName();
        user.setId(Integer.parseInt(currentUsername));
        if (!userService.isUsernameFree(user.getId(), user.getUsername())) {
            errors.rejectValue("username", "NotUnique.user.username", "Email should be unique");
        }
    }
}