package com.softserve.webtester.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softserve.webtester.dto.RequestFilterDTO;
import com.softserve.webtester.editor.ApplicationEditor;
import com.softserve.webtester.editor.ServiceEditor;
import com.softserve.webtester.model.Application;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.ResponseType;
import com.softserve.webtester.model.Service;
import com.softserve.webtester.model.VariableDataType;
import com.softserve.webtester.service.EnvironmentService;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.RequestService;
import com.softserve.webtester.service.RunService;
import com.softserve.webtester.validator.RequestValidator;

/**
 * Handles and retrieves {@link Request} pages depending on the URI template. A user must be log-in first he can access
 * this page.
 * 
 * @author Taras Oglabyak
 */
@Controller
@RequestMapping(value = "/tests/requests")
public class RequestController {

    private static final String APPLICATIONS = "applications";
    private static final String SERVICES = "services";
    private static final String LABELS = "labels";
    private static final String ENVIRONMENTS = "environments";
    private static final String REQUESTS = "requests";
    private static final String REQUEST_METHODS = "requestMethods";
    private static final String RESPONSE_TYPES = "responseTypes";
    private static final String VARIABLE_DATATYPES = "variableDataTypes";

    @Autowired
    private RequestService requestService;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private ApplicationEditor applicationEditor;

    @Autowired
    private ServiceEditor serviceEditor;

    @Autowired
    private RequestValidator requestValidator;
    
    @Autowired
    private RunService runService;

    @InitBinder("request")
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Application.class, applicationEditor);
        binder.registerCustomEditor(Service.class, serviceEditor);
        binder.addValidators(requestValidator);
    }

    /**
     * Retrieves page with existing requests.
     * 
     * @param requestFilterDTO DTO object using for filtering {@link Request} instances
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getRequestsPage(@ModelAttribute RequestFilterDTO requestFilterDTO, Model model) {
        model.addAttribute(APPLICATIONS, metaDataService.applicationLoadAllWithoutDeleted());
        model.addAttribute(SERVICES, metaDataService.serviceLoadAllWithoutDeleted());
        model.addAttribute(LABELS, metaDataService.loadAllLabels());
        model.addAttribute(ENVIRONMENTS, environmentService.loadAll());
        model.addAttribute(REQUESTS, requestService.loadAll(requestFilterDTO));
        return "request/requests";
    }

    /**
     * Creates ModelMap container with metadata lists.
     * 
     * @return {@link ModelMap} instance
     */
    private ModelMap addMetaData() {
        ModelMap map = new ModelMap();
        map.addAttribute(APPLICATIONS, metaDataService.applicationLoadAllWithoutDeleted());
        map.addAttribute(SERVICES, metaDataService.serviceLoadAllWithoutDeleted());
        map.addAttribute(REQUEST_METHODS, com.softserve.webtester.model.RequestMethod.values());
        map.addAttribute(RESPONSE_TYPES, ResponseType.values());
        map.addAttribute(VARIABLE_DATATYPES, VariableDataType.values());
        map.addAttribute(LABELS, metaDataService.loadAllLabels());
        return map;
    }

    /**
     * Retrieves page for creating new request with empty request instance or with duplicate of existing request
     * instance.
     * 
     * @param fromId identifier of existing {@link Request}
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreateRequestPage(@RequestParam(required = false) Integer fromId, 
            Model model) {
        model.addAllAttributes(addMetaData());
        Request request = null;
        if (fromId != null) {
            request = requestService.createDuplicate(fromId);
        } else {
            request = new Request();
            request.setTimeout(requestService.getDefaultTimeout());
        }
        model.addAttribute("request", request);
        return "request/requestCreateEdit";
    }

    /**
     * Handles creating new request.
     * 
     * @param request {@link Request} instance should be saved
     * @param result {@link BindingResult} validation handle object
     * @param model container with metadata lists
     * @return if success, redirects to requests main page; in case of validation errors returns to creating page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String confirmNewRequest(@Validated @ModelAttribute Request request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAllAttributes(addMetaData());
            return "request/requestCreateEdit";
        }
        requestService.save(request);
        return "redirect:/tests/requests";
    }

    /**
     * Retrieves request edit page.
     * 
     * @param id identifier of editing {@link Request} instance
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String getEditRequestPage(@PathVariable int id, Model model) {
        model.addAllAttributes(addMetaData());
        model.addAttribute("request", requestService.load(id));
        return "request/requestCreateEdit";
    }

    /**
     * Handles request updating.
     * 
     * @param id identifier of Request should be updated
     * @param request {@link Request} instance should be updated
     * @param result {@link BindingResult} instance
     * @param map {@link ModelMap} instance
     * @return if success, redirects to requests main page; in case of validation errors returns to editing page
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String confirmEditRequest(@PathVariable int id, @Validated @ModelAttribute Request request,
            BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAllAttributes(addMetaData());
            return "request/requestCreateEdit";
        }
        requestService.update(request);
        return "redirect:/tests/requests";
    }

    /**
     * Handles request deleting. If success, returns 204 (NO_CONTENT) HTTP status.
     * 
     * @param id identifier of {@link Request} should be updated
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        requestService.delete(id);
    }

    /**
     * Handles deleting requests. If success, returns 204 (NO_CONTENT) HTTP status.
     * 
     * @param requestIdArray array of requests identifiers should be deleted
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequests(@RequestBody int[] requestIdArray) {
        requestService.delete(requestIdArray);
    }

    /**
     * Handles requests run.
     * 
     * @param environmentId identifier of {@link Environment} instance
     * @param requestIdArray identifiers of {@link Request} instance
     * @return identifier of requests run
     */
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public @ResponseBody int runRequests(@RequestParam int environmentId,
            @RequestParam(value = "requestIdArray[]") int[] requestIdArray) {
        return runService.run(environmentId, requestIdArray);
    }

    /**
     * Checks the unique of {@link Request} instance's name.
     * 
     * @param name name property should be checked
     * @param exclusionId identifier of {@link Request} instance should be excluded from checking
     * @return is request name free boolean value
     */
    @RequestMapping(value = "/isRequestNameFree", method = RequestMethod.POST)
    public @ResponseBody boolean isRequestNameFree(@RequestParam String name, @RequestParam int exclusionId) {
        return StringUtils.isNotBlank(name) && requestService.isRequestNameFree(name, exclusionId);
    }
}