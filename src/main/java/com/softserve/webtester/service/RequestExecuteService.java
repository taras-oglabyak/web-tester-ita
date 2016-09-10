package com.softserve.webtester.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.softserve.webtester.dto.CollectionResultDTO;
import com.softserve.webtester.dto.PreparedRequestDTO;
import com.softserve.webtester.dto.RequestResultDTO;
import com.softserve.webtester.dto.ResponseDTO;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Request;

/**
 * Service, that responsible for executing one request and formation CollectionResultDTO object which includes results
 * of one collection run. We assume that requests' list is a collection with id = 0. And request is a list of requests
 * with one element.
 *
 * @author Anton Mykytiuk
 */
@Service
public class RequestExecuteService {

    private static final Logger LOGGER = Logger.getLogger(RequestExecuteService.class);

    @Autowired
    private BuildHttpRequestService buildHttpRequestService;

    @Value("${response.timeout.default}")
    private int defaultTimeout;

    @Value("${request.run.with.build.version.count}")
    private int sampleForBuildVersion;

    // response timeout for configure HttpClient
    int defaultTimeoutInMillis = defaultTimeout*1000;

    // HttpClient configuration for handling response timeout
    RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(defaultTimeoutInMillis)
            .setConnectionRequestTimeout(defaultTimeoutInMillis)
            .setSocketTimeout(defaultTimeoutInMillis)
            .build();

    /**
     * This method responsible for running request, requests' list or one collection.
     *
     * @param environment from here we get host name and DB connection for running request
     * @param requestList request list fot running
     * @param ifBuildVerExist trigger which tell us run collection with buildVersion or not
     * @param collectionId for getting list of requests from DB
     * @return CollectionResultDTO, object which consists of collection id, list of RequestResultDTO
     */
    public CollectionResultDTO executeRequests(Environment environment, List<Request> requestList,
            boolean ifBuildVerExist, int collectionId) {

        CollectionResultDTO collectionResultDTO = new CollectionResultDTO();
        collectionResultDTO.setCollectionId(collectionId);

        List<RequestResultDTO> requestResultDTOList = new ArrayList<>();

        for (Request request : requestList) {

            RequestResultDTO requestResultDTO = new RequestResultDTO();

            try {
                PreparedRequestDTO preparedRequestDTO = buildHttpRequestService.getHttpRequest(request, environment);
                HttpRequestBase requestBase = preparedRequestDTO.getHttpRequest();

                List<ResponseDTO> responseDTOList = new ArrayList<>();

                // If we run collections with build version, we execute every request (sampleForBuildVersion) times.
                // Value of (sampleForBuildVersion) stored in app.properties.
                if (ifBuildVerExist) {
                    for (int i = 0; i < sampleForBuildVersion; i++) {
                        ResponseDTO responseDTO = executeOneRequest(requestBase);
                        responseDTOList.add(responseDTO);
                    }
                } else {
                    ResponseDTO responseDTO = executeOneRequest(requestBase);
                    responseDTOList.add(responseDTO);
                }

                requestResultDTO.setRequest(request);
                requestResultDTO.setPreparedRequestDTO(preparedRequestDTO);
                requestResultDTO.setResponses(responseDTOList);

                requestResultDTOList.add(requestResultDTO);
            } catch (Exception e) {
                LOGGER.error("Cannot prepare request for sending: " + e.getMessage());

                requestResultDTO.setRequest(request);
                requestResultDTO.setPreparedRequestDTO(null);
                requestResultDTO.setResponses(null); // TODO RZ: Check this in you functionality
            }
        }

        collectionResultDTO.setRequestResultDTOList(requestResultDTOList);

        return collectionResultDTO;
    }

    /**
     * Method responsible for running one request
     *
     * @param requestBase interface for different types of request
     * @return ResponseDTO, object, which consists of response time and response
     */
    private ResponseDTO executeOneRequest(HttpRequestBase requestBase) {

        // Create auto closeable httpClient - base for executing request.
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build()) {

            long start = System.currentTimeMillis();

            // Running request.
            try (CloseableHttpResponse response = httpClient.execute(requestBase)) {

                ResponseDTO responseDTO = new ResponseDTO();

                // Calculating response time.
                // Do not expect to have more than 2147483647, because max response timeout is 60000.
                int responseTime = (int) (System.currentTimeMillis() - start);

                int statusCode = response.getStatusLine().getStatusCode();

                responseDTO.setResponseTime(responseTime);
                responseDTO.setStatusLine(response.getStatusLine().toString());
                responseDTO.setStatusCode(statusCode);
                responseDTO.setReasonPhrase(response.getStatusLine().getReasonPhrase());

                // If request's run success, try to get response body from response
                if ((statusCode >= 200) && (statusCode < 400)) {

                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        InputStream inputStream = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        try {
                            String responseBody = reader.readLine();
                            responseDTO.setResponseBody(responseBody);
                        } catch (IOException e) {
                            LOGGER.error("Can't read response body. " + e.getMessage(), e);
                        } finally {
                            reader.close();
                            inputStream.close();
                        }
                    }
                }

                return responseDTO;

            } catch (IOException e) {
                LOGGER.error("Exception occurred during request sending. " + e.getMessage(), e);
            }

        } catch (IOException e) {
            LOGGER.error("Exception occurred during HttpClient creating. " + e.getMessage(), e);
        }

        return null;
    }
}