package com.softserve.webtester.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.softserve.webtester.dto.CollectionResultDTO;
import com.softserve.webtester.dto.PreparedRequestDTO;
import com.softserve.webtester.dto.RequestResultDTO;
import com.softserve.webtester.dto.ResponseDTO;
import com.softserve.webtester.dto.ResultsDTO;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.DbValidation;
import com.softserve.webtester.model.DbValidationHistory;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.EnvironmentHistory;
import com.softserve.webtester.model.Header;
import com.softserve.webtester.model.HeaderHistory;
import com.softserve.webtester.model.Label;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.model.RequestMethod;
import com.softserve.webtester.model.ResultHistory;
import com.softserve.webtester.model.Variable;

/**
 * ParseAndWriteService class provides handling and saving {@link Request},
 * {@link RequestCollection} and {@link BuildVersion} results.
 * 
 * @author Roman Zolotar
 * @version 1.0
 */

@Service
public class ParseAndWriteService {

    private static final Logger LOGGER = Logger.getLogger(ParseAndWriteService.class);

    @Autowired
    private RequestExecuteSupportService requestExecuteSupportService;

    @Autowired
    private ResultHistoryService resultHistoryService;

    @Autowired
    private RequestCollectionService requestCollectionService;

    @Autowired
    private MetaDataService metaDataService;

    private static String VELOCITY_LOG = "";
    final static int REQUEST_RUN_COUNT = 5;
    final static int SUCCESS_CODE = 200;
    final static int FAIL_CODE = 400;

    /**
     * This method provides saving into DB {@link Request} results and
     * {@link RequestCollection} results if exists.
     * 
     * @param {@link
     *            ResultsDTO}
     * @return runId
     */

    public int parseAndWrite(ResultsDTO resultsDTO) {

        int runId = resultsDTO.getRunId();

        List<CollectionResultDTO> collectionResultDTOList = resultsDTO.getCollectionResultDTOList();
        ResultHistory resultHistory = new ResultHistory();
        EnvironmentHistory environmentHistory = new EnvironmentHistory();
        HeaderHistory headerHistory = new HeaderHistory();
        DbValidationHistory dbValidationHistory = new DbValidationHistory();

        for (CollectionResultDTO collectionList : collectionResultDTOList) {
            int collectionId = collectionList.getCollectionId();
            List<RequestResultDTO> requestResultDTOList = collectionList.getRequestResultDTOList();

            for (RequestResultDTO requestResult : requestResultDTOList) {
                Request request = requestResult.getRequest();
                PreparedRequestDTO preparedRequestDTO = requestResult.getPreparedRequestDTO();
                List<ResponseDTO> responseDTOList = requestResult.getResponses();

                savingResultHistory(request, resultHistory, preparedRequestDTO, resultsDTO, responseDTOList,
                        collectionId, dbValidationHistory);
                savingEnvironmentHistory(environmentHistory, resultHistory, resultsDTO);
                savingHeaderHistory(request, headerHistory, resultHistory);
                savingLabelHistory(request, resultHistory);
            }
        }
        return runId;
    }

    /**
     * This method provides forming {@link ResultHistory} object and saving it
     * into DB. Also it handles {@link DuildVersion} run results.
     * 
     * @param {@link Request}
     * @param {@link ResultHistory}
     * @param {@link DbValidationHistory}
     * @param {@link ResultsDTO}
     * @param responseDTOList
     * @param collectionId
     * @param preparedRequestDTO
     */

