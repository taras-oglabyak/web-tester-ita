package com.softserve.webtester.service;

import java.util.List;

import com.softserve.webtester.exception.ResourceNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.webtester.mapper.ApplicationMapper;
import com.softserve.webtester.mapper.BuildVersionMapper;
import com.softserve.webtester.mapper.LabelMapper;
import com.softserve.webtester.mapper.ServiceMapper;
import com.softserve.webtester.model.Application;
import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.model.Label;
import com.softserve.webtester.model.Service;

/**
 * MetaDataService class implements CRUD operation on {@link Application},
 * {@link Service}, {@link BuildVersion} and {@link Label} instances in the
 * database. The service uses Spring DataSourceTransactionManager for managing
 * transaction with the database and log4j for logging.
 * 
 * @author Roman Zolotar, Anton Mykytiuk
 */

@org.springframework.stereotype.Service
@Transactional
public class MetaDataService {

    private static final Logger LOGGER = Logger.getLogger(MetaDataService.class);

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private BuildVersionMapper buildVersionMapper;

    @Autowired
    private LabelMapper labelMapper;

    /**
     * Loads all {@link Application} instances from database
     * @return List of Application instances
     * @throws DataAccessException when there is no access to the DB
     */
    public List<Application> applicationLoadAll() {
        try {
            return applicationMapper.loadAll();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read Application table: ", e);
            throw e;
        }
    }
    
    /**
     * Loads all {@link Application} instances from DB which aren't marked as "deleted".
     * @return List of Application instances
     * @throws DataAccessException when there is no access to the DB
     */
    public List<Application> applicationLoadAllWithoutDeleted() {
        try {
            return applicationMapper.loadAllWithoutDeleted();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read Application table: ", e);
            throw e;
        }
    }

