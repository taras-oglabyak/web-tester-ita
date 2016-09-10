package com.softserve.webtester.service;

import com.softserve.webtester.dto.ResultCollectionFilterDTO;
import com.softserve.webtester.dto.ResultFilterDTO;
import com.softserve.webtester.mapper.*;
import com.softserve.webtester.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ResultHistoryService class implements CRUD operation on {@link ResultHistory} instance in DB
 * The service uses Spring DataSourceTransactionManager for managing transaction with DB and log4j for logging
 */
@Service
@Transactional
public class ResultHistoryService {

    private static final Logger LOGGER = Logger.getLogger(ResultHistoryService.class);

    @Autowired
    private ResultHistoryMapper resultHistoryMapper;

    @Autowired
    private HeaderHistoryMapper headerHistoryMapper;
    
    @Autowired
    private EnvironmentHistoryMapper environmentHistoryMapper;

    @Autowired
    private DbValidationHistoryMapper dbValidationHistoryMapper;

    @Autowired
    private LabelMapper labelMapper;

    /**
     * Saving {@link ResultHistory} instance to DB
     * @param resultHistory {@link ResultHistory} instance should be saved in DB
     * @return id of saved resultHistory instance
     * @throws DataAccessException
     */
    public int save(ResultHistory resultHistory) {

        try {

            if (resultHistory.getBuildVersion() != null) {
                resultHistoryMapper.save(resultHistory);
            } else if (resultHistory.getRequestCollection() != null) {
                resultHistoryMapper.saveCollection(resultHistory);
            } else {
                resultHistoryMapper.saveRequest(resultHistory);
            }
            int id = resultHistory.getId();
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save resultHistory instance ", e);
            throw e;
        }
    }

    /**
     * Loading {@link resultHistory} instance from DB
     * @param id identifier of resultHistory instance
     * @return {@link resultHistory} instance
     * @throws DataAccessException
     */
    public ResultHistory loadById(int id) {

        try {
            return resultHistoryMapper.loadById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instance with id: " + id, e);
            throw e;
        }
    }

    /**
     * Loading all stored {@link resultHistory} instances with their main information
     * @return List of {@link resultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAll() {

        try {
            return resultHistoryMapper.loadAll(false, null, null);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instances", e);
            throw e;
        }
    }

    /**
     * Loads filtered {@link ResultHistory} instances with their main information
     * @param resultFilterDTO DTO object using for filtering {@link ResultHistory} instance
     * @return List of {@link ResultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAll(ResultFilterDTO resultFilterDTO) {

        try {
            return resultHistoryMapper.loadAll(resultFilterDTO.getStatusFilter(), resultFilterDTO.getApplicationFilter(), resultFilterDTO.getServiceFilter());
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load request instances", e);
            throw e;
        }
    }

    /**
     * Loads filtered {@link ResultHistory} instances with their main information
     * @param resultCollectionFilterDTO DTO object using for filtering {@link ResultHistory} instance
     * @return List of {@link ResultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAllCollections(ResultCollectionFilterDTO resultCollectionFilterDTO) {

        try {
            return resultHistoryMapper.loadAllCollections(resultCollectionFilterDTO.getStatusFilter(), resultCollectionFilterDTO.getLabelFilter(), resultCollectionFilterDTO.getBuildVersionsFilter());
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instances", e);
            throw e;
        }
    }

    /**
     * Loading resultHistory instance from ResultHistory table using ResultFilterDTO filter instance
     * @param ResultFilterDTO
     * @param id using for selecting instances by requestCollectionId
     * @param runId using for selecting instances by runId
     * @return List of {@link ResultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAllRequestsByCollectionId(ResultFilterDTO resultFilterDTO, int id, int runId) {

        try {
            return resultHistoryMapper.loadAllRequestsByCollectionId(resultFilterDTO.getStatusFilter(), resultFilterDTO.getApplicationFilter(), resultFilterDTO.getServiceFilter(), id, runId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instances", e);
            throw e;
        }
    }

    /**
     * Loading resultHistory instance from ResultHistory table using ResultFilterDTO filter instance
     * @param ResultFilterDTO
     * @param runId using for selecting instances by runId
     * @return List of {@link ResultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAllRequestsByRunId(ResultFilterDTO resultFilterDTO) {

        try {
            return resultHistoryMapper.loadAllRequestsByRunId(resultFilterDTO.getRunId());
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instances", e);
            throw e;
        }
    }

    /**
     * Loading resultHistory instance from ResultHistory table using ResultCollectionFilterDTO filter instance
     * @param ResultCollectionFilterDTO
     * @param runId using for selecting instances by runId
     * @return List of {@link ResultHistory} instances
     * @throws DataAccessException
     */
    public List<ResultHistory> loadAllCollectionsByRunId(ResultCollectionFilterDTO resultCollectionFilterDTO) {

        try {
            return resultHistoryMapper.loadAllCollectionsByRunId(resultCollectionFilterDTO.getRunId());
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load resultHistory instances", e);
            throw e;
        }
    }

