package com.softserve.webtester.controller;

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
@RequestMapping(value = "/results/requests")
public class ResultHistoryController {

    private static final String APPLICATIONS = "applications";
    private static final String SERVICES = "services";
    private static final String LIST = "list";
    private static final String RESULT = "result";

    @Autowired
    private ResultHistoryService resultHistoryService;

    @Autowired
    private MetaDataService metaDataService;

    /**
     * Retrieves general page with existing resultHistories.
     * @param resultFilterDTO DTO object using for filtering {@link resultHistory} instances
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String listResults(@ModelAttribute ResultFilterDTO resultFilterDTO, Model model) {

        model.addAttribute(APPLICATIONS, metaDataService.applicationLoadAll());
        model.addAttribute(SERVICES, metaDataService.serviceLoadAll());
        model.addAttribute(LIST, resultHistoryService.loadAll(resultFilterDTO));
        return "requestResult";
    }

    /**
     * Retrieves page with existing resultHistories by RunId.
     * @param resultFilterDTO DTO object using for filtering {@link resultHistory} instances
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
    public String listResultsByRuId(@ModelAttribute ResultFilterDTO resultFilterDTO, @PathVariable int id, Model model) {

        model.addAttribute(APPLICATIONS, metaDataService.applicationLoadAll());
        model.addAttribute(SERVICES, metaDataService.serviceLoadAll());
        resultFilterDTO.setRunId(id);
        model.addAttribute(LIST, resultHistoryService.loadAllRequestsByRunId(resultFilterDTO));
        return "requestResult";
    }

    /**
     * Handles deleting resultHistory. If success, returns 204 (NO_CONTENT) HTTP status.
     * @param id identifier of {@link resultHistory} should be updated
     * @return redirects to results main page
     */
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeResult(@PathVariable int id){

        resultHistoryService.deleteByResultHistoryId(id);
        return "redirect:/results/requests";
    }

    /**
     * Handles deleting resultHistory. If success, returns 204 (NO_CONTENT) HTTP status.
     * @param arr array of resultHistories identifiers should be deleted
     */
    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequests(@RequestBody int[] arr) {

        resultHistoryService.deleteSelectedResults(arr);
    }

    /**
     * Handles showing resultHistory. If success, returns 204 (NO_CONTENT) HTTP status.
     * @param id identifier of {@link resultHistory} should be showing
     * @param model {@link Model} object
     * @return name of view
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String seeResult(@PathVariable int id, Model model){

        model.addAttribute(RESULT,resultHistoryService.loadById(id));
        return "result_detailed";
    }
}