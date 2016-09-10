package com.softserve.webtester.controller;

import com.softserve.webtester.dto.ResultCollectionFilterDTO;
import com.softserve.webtester.dto.ResultFilterDTO;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.service.ResultHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles and retrieves {@link resultHistory} pages.
 * @author Viktor Somka
 */
@Controller
@RequestMapping(value = "/results/collections")
public class ResultHistoryCollectionController {

    private static final String BUILD_VERSIONS = "buildVersions";
    private static final String LABELS = "labels";
    private static final String APPLICATIONS = "applications";
    private static final String SERVICES = "services";
    private static final String LIST = "list";

    @Autowired
    private ResultHistoryService resultHistoryService;

    @Autowired
    private MetaDataService metaDataService;

    /**
     * Retrieves general page with existing requests.
     * @param resultCollectionFilterDTO DTO object using for filtering {@link resultHistory} instances
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String listResults(@ModelAttribute ResultCollectionFilterDTO resultCollectionFilterDTO, Model model) {
        model.addAttribute(BUILD_VERSIONS, metaDataService.loadAllBuildVersions());
        model.addAttribute(LABELS, metaDataService.loadAllLabels());
        model.addAttribute(LIST, resultHistoryService.loadAllCollections(resultCollectionFilterDTO));
        return "collectionResult";
    }

    /**
     * Retrieves page with existing requests by RunId.
     * @param resultCollectionFilterDTO DTO object using for filtering {@link resultHistory} instances
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
    public String listCollectionResultsByRuId(@ModelAttribute ResultCollectionFilterDTO resultCollectionFilterDTO,
                                              @PathVariable int id, Model model) {

        model.addAttribute(BUILD_VERSIONS, metaDataService.loadAllBuildVersions());
        model.addAttribute(LABELS, metaDataService.loadAllLabels());
        resultCollectionFilterDTO.setRunId(id);
        model.addAttribute(LIST, resultHistoryService.loadAllCollectionsByRunId(resultCollectionFilterDTO));
        return "collectionResult";
    }

    /**
     * Handles deleting resultHistory. If success, returns 204 (NO_CONTENT) HTTP status.
     * @param id identifier of {@link resultHistory} should be updated
     * @return redirects to results main page
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeResult(@PathVariable int id){

        resultHistoryService.deteleByCollectionId(id);
        return "redirect:/results/collections";
    }

    /**
     * Handles deleting resultHistory. If success, returns 204 (NO_CONTENT) HTTP status.
     * @param arr array of resultHistories identifiers should be deleted
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequests(@RequestBody int[] arr) {

        resultHistoryService.deleteSelectedCollectionResults(arr);
    }

    /**
     * Handles showing resultHistory.
     * @param id identifier of {@link resultHistory} should be showing
     * @param runId identifier of {@link resultHistory} instance should be showing
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showRequests(@PathVariable("id") int id, @RequestParam("runId") int runId,
                               @ModelAttribute ResultFilterDTO resultFilterDTO, Model model){

        model.addAttribute(APPLICATIONS, metaDataService.applicationLoadAll());
        model.addAttribute(SERVICES, metaDataService.serviceLoadAll());
        model.addAttribute(LIST, resultHistoryService.loadAllRequestsByCollectionId(resultFilterDTO, id, runId));
        return "requestResult";
    }
}