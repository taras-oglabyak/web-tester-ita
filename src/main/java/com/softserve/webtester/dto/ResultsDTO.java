package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;

import com.softserve.webtester.model.Environment;

/**
 * Data transfer object class for transferring run results. Includes such fields as runId, buildVersionId, environment,
 * collectionResultDTOList. In case when we run one request, requests list or one collection collectionResultDTOList
 * list contains only one CollectionResultDTO object.
 *
 * @author Anton Mykytiuk
 */
public class ResultsDTO implements Serializable {

    private static final long serialVersionUID = -3767031811760898128L;

    private int runId;
    private int buildVersionId;
    private Environment environment;
    private List<CollectionResultDTO> collectionResultDTOList;

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public int getBuildVersionId() {
        return buildVersionId;
    }

    public void setBuildVersionId(int buildVersionId) {
        this.buildVersionId = buildVersionId;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<CollectionResultDTO> getCollectionResultDTOList() {
        return collectionResultDTOList;
    }

    public void setCollectionResultDTOList(List<CollectionResultDTO> collectionResultDTOList) {
        this.collectionResultDTOList = collectionResultDTOList;
    }
}