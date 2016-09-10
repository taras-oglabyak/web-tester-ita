package com.softserve.webtester.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.softserve.webtester.model.Application;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.validator.ApplicationValidator;

/**
 * ApplicationController class represents {@code Application} MVC Controller. 
 * Handles and retrieves Application pages depending on the URI template.
 *
 * @author Roman Zolotar
 * @version 1.3
 */

@Controller
@RequestMapping(value = "configuration/applications")
public class ApplicationController {
    private static final String APPLICATION = "application"; 
    private static final String APPLICATIONS = "applications";
    private static final String IS_UPDATE = "isUpdate";

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private ApplicationValidator applicationValidator;

    @InitBinder("application")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(applicationValidator);
    }
    
    /**
     * Retrieves page with existing Applications.
     * 
     * @param model container with Application list
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getNotDeletedApplicationList(Model model) {
        List<Application> applications = metaDataService.applicationLoadAllWithoutDeleted();
        model.addAttribute(APPLICATIONS, applications);
        return "application/list";
    }

    /**
     * Retrieves Application modify page.
     * 
     * @param id identifier of modifying {@link Application} instance
     * @param model container with Application object
     * @return name of view
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String getApplication(@PathVariable(value = "id") Integer applicationId, Model model) {
        Application application = metaDataService.applicationLoad(applicationId);
        model.addAttribute(IS_UPDATE, true);
        model.addAttribute(APPLICATION, application);
        return "application/update";
    }

    /**
     * Retrieves new Application creation page.
     * 
     * @param model container with Application object
     * @return name of view
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createApplication(Model model) {
        model.addAttribute(IS_UPDATE, false);
        model.addAttribute(APPLICATION, new Application());
        return "application/update";
    }

    /**
     * Handles creating new Application.
     * 
     * @param {@link Application} instance which should be saved
     * @param result {@link BindingResult} validation handle object
     * @return if success, redirects to Application main page 
     *         in case of validation errors returns to creating page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveCreatedApplication(@Validated @ModelAttribute("application") Application application,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(IS_UPDATE, false);
            return "application/update";
        }
        metaDataService.applicationSave(application);
        return "redirect:/configuration/applications";
    }

    /**
     * Handles modifying an Application.
     * 
     * @param {@link Application} instance which should be saved
     * @param result {@link BindingResult} validation handle object
     * @return if success, redirects to Application main page 
     *         in case of validation errors returns to modifying page
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String saveUpdatedApplication(@Validated @ModelAttribute("application") Application application,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(IS_UPDATE, true);
            return "application/update";
        }
        metaDataService.applicationUpdate(application);
        return "redirect:/configuration/applications";
    }

    /**
     * Retrieves Application deleting page.
     * 
     * @param id identifier of deleting {@link Application} instance
     * @return if success, redirects to Application main page 
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteApplication(@PathVariable(value = "id") Integer id, Model model) {
        metaDataService.applicationSoftDelete(id);
        return "redirect:/configuration/applications";
    }

}