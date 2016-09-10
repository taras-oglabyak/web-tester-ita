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

import com.softserve.webtester.model.Application;

/**
 * ApplicationMapper is MyBatis mapper interface for {@link Application} CRUD
 * operations.
 * 
 * @author Roman Zolotar
 * @version 1.3
 */

@Repository
public interface ApplicationMapper {
    final String LOAD_ALL = "SELECT * FROM Application";
    final String LOAD_ALL_WITHOUT_DELETED = "SELECT * FROM Application WHERE DELETED = 0";
    final String LOAD = "SELECT * FROM Application WHERE ID = #{id}";
    final String DELETE_BY_ID = "DELETE from Application WHERE ID = #{id}";
    final String INSERT = "INSERT INTO Application (NAME, DESCRIPTION, DELETED) VALUES (#{name}, #{description}, #{deleted})";
    final String UPDATE = "UPDATE Application SET DELETED = #{deleted}, NAME = #{name}, DESCRIPTION = #{description} WHERE ID = #{id}";
    final String IS_APPLICATION_NAME_FREE = "SELECT IF(count(*) > 0, false, true) FROM Application WHERE name = #{name} AND id != #{exclusionId}";

    /**
     * Loads all {@link Application} instance from DB.
     * @return List of Application instances
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD_ALL)
    @Results(value = { @Result(property = "id", column = "ID"), 
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "deleted", column = "DELETED") })
    List<Application> loadAll();
    
    /**
     * Loads all {@link Application} instances from DB which aren't marked as "deleted".
     * @return List of Application instances
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD_ALL_WITHOUT_DELETED)
    @Results(value = { @Result(property = "id", column = "ID"), 
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "deleted", column = "DELETED") })
    List<Application> loadAllWithoutDeleted();
    
    /**
     * Loads {@link Application} instance from DB by it's identifier.
     * @param id of Application instance which stored in the DB
     * @return Application instance
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(LOAD)
    @Results(value = { @Result(property = "id", column = "ID"), 
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCRIPTION"),
            @Result(property = "deleted", column = "DELETED") })
    Application load(int id);

    /**
     * Updates {@link Application} instance in the DB.
     * @param Application instance which needs to updated
     * @throws DataAccessException when there is no access to the DB
     */
    @Update(UPDATE)
    void update(Application application);
    
    /**
     * Deletes {@link Application} instance from the DB.
     * @param id of Application instance which need to delete
     * @throws DataAccessException when there is no access to the DB
     */
    @Delete(DELETE_BY_ID)
    void delete(int id);

    /**
     * Saves {@link Application} instance to DB.
     * @param Application instance which needs to store in the DB
     * @throws DataAccessException when there is no access to the DB
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Application application);
    
    /**
     * Checks the unique of {@link Application} name.
     * @param name of Application which should be checked
     * @param exclusionId - id of Application which should be excluded
     * @return true, if name is unique
     * @throws DataAccessException when there is no access to the DB
     */
    @Select(IS_APPLICATION_NAME_FREE)
    boolean isApplicationNameFree(@Param("name") String name, @Param("exclusionId") int exclusionId);
}
