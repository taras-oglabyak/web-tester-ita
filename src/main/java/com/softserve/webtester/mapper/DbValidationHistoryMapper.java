package com.softserve.webtester.mapper;

import com.softserve.webtester.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis DbValidationHistoryMapper mapper for performing CRUD operations on DbValidationHistory database instance.
 */

@Repository
public interface DbValidationHistoryMapper {

    /**
     * Saving {@link DbValidationHistory} instance to DB
     * @param dbValidationHistory DbValidation instance should be saved in DB
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO DbValidationHistory VALUES(NULL, #{sqlQuery}, #{expectedValue}, #{actualValue}, #{resultHistory.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(DbValidationHistory dbValidationHistory);

    /**
     * Saves {@link DbValidationHistory} instances for the Request in DB
     * Using SQL batch insert this method saves all dbValidationHistories.
     * @param resultHistory {@link ResultHistory} instance, whose dbValidationHistories should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("<script>INSERT INTO DbValidationHistory(sqlQuery, expectedValue, actualValue, resultHistoryId) VALUES "
            + "<foreach collection='dbValidationHistories' item='dbValidationHistory' separator=','> "
            + "(#{dbValidationHistory.sqlQuery}, #{dbValidation.expectedValue}, #{dbValidation.actualValue}, #{id}) "
            + "</foreach></script>")
    int saveByResultHistory(ResultHistory resultHistory);

    /**
     * Loads {@link DbValidationHistory} instance from DB by its identifier
     * @param id identifier of DbValidationHistory instance
     * @return DbValidationHistory instance
     * @throws DataAccessException
     */
    @Select("SELECT id, sqlQuery, expectedValue, actualValue FROM DbValidationHistory WHERE id = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "sqlQuery", column = "sqlQuery", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "expectedValue", column = "expectedValue", jdbcType = JdbcType.VARCHAR),
            @Result(property = "actualValue", column = "actualValue", jdbcType = JdbcType.VARCHAR)
    })
    DbValidationHistory load(int id);

    /**
     * Loads all {@link DbValidationHistory} instances for the ResultHistory from DB
     * @param id identifier of {@link ResultHistory} instance, whose dbValidationHistories should be loaded
     * @return List of DbValidationHistory instances
     * @throws DataAccessException
     */
    @Select("SELECT id, sqlQuery, expectedValue, actualValue FROM DbValidationHistory WHERE resultHistoryId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "sqlQuery", column = "sqlQuery", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "expectedValue", column = "expectedValue", jdbcType = JdbcType.VARCHAR),
            @Result(property = "actualValue", column = "actualValue", jdbcType = JdbcType.VARCHAR)
    })
    List<DbValidationHistory> loadByResultHistoryId(int id);

    /**
     * Updates {@link DbValidationHistory} instance in DB
     * @param dBValidationHistory DbValidationHistory instance to be be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE DbValidationHistory SET sqlQuery = #{sqlQuery}, expectedValue = #{expectedValue}, "
            + "actualValue = #{actualValue}, resultHistoryId = #{resultHistory.id} WHERE id = #{id}")
    int update(DbValidationHistory dBValidationHistory);

    /**
     * Deletes {@link DbValidationHistory} instance from DB
     * @param id identifier of DbValidationHistory instance to be be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM DbValidationHistory WHERE id = #{id}")
    int delete(int id);

    /**
     * Deletes {@link DbValidationHistory} instances from DB
     * @param id identifier of {@link ResultHistory} instance, whose dbValidationHistories should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM DbValidationHistory WHERE resultHistoryId = #{id}")
    int deleteByResultHistoryId(int id);
}