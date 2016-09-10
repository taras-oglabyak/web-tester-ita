package com.softserve.webtester.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.webtester.dto.RequestFilterDTO;
import com.softserve.webtester.exception.ResourceNotFoundException;
import com.softserve.webtester.mapper.DbValidationMapper;
import com.softserve.webtester.mapper.HeaderMapper;
import com.softserve.webtester.mapper.LabelMapper;
import com.softserve.webtester.mapper.RequestMapper;
import com.softserve.webtester.mapper.VariableMapper;
import com.softserve.webtester.model.Request;

/**
 * RequestService class implements CRUD operation on {@link Request} instance in the database.<br>
 * The service uses Spring DataSourceTransactionManager for managing transaction with the database and log4j for
 * logging.
 * 
 * @author Taras Oglabyak
 */
@Service
public class RequestService {

    private static final Logger LOGGER = Logger.getLogger(RequestService.class);

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private DbValidationMapper dbValidationMapper;

    @Autowired
    private HeaderMapper headerMapper;

    @Autowired
    private VariableMapper variableMapper;

    @Autowired
    private LabelMapper labelMapper;
    
    @Autowired
    private RequestExecuteSupportService requestExecuteSupportService;

    @Value("${request.timeout.default:25}")
    private int defaultTimeout;

    @Value("${request.name.duplicate.suffix:_duplicate}")
    private String duplicateSuffix;

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    /**
     * Saves {@link Request} instance to the database.
     * 
     * @param request {@link Request} instance should be saved in the database
     * @return id of saved request
     * @throws DuplicateKeyException if the request with the name exists in the database.
     * @throws DataAccessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int save(Request request) {
        try {

            // format Request body field and expected response field
            formatRequestBody(request);

            // Save request instance to Request table
            requestMapper.save(request);
            int id = request.getId();

            // Save all dbValidations, variables, headers and labels for the request instance to the database
            saveRequestComponents(request);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save request instance, request id: " + request.getId(), e);
            throw e;
        }
    }
    


    /**
     * Loads {@link Request} instance with headers, dbValidations, labels and variables.
     * 
     * @param id identifier of Request instance
     * @return {@link Request} instance
     * @throws ResourceNotFoundException if Request instance is null
     * @throws DataAccessException
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
    public Request load(int id) {
        try {
            Request request = requestMapper.load(id);
            if (request == null)
                throw new ResourceNotFoundException("Request not found, id: " + id);
            return request;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load request instance, request id: " + id, e);
            throw e;
        }
    }

    /**
     * Loads all stored {@link Request} instances with their main information.
     * 
     * @return List of {@link Request} instances
     * @throws DataAccessException
     */
    public List<Request> loadAll() {
        return loadAll(null, null, null, null);
    }

    /**
     * Loads filtered {@link Request} instances with their main information.
     * 
     * @param requestFilterDTO DTO object using for filtering {@link Request} instance
     * @return List of {@link Request} instances
     * @throws DataAccessException
     */
    public List<Request> loadAll(RequestFilterDTO requestFilterDTO) {
        return loadAll(requestFilterDTO.getRequestNameFilter(),
                       requestFilterDTO.getApplicationFilter(),
                       requestFilterDTO.getServiceFilter(),
                       requestFilterDTO.getLabelFilter());
    }