    /**
     * Loads {@link Application} instance from database
     * @param id of Application instance which stored in the DB
     * @return Application instance
     * @throws DataAccessException when there is no access to the DB
     */
    public Application applicationLoad(int id) {
        try {
            return applicationMapper.load(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read line from Application table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Updates {@link Application} instance in the DB.
     * @param Application instance which needs to updated
     * @throws DataAccessException when there is no access to the DB
     */
    public void applicationUpdate(Application application) {
        try {
            applicationMapper.update(application);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update line in Application table with next id: " + application.getId(), e);
            throw e;
        }
    }
    
    /**
     * Deletes {@link Application} instance from the DB.
     * @param id of Application instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    public void applicationDelete(int id) {
        try {
            applicationMapper.delete(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete line from Application table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Provides soft-delete {@link Application} instance
     * @param id of Application instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    public void applicationSoftDelete(int id) {
        try {
            Application application = applicationMapper.load(id);
            if (application != null) {
                application.setDeleted(true);
                applicationMapper.update(application);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete line from Application table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Saves {@link Application} instance to DB.
     * @param Application instance which needs to store in the DB
     * @throws DataAccessException when there is no access to the DB
     */
    public void applicationSave(Application application) {
        try {
            applicationMapper.save(application);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to create line in Application table: ", e);
            throw e;
        }
    }
    
    /**
     * Checks the unique of application's name.
     * @param name - name of {@link Application} which should be checked
     * @param exclusionId - id of {@link Application} that should be excluded
     * @return true, if name is unique
     * @throws DataAccessException when there is no access to the DB
     */
    @Transactional
    public boolean isApplicationNameFree(String name, int exclusionId) {
        try {
            return applicationMapper.isApplicationNameFree(name, exclusionId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check application's name " + name, e);
            throw e;
        }
    }

    /**
     * Loads all {@link Service} instance from DB.
     * @return List of Service instances
     * @throws DataAccessException when there is no access to the DB
     */
    public List<Service> serviceLoadAll() {
        try {
            return serviceMapper.loadAll();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read Service table: ", e);
            throw e;
        }
    }
    
    /**
     * Loads all {@link Service} instances from DB which aren't marked as "deleted".
     * @return List of Service instances
     * @throws DataAccessException when there is no access to the DB
     */
    public List<Service> serviceLoadAllWithoutDeleted() {
        try {
            return serviceMapper.loadAllWithoutDeleted();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read Service table: ", e);
            throw e;
        }
    }

    /**
     * Loads {@link Service} instance from DB by it's identifier.
     * @param id of Service instance which stored in the DB
     * @return service instance
     * @throws DataAccessException when there is no access to the DB
     */
    public Service serviceLoad(int id) {
        try {
            return serviceMapper.load(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to read line from Service table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Updates {@link Service} instance in the DB.
     * @param Service instance which needs to updated
     * @throws DataAccessException when there is no access to the DB
     */
    public void serviceUpdate(Service service) {
        try {
            serviceMapper.update(service);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update line in Service table with next id: " + service.getId(), e);
            throw e;
        }
    }

    /**
     * Deletes {@link Service} instance from the DB.
     * @param id of Service instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    public void serviceDelete(int id) {
        try {
            serviceMapper.delete(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete line from Service table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Provides Soft-delete {@link Service} instance
     * @param id of Service instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    public void serviceSoftDelete(int id) {
        try {
            Service service = serviceMapper.load(id);
            if (service != null) {
                service.setDeleted(true);
                serviceMapper.update(service);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete line from Service table with next id: " + id, e);
            throw e;
        }
    }

    /**
     * Saves {@link Service} instance to DB.
     * @param Service instance which needs to store in the DB
     * @throws DataAccessException when there is no access to the DB
     */
    public void serviceSave(Service service) {
        try {
            serviceMapper.save(service);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to create line in Service table: ", e);
            throw e;
        }
    }
    
    /**
     * Checks the unique of service's name.
     * @param name - name of {@link Service} which should be checked
     * @param exclusionId - id of {@link Service} that should be excluded
     * @return true, if name is unique
     * @throws DataAccessException when there is no access to the DB
     */
    @Transactional
    public boolean isServiceNameFree(String name, int exclusionId) {
        try {
            return serviceMapper.isServiceNameFree(name, exclusionId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check service's name, service name: " + name, e);
            throw e;
        }
    }

    // BUILD_VERSIONS

    /**
     * Saves {@link BuildVersion} instance to database.
     * 
     * @param buildVersion instance for saving in DB.
     * @return id of saved BuildVersion.
     * @throws DuplicateKeyException if the buildVersion with the same name exists in the database.
     * @throws DataAccessException  appear when there is no access to the DB.
     */
    @Transactional
    public int saveBuildVersion(BuildVersion buildVersion) {
        try {
            buildVersionMapper.saveBuildVersion(buildVersion);
            int id = buildVersion.getId();
            LOGGER.info("Successfully saved BuildVersion instance in the database, buildVersion id: " + id);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save BuildVersion instance, buildVersion id: " + buildVersion.getId(), e);
            throw e;
        }
    }

    /**
     * Loads {@link BuildVersion} instance from database.
     *
     * @param id identifier of Build Version stored in the DB.
     * @return {@link BuildVersion} instance.
     * @throws DataAccessException appear when there is no access to the DB.
     * @throws ResourceNotFoundException appear when there is no buildVersion with this id in the DB.
     */
    @Transactional
    public BuildVersion loadBuildVersionById(int id) {
        try {
            BuildVersion buildVersion = buildVersionMapper.loadBuildVersionById(id);
            if (buildVersion == null)
                throw new ResourceNotFoundException("Build Version not found, id: " + id);
            return buildVersion;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load BuildVersion instance, buildVersion id: " + id, e);
            throw e;
        }
    }

    /**
     * Loads all stored {@link BuildVersion} instances with their main information.
     * 
     * @return Set of {@link BuildVersion} instances.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public List<BuildVersion> loadAllBuildVersions() {
        try {
            return buildVersionMapper.loadAllBuildVersions();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load BuildVersion instances", e);
            throw e;
        }
    }

    /**
     * Updates {@link BuildVersion} instance should be updated in the database.
     * 
     * @param buildVersion instance that must be updated.
     * @return the number of rows affected by the statement.
     * @throws DuplicateKeyException if the buildVersion with the same name exists in the database.
     * @throws DataAccessException appear when there is no access to the DB.
     **/
    @Transactional
    public int updateBuildVersion(BuildVersion buildVersion) {
        try {
            buildVersionMapper.updateBuildVersion(buildVersion);
            int id = buildVersion.getId();
            LOGGER.info("Successfully updated BuildVersion instance in the database, buildVersion id: " + id);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update BuildVersion instance, buildVersion id " + buildVersion.getId(), e);
            throw e;
        }
    }

    /**
     * Deletes {@link BuildVersion} instance from the database.
     *
     * @param id identifier of Build Version stored in the DB.
     * @return the number of rows affected by the statement.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public int deleteBuildVersion(int id) {
        try {
            return buildVersionMapper.deleteBuildVersion(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete BuildVersion instance, request id: " + id, e);
            throw e;
        }
    }

    /**
     * Checks the unique of build version's name.
     *
     * @param name of {@link BuildVersion} should be checked.
     * @param exclusionId id of {@link BuildVersion} should be excluded.
     * @return true, if name is unique.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public boolean isBuildVersionNameFree(String name, int exclusionId) {
        try {
            return buildVersionMapper.isBuildVersionNameFree(name, exclusionId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check build version's name, build version's name: " + name, e);
            throw e;
        }
    }

    // LABELS

    /**
     * Saves {@link Label} instance to database.
     * 
     * @param label instance should be saved in the DB.
     * @return id of saved Label.
     * @throws DuplicateKeyException if the Label with the same name exists in the database.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public int saveLabel(Label label) {
        try {
            labelMapper.saveLabel(label);
            int id = label.getId();
            LOGGER.info("Successfully saved Label instance in the database, label id: " + id);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to save Label instance, label id: " + label.getId(), e);
            throw e;
        }
    }

    /**
     * Loads {@link Label} instance from database.
     * 
     * @param id identifier of Label stored in the DB.
     * @return {@link Label} instance.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public Label loadLabelById(int id) {
        try {
            Label label = labelMapper.loadLabelById(id);
            if (label == null)
                throw new ResourceNotFoundException("Label not found, id: " + id);
            return label;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load Label instance, label id: " + id, e);
            throw e;
        }
    }

    /**
     * Loads all stored {@link Label} instances with their main information.
     * 
     * @return Set of {@link Label} instances.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public List<Label> loadAllLabels() {
        try {
            return labelMapper.loadAllLabels();
        } catch (DataAccessException e) {
            LOGGER.error("Unable to load Label instances", e);
            throw e;
        }
    }

    /**
     * Updates {@link Label} instance should be updated in the database.
     *
     * @param label instance that must be updated.
     * @return the number of rows affected by the statement.
     * @throws DuplicateKeyException if the label with the same name exists in the database.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public int updateLabel(Label label) {
        try {
            labelMapper.updateLabel(label);
            int id = label.getId();
            LOGGER.info("Successfully updated Label instance in the database, label id: " + id);
            return id;
        } catch (DataAccessException e) {
            LOGGER.error("Unable to update Label instance, label id " + label.getId(), e);
            throw e;
        }
    }

    /**
     * Deletes {@link Label} instance from the database.
     * 
     * @param id identifier of Label stored in the DB.
     * @return the number of rows affected by the statement.
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public int deleteLabel(int id) {
        try {
            return labelMapper.deleteLabel(id);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to delete Label instance, request id: " + id, e);
            throw e;
        }
    }

    /**
     * Checks the unique of label's name.
     *
     * @param name of {@link Label} should be checked.
     * @param exclusionId id of {@link Label} should be excluded.
     * @return true, if name is unique
     * @throws DataAccessException appear when there is no access to the DB.
     */
    @Transactional
    public boolean isLabelNameFree(String name, int exclusionId) {
        try {
            return labelMapper.isLabelNameFree(name, exclusionId);
        } catch (DataAccessException e) {
            LOGGER.error("Unable to check label's name, label's name: " + name, e);
            throw e;
        }
    }
    
}
