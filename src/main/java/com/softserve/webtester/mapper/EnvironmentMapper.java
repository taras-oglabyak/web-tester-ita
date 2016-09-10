package com.softserve.webtester.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.model.Environment;
import com.softserve.webtester.model.EnvironmentDbTypeHandler;

/**
 * 
 * MyBatisMapper provides database CRUD operations for {@link Environment} object
 *
 */
@Repository
public interface EnvironmentMapper {

    /**
     * Saves {@link Environment} instance to database. Uses insertSql method
     * from {@link EnvironmentSqlProvider}
     * 
     * @param Environment instance should be stored
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @InsertProvider(type = EnvironmentSqlProvider.class, method = "insertSql")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Environment environment);

    /**
     * Selects {@link Environment} entity from database. Uses selectSql method
     * from {@link EnvironmentSqlProvider}
     * 
     * @param id identifier of Environment instance
     * @return Environment instance
     * @throws DataAccessException
     */
    @SelectProvider(type = EnvironmentSqlProvider.class, method = "selectSql")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "baseUrl", column = "baseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbType", column = "dbType", typeHandler = EnvironmentDbTypeHandler.class),
            @Result(property = "dbUrl", column = "dbUrl", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbPort", column = "dbPort", jdbcType = JdbcType.INTEGER),
            @Result(property = "dbName", column = "dbName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbUsername", column = "dbUsername", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbPassword", column = "dbPassword", jdbcType = JdbcType.VARCHAR),
            @Result(property = "timeMultiplier", column = "timeMultiplier", jdbcType = JdbcType.DOUBLE),
            @Result(property = "deleted", column = "deleted", jdbcType = JdbcType.BIT) })
    Environment load(int id);

    /**
     * Selects all {@link Environment} entities not marked as "deleted" from
     * database. Uses selectAllSql method from {@link EnvironmentSqlProvider}
     * 
     * @return List of Environment instances
     * @throws DataAccessException
     */
    @SelectProvider(type = EnvironmentSqlProvider.class, method = "selectAllSql")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "baseUrl", column = "baseUrl", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbType", column = "dbType", typeHandler = EnvironmentDbTypeHandler.class),
            @Result(property = "dbUrl", column = "dbUrl", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbPort", column = "dbPort", jdbcType = JdbcType.INTEGER),
            @Result(property = "dbName", column = "dbName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbUsername", column = "dbUsername", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbPassword", column = "dbPassword", jdbcType = JdbcType.VARCHAR),
            @Result(property = "timeMultiplier", column = "timeMultiplier", jdbcType = JdbcType.DOUBLE),
            @Result(property = "deleted", column = "deleted", jdbcType = JdbcType.BIT) })
    List<Environment> loadAll();

    /**
     * Updates {@link Environment} instance in the database. Uses updateSql
     * method from {@link EnvironmentSqlProvider}
     * 
     * @param Environment instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @UpdateProvider(type = EnvironmentSqlProvider.class, method = "updateSql")
    int update(Environment environment);

    /**
     * Deletes {@link Environment} instance from database. Uses deleteSql method
     * from {@link EnvironmentSqlProvider}
     * 
     * @param Environment instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @UpdateProvider(type = EnvironmentSqlProvider.class, method = "deleteSql")
    int delete(Environment environment);

    /**
     * Checks the unique of {@link Environment} property "name"
     * 
     * @param Environment instance should be checked
     * @return count of entities with the same property "name"
     * @throws DataAccessException
     */
    @SelectProvider(type = EnvironmentSqlProvider.class, method = "isNameFree")
    int isNameFree(Environment environment);
}