    /**
     * Deleting {@link ResultHistory} instance from DB
     * @param id identifies resultHistory to be deleted
     * @return the number of rows affected by the statement
     * @throws DataAccessException
     */
    public int deleteByResultHistoryId(int id) {

        try {
            return resultHistoryMapper.deleteByResultHistoryId(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete resultHistory instance with requestId: " + id, e);
            throw e;
        }
    }

    /**
     * Deleting {@link ResultHistory} instance from DB
     * @param id identifies resultHistory to be deleted
     * @return the number of rows affected by the statement
     * @throws DataAccessException
     */
    public int deteleByCollectionId(int id) {

        try {
            return resultHistoryMapper.deteleByCollectionId(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete resultHistory instance with collectionId: " + id, e);
            throw e;
        }
    }

    /**
     * Deleting {@link ResultHistory} instances from DB
     * @param arr identifiers resultHistories to be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    public int deleteSelectedResults(int[] arr) {

        try {
            return resultHistoryMapper.deleteSelectedResults(arr);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete result instances, results id: " + arr, e);
            throw e;
        }
    }

    /**
     * Deleting {@link ResultHistory} instances from DB
     * @param arr identifiers resultHistories to be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    public int deleteSelectedCollectionResults(int[] arr) {

        try {
            return resultHistoryMapper.deleteSelectedCollectionResults(arr);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete result instances, results id: " + arr, e);
            throw e;
        }
    }

    /**
     * Selecting max runId from ResultHistory table
     * @return max number on runId
     * @throws DataAccessException
     */
    public int getMaxId() {

        try {
            return resultHistoryMapper.getMaxId();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to retreive Max id: " + e);
            throw e;
        }
    }

    /**
     * Saving DbValidationHistories, headerHistories and labels for the resultHistory instance to DB
     * @param resultHistory
     */
    public void saveResultHistoryComponents(ResultHistory resultHistory) {

        List<DbValidationHistory> dbValidationHistories = resultHistory.getDbValidationHistories();
        if (CollectionUtils.isNotEmpty(dbValidationHistories)) {
            dbValidationHistoryMapper.saveByResultHistory(resultHistory);
        }

        List<HeaderHistory> headerHistories = resultHistory.getHeaderHistories();
        if (CollectionUtils.isNotEmpty(headerHistories)) {
            headerHistoryMapper.saveByResultHistory(resultHistory);
        }

        List<Label> labels = resultHistory.getLabels();
        if (CollectionUtils.isNotEmpty(labels)) {
            labelMapper.saveByResultHistory(resultHistory);
        }
    }

    /**
     * Saving {@link HeaderHistory} instance to DB
     * @param headerHistory {@link HeaderHistory} instance should be saved in DB
     * @return id of saved headerHistory instance
     * @throws DataAccessException
     */

    public void saveHeaderHistory(HeaderHistory headerHistory) {

        try {
            if (headerHistory != null) {
                headerHistoryMapper.save(headerHistory);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save resultHistory instance ", e);
            throw e;
        }
    }

    /**
     * Saving {@link EnvironmentHistory} instance to DB
     * @param environmentHistory {@link EnvironmentHistory} instance should be saved in DB
     * @return id of saved environmentHistory instance
     * @throws DataAccessException
     */
    public void saveEnvironmentHistory(EnvironmentHistory environmentHistory) {

        try {
            if (environmentHistory != null) {
                environmentHistoryMapper.save(environmentHistory);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save environmentHistory instance ", e);
            throw e;
        }
    }
    
    public void saveDbValidationHistory(DbValidationHistory dbValidationHistory) {
        try {
                dbValidationHistoryMapper.save(dbValidationHistory);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save environmentHistory instance ", e);
            throw e;
        }
    }
}