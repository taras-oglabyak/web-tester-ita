package com.softserve.webtester.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.model.EnvironmentHistory;

/**
 * MyBatis EnvironmentHistoryMapper mapper for performing CRUD operations on EnvironmentHistory database instance.
 *
 */

@Repository
public interface EnvironmentHistoryMapper {

    /**
     * Saves {@link EnvironmentHistory} instance to DB
     * @param environmentHistory EnvironmentHistory instance should be saved in DB
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO EnvironmentHistory VALUES(NULL, #{resultHistory.id}, #{name}, #{baseURL}, #{dbURL}, " +
            "#{dbPort}, #{dbName}, #{environment.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(EnvironmentHistory environmentHistory);

    /**
     * Loads {@link EnvironmentHistory} instance from DB by its identifier
     * @param id identifier of Header instance
     * @return EnvironmentHistory instance
     * @throws DataAccessException
     */
    @Select("SELECT id, name, baseURL, dbURL, dbPort, dbName, environmentId FROM EnvironmentHistory WHERE id = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "baseURL", column = "baseURL", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbURL", column = "dbURL", jdbcType = JdbcType.VARCHAR),
            @Result(property = "dbPort", column = "dbPort", jdbcType = JdbcType.INTEGER),
            @Result(property = "dbName", column = "dbName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "environment", column = "environmentId",
                    one = @One(select = "com.softserve.webtester.mapper.EnvironmentMapper.load"))
    })
    EnvironmentHistory load(int id);

    /**
     * Updates {@link EnvironmentHistory} instance in DB
     * @param environmentHistory EnvironmentHistory instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE EnvironmentHistory SET resultHistoryId = #{resultHistory.id}, name = #{name}, baseURL = #{baseURL}, "
            + "dbURL = #{dbURL}, dbPort = #{dbPort}, dbName = #{dbName}," +
            "environmentId = #{environment.id} WHERE id = #{id}")
    int update(EnvironmentHistory environmentHistory);

    /**
     * Deletes {@link EnvironmentHistory} instance from DB
     * @param id identifier of EnvironmentHistory environmentHistory should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM EnvironmentHistory WHERE id = #{id}")
    int delete(int id);

    /**
     * Deletes {@link EnvironmentHistory} instances from DB
     * @param id identifier of {@link EnvironmentHistory} instances, whose environmentHistories should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM EnvironmentHistory WHERE resultHistoryId = #{id}")
    int deleteByResultHistoryId(int id);
}