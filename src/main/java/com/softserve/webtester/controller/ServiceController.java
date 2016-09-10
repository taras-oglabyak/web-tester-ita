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

import com.softserve.webtester.model.Service;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.validator.ServiceValidator;

/**
 * ServiceController class represents {@code Service} MVC Controller.
 * Handles and retrieves Service pages depending on the URI template.
 *
 * @author Roman Zolotar
 * @version 1.3
 */

@Controller
@RequestMapping(value = "/configuration/services")
public class ServiceController {
    private static final String SERVICE = "service";
    private static final String SERVICES = "services";
    private static final String IS_UPDATE = "isUpdate";

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private ServiceValidator serviceValidator;

    @InitBinder("service")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(serviceValidator);
    }

    /**
     * Retrieves page with existing Services.
     * 
     * @param model container with Services list
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getNotDeletedServiceList(Model model) {
        List<Service> services = metaDataService.serviceLoadAllWithoutDeleted();
        model.addAttribute(SERVICES, services);
        return "service/list";
    }

    /**
     * Retrieves Service modify page.
     * 
     * @param id identifier of modifying {@link Service} instance
     * @param model container with Service object
     * @return name of view
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String getService(@PathVariable(value = "id") Integer serviceId, Model model) {
        Service service = metaDataService.serviceLoad(serviceId);
        model.addAttribute(IS_UPDATE, "true");
        model.addAttribute(SERVICE, service);
        return "service/update";
    }

    /**
     * Retrieves new Service creation page.
     * 
     * @param model container with Service object
     * @return name of view
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createService(Model model) {
        model.addAttribute(IS_UPDATE, "false");
        model.addAttribute(SERVICE, new Service());
        return "service/update";
    }

    /**
     * Handles creating new Service.
     * 
     * @param {@link Service} instance which should be saved
     * @param result {@link BindingResult} validation handle object
     * @return if success, redirects to Service main page 
     *         in case of validation errors returns to creating page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String saveCreatedService(@Validated @ModelAttribute("service") Service service, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute(IS_UPDATE, "false");
            return "service/update";
        }
        metaDataService.serviceSave(service);
        return "redirect:/configuration/services";
    }

    /**
     * Handles modifying an Service.
     * 
     * @param {@link Service} instance which should be saved
     * @param result {@link BindingResult} validation handle object
     * @return if success, redirects to Service main page 
     *         in case of validation errors returns to modifying page
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String saveUpdatedService(@Validated @ModelAttribute("service") Service service, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute(IS_UPDATE, "true");
            return "service/update";
        }
        metaDataService.serviceUpdate(service);
        return "redirect:/configuration/services";
    }

    /**
     * Retrieves Service deleting page.
     * 
     * @param id identifier of deleting {@link Service} instance
     * @return if success, redirects to Service main page 
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteService(@PathVariable(value = "id") Integer id, Model model) {
        metaDataService.serviceSoftDelete(id);
        return "redirect:/configuration/services";
    }
}