    public void savingResultHistory(Request request, ResultHistory resultHistory, PreparedRequestDTO preparedRequestDTO,
            ResultsDTO resultsDTO, List<ResponseDTO> responseDTOList, int collectionId,
            DbValidationHistory dbValidationHistory) {

        Environment environment = resultsDTO.getEnvironment();
        
        boolean statusIndicator = false;
        int buildVersionId = resultsDTO.getBuildVersionId();

        if (buildVersionId != 0) {
            int responsesCount = 0;
            int timeResponsesSum = 0;
            boolean bodyCheck = true;
            for (ResponseDTO responseDTOListElement : responseDTOList) {

                // check if request run was successful
                int statusCode = responseDTOListElement.getStatusCode();
                boolean checkStatusCode = (statusCode >= SUCCESS_CODE) && (statusCode < FAIL_CODE);

                // check if response body equals to expected one
                String responseBody = (requestExecuteSupportService.format(responseDTOListElement.getResponseBody()));
                boolean checkResponseBodyInstance = responseBody
                        .equals(request.getExpectedResponse());

                if (checkStatusCode) {
                    timeResponsesSum += responseDTOListElement.getResponseTime();
                    responsesCount++;
                    if (!checkResponseBodyInstance) {
                        bodyCheck = false;
                    }
                }

                resultHistory.setStatusLine(responseDTOListElement.getStatusLine());
                resultHistory.setActualResponse(requestExecuteSupportService.format(responseBody));

            }

            // calculating average response time for request with buildVersion
            // run
            if (responsesCount != 0) {
                resultHistory.setResponseTime(timeResponsesSum / responsesCount);
            } else {
                resultHistory.setResponseTime(0);
            }

            statusIndicator = ((responsesCount == REQUEST_RUN_COUNT) && (bodyCheck));
        } else {
            ResponseDTO responseDTO = responseDTOList.get(0);
            resultHistory.setResponseTime(responseDTO.getResponseTime());
            resultHistory.setStatusLine(responseDTO.getStatusLine());
            resultHistory.setActualResponse(requestExecuteSupportService.format(responseDTO.getResponseBody()));
            statusIndicator = (responseDTO.getStatusCode() == SUCCESS_CODE);
        }

        resultHistory.setApplication(request.getApplication());
        resultHistory.setService(request.getService());
        resultHistory.setRequest(request);
        resultHistory.setRequestName(request.getName());
        resultHistory.setRequestDescription(request.getDescription());
        resultHistory.setUrl(preparedRequestDTO.getHttpRequest().getURI().toString());
        resultHistory.setResponseType(request.getResponseType().getTextValue());
        resultHistory.setTimeStart(new Timestamp(System.currentTimeMillis()));
        resultHistory.setExpectedResponseTime(request.getTimeout());

        if ((preparedRequestDTO.getHttpRequest().getMethod().equals("GET"))
                ^ (preparedRequestDTO.getHttpRequest().getMethod().equals("DELETE"))) {
            resultHistory.setRequestBody(null);

        } else {
            resultHistory
                    .setRequestBody(preparedRequestDTO.getPreparedRequestBody());
        }

        try {
            if (request.getExpectedResponse().contains("STUB")) {
                if (StringUtils.isNotEmpty(resultHistory.getActualResponse())) {
                    request.setExpectedResponse(request.getExpectedResponse().replace("STUB_VALUE",
                            getActualIdFromResponseBody(resultHistory)));
                }
            }
            if (CollectionUtils.isNotEmpty(preparedRequestDTO.getVariableList())) {
                String expectedResponse = requestExecuteSupportService.getEvaluatedString(
                        request.getExpectedResponse(), preparedRequestDTO.getVariableList(), VELOCITY_LOG);
                
                resultHistory.setExpectedResponse(expectedResponse);
            } else
                resultHistory.setExpectedResponse(request.getExpectedResponse());

        } catch (Exception e) {
            LOGGER.error("Unable to add variebles to expected response:", e);
        }

        resultHistory.setRunId(resultsDTO.getRunId());

        if (collectionId != 0) {
            RequestCollection reqColl = requestCollectionService.load(collectionId);
            resultHistory.setRequestCollection(reqColl);
        }
        if (buildVersionId != 0) {
            BuildVersion bVersion = metaDataService.loadBuildVersionById(buildVersionId);
            resultHistory.setBuildVersion(bVersion);
        }

        StringBuilder validationMessages = getValidationMessages(resultHistory, request, preparedRequestDTO,
                dbValidationHistory, responseDTOList);
        resultHistory.setStatus(statusIndicator && isValidResponse(validationMessages));
        resultHistory.setMessage(validationMessages.toString());
        resultHistoryService.save(resultHistory);
        savingDbValidation(request, environment, resultHistory, dbValidationHistory, preparedRequestDTO);
    }

