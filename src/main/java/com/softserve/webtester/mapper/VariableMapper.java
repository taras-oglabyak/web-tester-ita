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

import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.Variable;
import com.softserve.webtester.model.VariableDataTypeHandler;

/**
 * VariableMapper is MyBatis mapper interface for CRUD operation on {@link Variable} instance in the database.
 *
 */
@Repository
public interface VariableMapper {

    /**
     * Saves {@link Variable} instance to database.
     * 
     * @param variable Variable instance should be saved in the database
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO Variable(name, value, isSql, isRandom, dataType, length, requestId) "
            + "VALUES(#{name}, #{value}, #{sql}, #{random}, #{dataType}, #{length}, #{request.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Variable variable);

    /**
     * Saves {@link Variable} instances for the Request in the database.
     * 
     * @param request {@link Request} instances, whose variables should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("<script>INSERT INTO Variable(name, value, isSql, isRandom, dataType, length, requestId) VALUES "
            + "<foreach collection='variables' item='variable' separator=','> "
            + "(#{variable.name}, #{variable.value}, #{variable.sql}, #{variable.random}, "
            + "#{variable.dataType}, #{variable.length}, #{id}) " 
            + "</foreach></script>")
    int saveByRequest(Request request);

    /**
     * Loads {@link Variable} instance from database by its identifier.
     * 
     * @param id identifier of Variable instance
     * @return Variable instance
     * @throws DataAccessException
     */
    @Select("SELECT id, name, value, isSql, isRandom, dataType, length FROM Variable WHERE id = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
               @Result(property = "value", column = "value", jdbcType = JdbcType.LONGVARCHAR),
               @Result(property = "sql", column = "isSql", jdbcType = JdbcType.BIT),
               @Result(property = "random", column = "isRandom", jdbcType = JdbcType.BIT),
               @Result(property = "dataType", column = "dataType", typeHandler = VariableDataTypeHandler.class),
               @Result(property = "length", column = "length", jdbcType = JdbcType.INTEGER) })
    Variable load(int id);

    /**
     * Loads all {@link Variable} instances for the Request from the database.
     * 
     * @param id identifier of {@link Request} instance, whose variables should be loaded
     * @return List of Variable instances
     * @throws DataAccessException
     */
    @Select("SELECT id, name, value, isSql, isRandom, dataType, length FROM Variable WHERE requestId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
               @Result(property = "value", column = "value", jdbcType = JdbcType.LONGVARCHAR),
               @Result(property = "sql", column = "isSql", jdbcType = JdbcType.BIT),
               @Result(property = "random", column = "isRandom", jdbcType = JdbcType.BIT),
               @Result(property = "dataType", column = "dataType", typeHandler = VariableDataTypeHandler.class),
               @Result(property = "length", column = "length", jdbcType = JdbcType.INTEGER) })
    List<Variable> loadByRequestId(int id);

    /**
     * Updates {@link Variable} instance in the database.
     * 
     * @param variable Variable instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE Variable SET name = #{name}, value = #{value}, "
            + "isSql = #{sql}, isRandom = #{random}, dataType = #{dataType}, "
            + "length = #{length}, requestId = #{request.id} WHERE id = #{id}")
    int update(Variable variable);

    /**
     * Deletes {@link Variable} instance from the database.
     * 
     * @param id identifier of Variable instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM Variable WHERE id = #{id}")
    int delete(int id);

    /**
     * Deletes {@link Variable} instances from the database.
     * 
     * @param id identifier of {@link Request} instance, whose variables should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM Variable WHERE requestId = #{id}")
    int deleteByRequestId(int id);

}