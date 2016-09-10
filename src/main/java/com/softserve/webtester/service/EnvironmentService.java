package com.softserve.webtester.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.DataSources;
import com.softserve.webtester.exception.ResourceNotFoundException;
import com.softserve.webtester.mapper.EnvironmentMapper;
import com.softserve.webtester.model.Environment;

/**
 * EnvironmentService class implements CRUD operations on Environment instance
 * in the database
 *
 */
@Service
public class EnvironmentService {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentService.class);

    //Override default configuration parameters for static factory class DataSources
    private static final String MAX_POOL_SIZE_STRING = "maxPoolSize";
    private static final int MAX_POOL_SIZE_VALUE = 20;
    private static final String ACQUIRE_RETRY_ATTEMPTS_STRING = "acquireRetryAttempts";
    private static final int ACQUIRE_RETRY_ATTEMPTS_VALUE = 1;
    private static final String TEST_CONNECTION_ON_CHECKIN_STRING = "testConnectionOnCheckin";
    private static final boolean TEST_CONNECTION_ON_CHECKIN_VALUE = true;
    private static final String PREFERRED_TEST_QUERY_STRING = "preferredTestQuery";

    /**
     * Handle connection pools to available DBs. Key is {@link Environment} instance which used for
     * creating connection pool
     */
    private Map<Environment, DataSource> connectionPools = new HashMap<Environment, DataSource>();

    @Value("${environment.timemultiplier.default}")
    private float defaultTimeMultiplier;

    @Autowired
    private EnvironmentMapper environmentMapper;

    public float getDefaultTimeMultiplier() {
        return defaultTimeMultiplier;
    }

    public Map<Environment, DataSource> getConnectionPools() {
        return connectionPools;
    }

    /**
     * Selects {@link Environment} entity from database
     * 
     * @param id identifier of Environment instance
     * @return Environment instance
     * @throws DataAccessException
     */
    public Environment load(int id) {
        try {
            Environment environment = environmentMapper.load(id);
            if (environment == null)
                throw new ResourceNotFoundException("Environment not found, id: " + id);
            return environment;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load environment instance, environment id: " + id, e);
            throw e;
        }
    }

    /**
     * Selects all {@link Environment} entities not marked as "deleted" from database
     * 
     * @return List of Environment instances
     * @throws DataAccessException
     */
    public List<Environment> loadAll() {
        try {
            return environmentMapper.loadAll();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load environment instances ", e);
            throw e;
        }
    }

    /**
     * Saves {@link Environment} instance to database
     * 
     * @param Environment instance should be stored
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    public int save(Environment environment) {
        try {
            environmentMapper.save(environment);
            return environment.getId();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save environment instance, id " + environment.getId(), e);
            throw e;
        }
    }

    /**
     * Updates {@link Environment} instance in the database
     * 
     * @param Environment instance should be updated
     * @return the number of rows affected by the statement
     * @throws DataAccessException
     */
    public int update(Environment environment) {
        try {
            environmentMapper.update(environment);
            return environment.getId();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update environment instance, id " + environment.getId(), e);
            throw e;
        }
    }

    /**
     * Deletes {@link Environment} instance from database
     * 
     * @param Environment instance should be deleted
     * @return the number of rows affected by the statement
     * @throws DataAccessException
     */
    public int delete(Environment environment) {
        try {
            environmentMapper.delete(environment);
            return environment.getId();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete environment instance, id " + environment.getId(), e);
            throw e;
        }
    }

    /**
     * Checks the unique of {@link Environment} property "name"
     * 
     * @param Environment instance should be checked
     * @return count of entities with the same property "name"
     * @throws DataAccessException
     */
    public int isNameFree(Environment environment) {
        try {
            return environmentMapper.isNameFree(environment);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check environment name, environments name: " + environment.getName(), e);
            throw e;
        }
    }

    /**
     * Provides creating pooled {@link DataSource} instance using {@link Environment} instance
     * @throws Exception
     */
    private DataSource getPooledDataSource(Environment environment) throws Exception {
        String jdbcDriver = environment.getDbType().getDbDriver();
        String connectionUrl = String.format(environment.getDbType().getConnectionPattern(), environment.getDbUrl(),
                environment.getDbPort(), environment.getDbName(), environment.getDbUsername(),
                environment.getDbPassword());
        Class.forName(jdbcDriver);
        DataSource ds_unpooled = DataSources.unpooledDataSource(connectionUrl);

        Map<String, Object> overrides = new HashMap<>();
        overrides.put(MAX_POOL_SIZE_STRING, MAX_POOL_SIZE_VALUE);
        overrides.put(ACQUIRE_RETRY_ATTEMPTS_STRING, ACQUIRE_RETRY_ATTEMPTS_VALUE);
        overrides.put(TEST_CONNECTION_ON_CHECKIN_STRING, TEST_CONNECTION_ON_CHECKIN_VALUE);
        overrides.put(PREFERRED_TEST_QUERY_STRING, environment.getDbType().getCheckSql());

        DataSource ds_pooled = DataSources.pooledDataSource(ds_unpooled, overrides);

        return ds_pooled;
    }
    
    /**
     * Initializes connection pool to all available DBs at start application which are handled
     * in {@link connectionPools}
     */
    @PostConstruct
    public void initConnectionPools() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Environment> environmentList = loadAll();
                for (Environment environment : environmentList) {
                    try {
                        DataSource dataSource = getPooledDataSource(environment);

                        if (dataSource.getConnection().isValid(1)) {
                            connectionPools.put(environment, dataSource);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Unable to create initialized connection pool for environment, id "
                                + environment.getId(), e);
                    }
                }
            }
        });
        thread.start();
    }
    
    /**
     * Initializes connection pool to DB using {@link Environment} instance
     * @throws Exception
     */
    private void initConnectionPool(Environment environment) throws Exception {
        try {
            DataSource dataSource = getPooledDataSource(environment);
            if (dataSource.getConnection().isValid(0)) {
                connectionPools.put(environment, dataSource);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create connection pool for environment, id " + environment.getId(), e);
            throw e;
        }
    }

    /**
     * Checks established connection to DB based on {@link Environment} parameters
     * 
     * @param environment which connection should be checked
     * @throws Exception
     */
    public String checkConnection(Environment environment) throws Exception {
        try {
            if (!connectionPools.containsKey(environment)) {
                initConnectionPool(environment);
            }
        } catch (Exception e) {
            throw (Exception)ExceptionUtils.getRootCause(e);
        }

        Statement statement = null;
        String result = null;

        try (Connection connection = getConnectionPools().get(environment).getConnection()) {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(environment.getDbType().getCheckSql());
            while (rs.next()) {
                result = rs.getString(1);
            }
        } catch (SQLException se) {
            throw se; 
        }
        return result;
    }
}
