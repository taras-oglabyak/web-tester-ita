package com.softserve.webtester.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.softserve.webtester.dto.RequestCollectionFilterDTO;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.service.EnvironmentService;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.RequestCollectionService;
import com.softserve.webtester.service.RequestService;
import com.softserve.webtester.service.RunService;
import com.softserve.webtester.validator.CollectionValidator;

/**
 * Handles and retrieves {@link RequestCollection} pages depending on the URI template. A user must be log-in first he
 * can access this page.
 * 
 * @author Yura Lubinec
 */
@Controller
@RequestMapping(value = "/tests/collections")
public class RequestCollectionController {

    private static final String LABEL = "labels";
    private static final String REQUESTS = "requests";
    private static final String COLLECTION = "requestCollection";
    private static final String BUILD_VERSION = "buildVersions";
    private static final String COLLECTION_LIST = "collectionList";
    private static final String ENVIRONMENTS = "environments";

    @Autowired
    private RequestCollectionService requestCollectionService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private CollectionValidator requestCollectionValidator;
    
    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private RunService runService;

    @InitBinder("requestCollection")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(requestCollectionValidator);
        
    }

    /**
     * Retrieves page with all existing requestCollections.
     * 
     * @param requestCollectionFilterDTO DTO object using for filtering {@link RequestCollection} instances
     * @param model container for {@link RequestCollection} and {@link Label} instances
     * @return 'collections' view and founded requestCollections
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllRequestCollection(@ModelAttribute RequestCollectionFilterDTO requestCollectionFilterDTO, 
            Model model) {
        model.addAttribute(LABEL, metaDataService.loadAllLabels());
        model.addAttribute(BUILD_VERSION, metaDataService.loadAllBuildVersions());
        model.addAttribute(COLLECTION_LIST, requestCollectionService.loadAll(requestCollectionFilterDTO));
        model.addAttribute(ENVIRONMENTS, environmentService.loadAll());
        return "collection/collections";
    }

    /**
     * Retrieves page for creating new requestCollection with empty requestCollection instance.
     * 
     * @param model container for {@link RequestCollection}, {@link Request} and {@link Label} instances
     * @return 'collectionCreateEdit' view with requestCollection instance
     */
    @RequestMapping(value = "/newCollection", method = RequestMethod.GET)
    public String getEmptyFormForRequestCollection(Model model) {
        model.addAttribute(LABEL, metaDataService.loadAllLabels());
        model.addAttribute(REQUESTS, requestService.loadAll());
        model.addAttribute(COLLECTION, new RequestCollection());
        return "collection/collectionCreateEdit";
    }

    /**
     * Handles creating new request.
     * 
     * @param request {@link RequestCollection} instance should be saved
     * @param result {@link BindingResult} validation handle object
     * @param model container for {@link RequestCollection}, {@link Request} and {@link Label} instances
     * @return if success, redirects to requestCollections main page
     */
    @RequestMapping(value = "/newCollection", method = RequestMethod.POST)
    public String saveRequestCollection(@Validated @ModelAttribute RequestCollection requestCollection,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(LABEL, metaDataService.loadAllLabels());
            model.addAttribute(REQUESTS, requestService.loadAll());
            return "collection/collectionCreateEdit";
        }
        requestCollectionService.save(requestCollection);
        return "redirect:/tests/collections";
    }

    /**
     * Retrieves requestCollection edit page
     * 
     * @param id identifier of editing {@link RequestCollection} instance
     * @param model container for {@link RequestCollection}, {@link Request} and {@link Label} instances
     * @return 'collectionCreateEdit' view
     */ 
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String getRequestCollection(@PathVariable int id, Model model) {
        model.addAttribute(LABEL, metaDataService.loadAllLabels());
        model.addAttribute(REQUESTS, requestService.loadAll());
        model.addAttribute(COLLECTION, requestCollectionService.load(id));
        return "collection/collectionCreateEdit";
    }

    /**
     * Handles request updating.
     * 
     * @param id identifier of RequestCollection should be updated
     * @param requestCollection {@link RequestCollection} instance should be updated
     * @param result {@link BindingResult} instance
     * @param model container for {@link RequestCollection}, {@link Request} and {@link Label} instances
     * @return if success, redirects to requestCollections main page;
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String EditRequestCollection(@PathVariable int id,
            @Validated @ModelAttribute RequestCollection requestCollection, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(LABEL, metaDataService.loadAllLabels());
            model.addAttribute(REQUESTS, requestService.loadAll());
            return "collection/collectionCreateEdit";
        }
        requestCollectionService.update(requestCollection);
        return "redirect:/tests/collections";
    }

    /**
     * Handles deleting requestCollections. If success, returns 204 (NO_CONTENT) HTTP status.
     * 
     * @param requestColelctionIdArray array of requests identifiers should be deleted
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detele(@RequestBody int[] requestCollectionIdArray) {
        requestCollectionService.delete(requestCollectionIdArray);
    }
    
    /**
     * Handles requestCollection run.
     * 
     * @param environmentId identifier of {@link Environment} instance
     * @param buildVersionId identifier or {@link BuildVersion} istance
     * @param requestCollectionIdArray identifiers of {@link RequestCollection} instance
     * @return identifier of requests run
     */
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public @ResponseBody int runRequestCollection(@RequestParam int environmentId, @RequestParam int buildVersionId,
            @RequestParam(value = "requestCollectionIdArray[]") int[] requestCollectionIdArray) {
        return runService.run(environmentId, buildVersionId, requestCollectionIdArray);
    }
}
