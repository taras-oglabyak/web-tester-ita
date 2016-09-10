package com.softserve.webtester.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.webtester.dto.CollectionResultDTO;
import com.softserve.webtester.dto.ResultsDTO;
import com.softserve.webtester.model.Environment;

/**
 * Service, that responsible for executing requests. Has two methods with different signature, in one way it receives
 * array request's id for running request or requests list, in another - array collection's id and build version's id
 * for running collections or collections list. Also they receive environment id for getting environment entity from DB.
 *
 * @author Anton Mykytiuk
 */
@Service
public class RunService {

    @Autowired
    private ResultHistoryService resultHistoryService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestExecuteService requestExecuteService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private ParseAndWriteService parseAndWriteService;

    /**
     * Method which responsible for running request or requests list and parsing, writing into DB results.
     *
     * @param environmentId for getting environment entity from DB
     * @param requestIdArray list of request id to get request objects from DB
     * @return run id to show results
     */
    public int run(int environmentId, int[] requestIdArray) {

        Environment environment = environmentService.load(environmentId);

        /**
         * Generated run id for actual run.
         */
        int runId = resultHistoryService.getMaxId() + 1;

        return parseAndWriteService.parseAndWrite(execute(environment, requestIdArray, runId));

    }

    /**
     * Method which responsible for running collections of requests and parsing, writing into DB results.
     *
     * @param environmentId for getting environment data from DB
     * @param buildVersionId tell us to run request contained in collection for 5 times if not 0
     * @param collectionIdArray array of collection id, for getting list of request objects from DB
     * @return run id to show results
     */
    public int run(int environmentId, int buildVersionId, int[] collectionIdArray) {

        Environment environment = environmentService.load(environmentId);

        /**
         * Generated run id for actual run.
         */
        int runId = resultHistoryService.getMaxId() + 1;

        return parseAndWriteService.parseAndWrite(execute(environment, buildVersionId, collectionIdArray, runId));

    }

    /**
     * Execute request or requests list.
     *
     * @param environment data for running requests
     * @param requestIdArray array of request id to get request objects from DB
     * @param runId for getting run results from DB
     * @return ResultsDTO instance
     */
    private ResultsDTO execute(Environment environment, int [] requestIdArray, int runId) {

        ResultsDTO resultsDTO = new ResultsDTO();

        resultsDTO.setEnvironment(environment);
        resultsDTO.setBuildVersionId(0);
        resultsDTO.setRunId(runId);

        List<CollectionResultDTO> collectionResultDTOList = new ArrayList<>();

        //when we run requests' list, we run it as collection with id 0
        CollectionResultDTO collectionResultDTO = requestExecuteService.executeRequests(environment,
                requestService.loadArray(requestIdArray), false, 0);

        //when we run requests' list collectionResultDTO will contain only one element
        collectionResultDTOList.add(collectionResultDTO);

        resultsDTO.setCollectionResultDTOList(collectionResultDTOList);

        return resultsDTO;
    }

    /**
     * Execute collection or collections' list.
     *
     * @param environment data for running requests
     * @param buildVersionId tell us to run request contained in collection for 5 times
     * @param collectionIdArray array of collection id to get requests lists from DB
     * @param runId for getting run results from DB
     * @return ResultsDTO instance
     */
    private ResultsDTO execute(Environment environment, int buildVersionId, int [] collectionIdArray, int runId) {

        ResultsDTO resultsDTO = new ResultsDTO();

        resultsDTO.setEnvironment(environment);
        resultsDTO.setBuildVersionId(buildVersionId);
        resultsDTO.setRunId(runId);

        List<CollectionResultDTO> collectionResultDTOList = new ArrayList<>();

        //for every single collection run we create collectionResultDTO and then add it to the collectionResultDTOList
        for (int i = 0; i < collectionIdArray.length; i++) {
            CollectionResultDTO collectionResultDTO = requestExecuteService.executeRequests(environment,
                    requestService.loadFullRequestsByRequestCollectionId(collectionIdArray[i]),
                    (buildVersionId != 0), collectionIdArray[i]);
            collectionResultDTOList.add(collectionResultDTO);
        }

        resultsDTO.setCollectionResultDTOList(collectionResultDTOList);

        return resultsDTO;
    }

}