    /**
     * Loads filtered {@link Request} instances from the database.<br>
     * This method loads only main information about request instance.
     * 
     * @param requestNameFilter using for filtering instances, which name starts with the parameter
     * @param applicationFilter using for filtering instances, which application identifier are in the array
     * @param serviceFilter using for filtering instances, which service identifier are in the array
     * @param labelFilter using for filtering instances, which labels identifier are in the array
     * @return List of Request instances
     * @throws DataAccessException
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
    public List<Request> loadAll(String requestNameFilter, int[] applicationFilter, int[] serviceFilter,
                                 int[] labelFilter) {
        try {
            return requestMapper.loadAll(requestNameFilter, applicationFilter, serviceFilter, labelFilter);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load request instances", e);
            throw e;
        }
    }

    /**
     * Updates {@link Request} instance in the database.
     * 
     * @param request {@link Request} instance to be saved
     * @return the number of rows affected by the statement
     * @throws DuplicateKeyException if the request with the name exists in the database.
     * @throws DataAccessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int update(Request request) {
        try {
            int id = request.getId();

            // format Request body field and expected response field
            formatRequestBody(request);

            // Update request instance in Request table
            requestMapper.update(request);

            // Delete all dbValidations, variables, headers and labels for the request instance in the database
            dbValidationMapper.deleteByRequestId(id);
            variableMapper.deleteByRequestId(id);
            headerMapper.deleteByRequestId(id);
            labelMapper.deleteByRequestId(id);

            // Save all dbValidations, variables, headers and labels for the request instance to the database
            saveRequestComponents(request);
            LOGGER.info("Successfully updated request instance in the database, request id: " + id);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update request instance, request id: " + request.getId(), e);
            throw e;
        }
    }

    /**
     * Deletes {@link Request} instance from the database.
     * 
     * @param id identifier of request to delete
     * @return the number of rows affected by the statement
     * @throws DataAccessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int delete(int id) {
        try {
            return requestMapper.delete(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete request instance, request id: " + id, e);
            throw e;
        }
    }

    /**
     * Deletes {@link Request} instances from the database.
     * 
     * @param requestIdArray identifiers of request to delete
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public int delete(int[] requestIdArray) {
        try {
            return requestMapper.deleteRequests(requestIdArray);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete request instances, requests id: " + requestIdArray, e);
            throw e;
        }
    }

    /**
     * Checks the unique of request's name.
     * 
     * @param name name of {@link Request} should be checked
     * @param exclusionId id of {@link Request} should be excluded
     * @return true, if name is unique
     * @throws DataAccessException
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean isRequestNameFree(String name, int exclusionId) {
        try {
            return requestMapper.isRequestNameFree(name, exclusionId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check request name, requests name: " + name, e);
            throw e;
        }
    }

    /**
     * Creates duplicate existing {@link Request}
     * 
     * @param fromId identifier of Request should be duplicated
     * @return duplicate of existing Request instance
     * @throws DataAccessException
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
    public Request createDuplicate(int fromId) {
        try {
            Request request = load(fromId);
            request.setId(0);
            request.getHeaders().forEach(i -> i.setId(0));
            request.getVariables().forEach(i -> i.setId(0));
            request.getDbValidations().forEach(i -> i.setId(0));
            request.setName(request.getName() + duplicateSuffix);
            return request;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to duplicate the request, requests id: " + fromId, e);
            throw e;
        }
    }

    /**
     * Saves all dbValidations, variables, headers and labels for the request instance to the database.
     */
    private void saveRequestComponents(Request request) {
        if (CollectionUtils.isNotEmpty(request.getDbValidations())) {
            dbValidationMapper.saveByRequest(request);
        }
        if (CollectionUtils.isNotEmpty(request.getVariables())) {
            variableMapper.saveByRequest(request);
        }
        if (CollectionUtils.isNotEmpty(request.getHeaders())) {
            headerMapper.saveByRequest(request);
        }
        if (CollectionUtils.isNotEmpty(request.getLabels())) {
            labelMapper.saveByRequest(request);
        }
    }

    /**
     * loads request list from DB
     *
     * @param requestIdArray identifiers of requests to get
     * @return list of request instances
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
    public List<Request> loadArray(int[] requestIdArray) {
        try {
            return requestMapper.loadArray(requestIdArray);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load request instances", e);
            throw e;
        }
    }


    /**
     * loads request list from DB
     *
     * @param id identifiers of collection which contains list of requests
     * @return list of request instances
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_COMMITTED)
    public List<Request> loadFullRequestsByRequestCollectionId(int id) {
        try {
            return requestMapper.loadFullRequestsByRequestCollectionId(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load request instances", e);
            throw e;
        }
    }
    
    /**
     * Formats {@link Request} body field and expected response field
     * 
     * @param request {@link Request} instance, which fields should be formatted
     */
    private void formatRequestBody(Request request) {
        request.setRequestBody(requestExecuteSupportService.format(request.getRequestBody()));
        request.setExpectedResponse(requestExecuteSupportService.format(request.getExpectedResponse()));
    }
}