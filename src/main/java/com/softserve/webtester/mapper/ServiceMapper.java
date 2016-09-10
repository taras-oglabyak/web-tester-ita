package com.softserve.webtester.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.model.Service;

/**
 * ServiceMapper is MyBatis mapper interface for {@link Service} CRUD
 * operations.
 *
 * @author Roman Zolotar
 * @version 1.3
 */

@Repository
public interface ServiceMapper {
    final String LOAD_ALL = "SELECT * FROM Service";
    final String LOAD_ALL_WITHOUT_DELETED = "SELECT * FROM Service WHERE DELETED = 0";
    final String LOAD = "SELECT * FROM Service WHERE ID = #{id}";
    final String LOADNAME = "SELECT NAME FROM Service WHERE ID = #{id}";
    final String DELETE_BY_ID = "DELETE from Service WHERE ID = #{id}";
    final String INSERT = "INSERT INTO Service (NAME, DESCRIPTION, SLA, DELETED) VALUES (#{name}, #{description}, #{sla}, #{deleted})";
    final String UPDATE = "UPDATE Service SET DELETED = #{deleted}, NAME = #{name}, DESCRIPTION = #{description}, SLA = #{sla} WHERE ID = #{id}";
    final String IS_SERVICE_NAME_FREE = "SELECT IF(count(*) > 0, false, true) FROM Service WHERE name = #{name} AND id != #{exclusionId}";

    /**
     * Loads all {@link Service} instance from DB.
     * @return List of Service instances
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD_ALL)
    @Results(value = { @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "sla", column = "SLA"),
            @Result(property = "deleted", column = "DELETED") })
    List<Service> loadAll();

    /**
     * Loads all {@link Service} instances from DB which aren't marked as "deleted".
     * @return List of Service instances
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD_ALL_WITHOUT_DELETED)
    @Results(value = { @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "sla", column = "SLA"),
            @Result(property = "deleted", column = "DELETED") })
    List<Service> loadAllWithoutDeleted();

    /**
     * Loads {@link Service} instance from DB by it's identifier.
     * @param id of Service instance which stored in the DB
     * @return Service instance
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD)
    @Results(value = { @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "sla", column = "SLA"),
            @Result(property = "deleted", column = "DELETED") })
    Service load(int id);
    
    /**
     * Loads name of {@link Service} instance from DB by it's identifier. Used in Results/Graphic.
     * @param id of Service instance which stored in the DB
     * @return name of Service instance
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOADNAME)
    String loadServiceName(int id);

    /**
     * Updates {@link Service} instance in the DB.
     * @param Service instance which needs to updated
     * @throws DataAccessException when there is no access to the DB
     */
    @Update(UPDATE)
    void update(Service service);

    /**
     * Deletes {@link Service} instance from the DB.
     * @param id of Service instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    @Delete(DELETE_BY_ID)
    void delete(int id);

    /**
     * Saves {@link Service} instance to DB.
     * @param Service instance which needs to store in the DB
     * @throws DataAccessException when there is no access to the DB
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Service service);

    /**
     * Checks the unique of {@link Service} name.
     * @param name of Service which should be checked
     * @param exclusionId - id of Service which should be excluded
     * @return true, if name is unique
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(IS_SERVICE_NAME_FREE)
    boolean isServiceNameFree(@Param("name") String name, @Param("exclusionId") int exclusionId);
}