    /**
     * This method provides forming {@link EnvironmentHistory} object and saving
     * it into DB.
     * 
     * @param {@link EnvironmentHistory}
     * @param {@link ResultHistory}
     * @param {@link ResultsDTO}
     */

    public void savingEnvironmentHistory(EnvironmentHistory environmentHistory, ResultHistory resultHistory,
            ResultsDTO resultsDTO) {

        Environment environment = resultsDTO.getEnvironment();

        environmentHistory.setResultHistory(resultHistory);
        environmentHistory.setBaseURL(environment.getBaseUrl());
        environmentHistory.setDbName(environment.getDbName());
        environmentHistory.setDbPort(environment.getDbPort());
        environmentHistory.setDbURL(environment.getDbUrl());
        environmentHistory.setName(environment.getName());
        environmentHistory.setEnvironment(environment);
        resultHistoryService.saveEnvironmentHistory(environmentHistory);
    }

    /**
     * This method provides forming {@link HeaderHistory} object (if
     * {@link Header} exists) and saving it into DB.
     * 
     * @param {@link Request}
     * @param {@link HeaderHistory}
     * @param {@link ResultHistory}
     */

    public void savingHeaderHistory(Request request, HeaderHistory headerHistory, ResultHistory resultHistory) {

        if (request.getHeaders() != null) {
            List<Header> headers = request.getHeaders();
            for (Header header : headers) {
                headerHistory.setName(header.getName());
                headerHistory.setValue(header.getValue());
                headerHistory.setResultHistory(resultHistory);
                resultHistoryService.saveHeaderHistory(headerHistory);
            }
        }
    }

    /**
     * This method provides forming {@link Label} object (if exists), passes it
     * to {@link ResultHistory} and saving it into DB.
     * 
     * @param {@link Request}
     * @param {@link ResultHistory}
     */

    public void savingLabelHistory(Request request, ResultHistory resultHistory) {

        List<Label> labels = (request.getLabels());
        resultHistory.setLabels(labels);
        if (resultHistory.getLabels() != null) {
            resultHistoryService.saveResultHistoryComponents(resultHistory);
        }
    }

    /**
     * This method provides forming {@link DbValidationHistory} object if
     * {@link DbValidation} exists and saving it into DB. Also it handles
     * executing query for DbValidation and adds {@link Variable} to
     * DbValidation
     * 
     * @param {@link Request}
     * @param {@link Environment}
     * @param {@link ResultHistory}
     * @param {@link dbValidationHistory}
     * @param preparedRequestDTO
     */

