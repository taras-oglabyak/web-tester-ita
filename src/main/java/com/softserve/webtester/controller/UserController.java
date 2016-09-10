package com.softserve.webtester.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softserve.webtester.model.User;
import com.softserve.webtester.service.UserService;
import com.softserve.webtester.validator.UsernameUniqueValidator;

/**
 * Handles and retrieves user-account page depending on the URI template. A user must be log-in first he can access
 * this page.
 * 
 * @author Taras Oglabyak
 */
@Controller
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsernameUniqueValidator usernameUniqueValidator;

    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(usernameUniqueValidator);
    }

    /**
     * Retrieves user-account page.
     * 
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getUserAccountPage(Model model) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.load(userId);
        model.addAttribute("user", user);
        return "account";
    }

    /**
     * Handles changes of user-account information and saves it to the database. In case of validation errors forwards
     * user to user-account page and shows validation result's messages, otherwise redirects the user to user-account
     * page with 'success' request parameter.
     * 
     * @param user {@link User} instance should be updated
     * @param result BindingResult instance for result of validation
     * @return name of view will be returned
     */
    @RequestMapping(method = RequestMethod.POST)
    public String editAccount(@Validated @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "account";
        }
        userService.update(user);
        return "redirect:/account?success=true";
    }
    
    /**
     * Checks the unique of {@link User} instance's name.
     * 
     * @param id identifier of {@link User} instance should be excluded from checking
     * @param username username property should be checked
     * @return is user username free boolean value
     */
    @RequestMapping(value = "/isUsernameFree", method = RequestMethod.POST)
    public @ResponseBody boolean isUsernameFree(@RequestParam int id, @RequestParam String username) {
        return StringUtils.isNotBlank(username) && userService.isUsernameFree(id, username);
    }
}