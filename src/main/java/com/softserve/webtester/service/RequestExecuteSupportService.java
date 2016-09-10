package com.softserve.webtester.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.Variable;

/**
 * RequestExecuteSupportService class handles common support methods for {@link RequestService},
 * {@link BuildHttpRequestService} and {@link ParseAndWriteService}
 */
@Service
public class RequestExecuteSupportService {

    private static final Logger LOGGER = Logger.getLogger(RequestExecuteSupportService.class);
    
    final static int NUMBER_OF_COLUMN = 1;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private EnvironmentService environmentService;

    /**
     * Get value from executed SQL query on given connection builded on Environment 
     * @param environment on which need to execute query and query
     * @return string value
     */
    public String getExecutedQueryValue(Environment environment, String sqlQuery) throws SQLException {
        String result = null;
        if (isSelectQuery(sqlQuery)) {
            try (Connection  dbCon = environmentService.getConnectionPools().get(environment).getConnection()) {
                Statement statement = dbCon.createStatement();
                ResultSet results = statement.executeQuery(sqlQuery);
                while (results.next()) {
                    result = results.getString(NUMBER_OF_COLUMN);
                }
            } catch (SQLException e) {
                LOGGER.error("Could not execute query: " + sqlQuery, e);
                throw e;
            }
        }
        return result;
    }

    private boolean isSelectQuery(String sqlQuery) {
        return sqlQuery.trim().toLowerCase().startsWith("select");
    }

    /**
     * Get evaluated a String containing Velocity Template Language using list of Variable, and returns the result as a String.
     * @param input instance should be formatted, data to build context and data for logging
     * @return formatted string object
     */
    public String getEvaluatedString (String source, List<Variable> varibleList, String logString){
        VelocityContext context = new VelocityContext();
        for (Variable var : varibleList) {
            context.put(var.getName(), var.getValue());
        }
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, logString, source);
        return writer.toString();
    }
    
    /**
     * Formats input XML or JSON instance.
     * 
     * @param input instance should be formatted
     * @return formatted string object
     */
    public String format(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        if (input.startsWith("<")) {
            return prettyFormatXML(input, 2);
        }
        if (input.startsWith("[") || input.startsWith("{")) {
            return prettyFormatJSON(input);
        }
        return input;
    }

    /**
     * Formats XML string
     * 
     * @param input XML string should be formatted
     * @param indent indent count
     * @return formatted string object
     */
    private String prettyFormatXML(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            LOGGER.warn("Unable to parse XML: " + input + System.lineSeparator() +  "XML will be ugly :(");
        }
        return input;
    }

    /**
     * Formats JSON string
     * 
     * @param input JSON string should be formatted
     * @return formatted string object
     */
    private String prettyFormatJSON(String input) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(input);
            return gson.toJson(je);
        } catch (JsonSyntaxException e) {
            LOGGER.warn("Unable to parse JSON: " + input + System.lineSeparator() + "JSON will be ugly :(");
        }
        return input;
    }
}