    public void savingDbValidation(Request request, Environment environment, ResultHistory resultHistory,
            DbValidationHistory dbValidationHistory, PreparedRequestDTO preparedRequestDTO) {

        if (CollectionUtils.isNotEmpty(request.getDbValidations())) {
            List<DbValidation> dbValidations = (request.getDbValidations());
            for (DbValidation dbValidation : dbValidations) {
                String dbValidationSQLQuery = dbValidation.getSqlQuery();
                List<Variable> varibleList = preparedRequestDTO.getVariableList();

                if (CollectionUtils.isNotEmpty(preparedRequestDTO.getVariableList())) {
                    dbValidationSQLQuery = requestExecuteSupportService.getEvaluatedString(dbValidationSQLQuery,
                            varibleList, VELOCITY_LOG);
                } else {
                    dbValidationSQLQuery = dbValidation.getSqlQuery();
                }

                dbValidationHistory.setSqlQuery(dbValidationSQLQuery);
                dbValidationHistory.setExpectedValue(dbValidation.getExpectedValue());
                try {
                    if ((StringUtils.isNotEmpty(dbValidationSQLQuery))) {
                        dbValidationHistory.setActualValue(
                                requestExecuteSupportService.getExecutedQueryValue(environment, dbValidationSQLQuery));
                        if(!(dbValidationHistory.getActualValue().equals(dbValidationHistory.getExpectedValue()))){
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Unable to execute SQL Query:  " + dbValidationSQLQuery);
                    e.printStackTrace();
                    // TODO RZ: set NULL and message to the validation
                    // messages
                }

                dbValidationHistory.setResultHistory(resultHistory);
                if (CollectionUtils.isNotEmpty(request.getDbValidations())) {
                    dbValidationHistory.setResultHistory(resultHistory);
                    resultHistoryService.saveDbValidationHistory(dbValidationHistory);
                }
            }
        }

    }

    private boolean isValidResponse(StringBuilder validationMessages) {

        return validationMessages.toString().equals("OK");
    }

    /**
     * This method generates error(s) messages if error exists and appends it to
     * StringBuilder-object. Also it detects type of expected response.
     * 
     * @param {@link ResultHistory}
     * @param {@link Request}
     * @param {@link dbValidationHistory}
     * @param preparedRequestDTO
     * @return message object
     */

    public StringBuilder getValidationMessages(ResultHistory resultHistory, Request request,
            PreparedRequestDTO preparedRequestDTO, DbValidationHistory dbValidationHistory, List<ResponseDTO> responseDTOList) {
        
        ResponseDTO responseDTO = responseDTOList.get(0);

        StringBuilder message = new StringBuilder();
        String type = null;
        if (resultHistory.getExpectedResponse().startsWith("<")) {
            type = "XML";
        }
        if (resultHistory.getExpectedResponse().startsWith(("["))
                || resultHistory.getExpectedResponse().startsWith(("{"))) {
            type = "JSON";
        }
        if (responseDTO.getStatusCode() == 404){
            message.append("Invalid URL; ");
        }

        if (preparedRequestDTO.getHttpRequest().getMethod().equals(RequestMethod.DELETE.name())) {
            message.append("Unsupported request method; ");
        }
        if (!type.equalsIgnoreCase(request.getResponseType().getTextValue())) {
            message.append("Wrong content type; ");
        }
        /*if(!(dbValidationHistory.getActualValue().equals(dbValidationHistory.getExpectedValue()))){
            message.append("At least one db validation expected value is not equal to the actual one; ");
        }*/
        if (!resultHistory.getExpectedResponse().equalsIgnoreCase(resultHistory.getActualResponse())
                && (RequestMethod.GET.name().equalsIgnoreCase("GET"))) {
            message.append("Actual response does not match the expected one; ");
        }
        if (resultHistory.getResponseTime() > resultHistory.getExpectedResponseTime()) {
            message.append("Response time exceeded timeout value; ");
        }

        if (StringUtils.isBlank(message)) {
            message.append("OK");
        }
        return message;
    }

    /**
     * This method provides detecting response type and changes "stub value"
     * instead "id" in expected response on actual "id" from actual response 
     * corresponding to response type
     * 
     * @param {@link ResultHistory}
     * @return actualId
     */

    public String getActualIdFromResponseBody(ResultHistory resultHistory) {

        String input = resultHistory.getActualResponse();
        String actualId = null;
        if (((resultHistory.getActualResponse()) != null) && !(resultHistory.getActualResponse().startsWith("<"))) {
            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(input).getAsJsonObject();
            actualId = mainObject.get("id").getAsString();
        }
        if (((resultHistory.getActualResponse()) != null) && (resultHistory.getActualResponse().startsWith("<"))) {
            // String id = null;
            Pattern p = Pattern.compile("<id>\\d+</id>");
            Matcher m = p.matcher(resultHistory.getActualResponse());
            while (m.find()) {
                Pattern pp = Pattern.compile("\\d+");
                Matcher mm = pp.matcher(m.group());
                while (mm.find()) {
                    actualId = mm.group();
                }
            }

        }
        return actualId;

    }
}