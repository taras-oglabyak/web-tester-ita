package com.softserve.webtester.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.EnvironmentDbType;
import com.softserve.webtester.service.EnvironmentService;
import com.softserve.webtester.validator.EnvironmentValidator;

/**
 * Handles and retrieves {@link Environment} pages depending on the URI template. A user must be log-in first he can access
 * this page.
 */
@Controller
@RequestMapping("/configuration/environments")
public class EnvironmentController {

	private static final String ENVIRONMENT_LIST = "environmentList";
	private static final String PAGE_TASK = "pageTask";
	private static final String ENVIRONMENT = "environment";
	private static final String DB_TYPES = "dbTypes";
	
    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EnvironmentValidator environmentValidator;

    @InitBinder("environment")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(environmentValidator);
    }

    /**
     * Retrieves page with all existing environments
     * 
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getEnvironmentsPage(Model model) {
        List<Environment> environments = environmentService.loadAll();
        model.addAttribute(ENVIRONMENT_LIST, environments);
        return "environment/environmentList";
    }

    /**
     * Retrieves page for creating new environment with empty environment instance
     * 
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getEnvironmentCreatePage(Model model) {

        model.addAttribute(PAGE_TASK, "Create");
        Environment environment = new Environment();
        environment.setTimeMultiplier(environmentService.getDefaultTimeMultiplier());
        model.addAttribute(ENVIRONMENT, environment);
        model.addAttribute(DB_TYPES, EnvironmentDbType.values());
        return "environment/environmentCreateOrUpdate";
    }

    /**
     * Handles creating new environment
     * 
     * @param {@link Environment} instance should be saved
     * @param result {@link BindingResult} validation handle object
     * @param model {@link Model} object
     * @return if success, redirects to environments main page, in case of validation errors returns to creating page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEnvironment(@Validated @ModelAttribute Environment environment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PAGE_TASK, "Create");
            model.addAttribute(DB_TYPES, EnvironmentDbType.values());
            return "environment/environmentCreateOrUpdate";
        }
        environmentService.save(environment);
        return "redirect:/configuration/environments";
    }

    /**
     * Retrieves environment modifying page
     * 
     * @param id identifier of editing {@link Environment} instance
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public String getEnvironmentUpdatePage(@PathVariable int id, Model model) {
        model.addAttribute(PAGE_TASK, "Update");
        model.addAttribute("id", id);
        Environment environment = environmentService.load(id);
        model.addAttribute(ENVIRONMENT, environment);
        model.addAttribute(DB_TYPES, EnvironmentDbType.values());
        return "environment/environmentCreateOrUpdate";
    }

    /**
     * Handles environment updating.
     * 
     * @param id identifier of Environment should be updated
     * @param environment {@link Environment} instance should be updated
     * @param result {@link BindingResult} instance
     * @param model {@link Model} object
     * @return if success, redirects to environments main page; in case of validation errors returns to modifying page
     */
    @RequestMapping(value = "modify/{id}", method = RequestMethod.POST)
    public String updateEnvironment(@PathVariable int id, @Validated @ModelAttribute Environment environment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(PAGE_TASK, "Update");
            model.addAttribute(DB_TYPES, EnvironmentDbType.values());
            return "environment/environmentCreateOrUpdate";
        }
        environmentService.update(environment);
        return "redirect:/configuration/environments";
    }

    /**
     * Handles environment deleting
     * 
     * @param id identifier of {@link Environment} should be deleted
     * @return redirects to environments main page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteEnvironment(Environment environment) {
        environmentService.delete(environment);
        return "redirect:/configuration/environments";
    }

    /**
     * Checks established connection to DB
     * 
     * @param id identifier of {@link Environment} which connection should be checked
     * @param model {@link Model} object
     * @return {@link ResponseEntity} with information message and status
     */
    @RequestMapping(value = "/check/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> checkEnvironment(@PathVariable int id, Model model) {
        String message = StringUtils.EMPTY;
        HttpStatus status = HttpStatus.OK;
        
        Environment environment = environmentService.load(id);

        try {
            environmentService.checkConnection(environment);
            message = String.format("%s: environment connection was checked successfully", environment.getName());
        } catch (Exception e) {
            message = String.format("%s: environment check finished with  error: %s", environment.getName(), e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(message, status);

        return responseEntity;
    }
}
