package com.softserve.webtester.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.model.DbValidation;
import com.softserve.webtester.model.Request;

/**
 * DbValidationMapper is MyBatis mapper interface for CRUD operation on {@link DbValidation} instance in the database.
 *
 */
@Repository
public interface DbValidationMapper {

    /**
     * Saves {@link DbValidation} instance to database.<br>
     * 
     * @param dbValidation DbValidation instance should be saved in the database
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO DbValidation(sqlQuery, expectedValue, requestId) "
            + "VALUES(#{sqlQuery}, #{expectedValue}, #{request.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(DbValidation dbValidation);

    /**
     * Saves {@link DbValidation} instances for the Request in the database.<br>
     * Using SQL batch insert this method saves all dbValidations.
     * 
     * @param request {@link Request} instance, whose dbValidations should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("<script>INSERT INTO DbValidation(sqlQuery, expectedValue, requestId) VALUES "
            + "<foreach collection='dbValidations' item='dbValidation' separator=','> "
            + "(#{dbValidation.sqlQuery}, #{dbValidation.expectedValue}, #{id}) " + "</foreach></script>")
    int saveByRequest(Request request);

    /**
     * Loads {@link DbValidation} instance from database by its identifier.
     * 
     * @param id identifier of DbValidation instance
     * @return DbValidation instance
     * @throws DataAccessException
     */
    @Select("SELECT id, sqlQuery, expectedValue FROM DbValidation WHERE id = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "sqlQuery", column = "sqlQuery", jdbcType = JdbcType.LONGVARCHAR),
               @Result(property = "expectedValue", column = "expectedValue", jdbcType = JdbcType.VARCHAR) })
    DbValidation load(int id);

    /**
     * Loads all {@link DbValidation} instances for the Request from the database.
     * 
     * @param id identifier of {@link Request} instance, whose dbValidations should be loaded
     * @return List of DbValidation instances
     * @throws DataAccessException
     */
    @Select("SELECT id, sqlQuery, expectedValue FROM DbValidation WHERE requestId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "sqlQuery", column = "sqlQuery", jdbcType = JdbcType.LONGVARCHAR),
               @Result(property = "expectedValue", column = "expectedValue", jdbcType = JdbcType.VARCHAR) })
    List<DbValidation> loadByRequestId(int id);

    /**
     * Updates {@link DbValidation} instance in the database.
     * 
     * @param dbValidation DbValidation instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE DbValidation SET sqlQuery = #{sqlQuery}, expectedValue = #{expectedValue}, "
            + "requestId = #{request.id} WHERE id = #{id}")
    int update(DbValidation dbValidation);

    /**
     * Deletes {@link DbValidation} instance from the database.
     * 
     * @param id identifier of DbValidation instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM DbValidation WHERE id = #{id}")
    int delete(int id);

    /**
     * Deletes {@link DbValidation} instances from the database.
     * 
     * @param id identifier of {@link Request} instance, whose dbValidations should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM DbValidation WHERE requestId = #{id}")
    int deleteByRequestId(int id);

}