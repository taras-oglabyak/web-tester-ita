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

import com.softserve.webtester.model.Header;
import com.softserve.webtester.model.Request;

/**
 * HeaderMapper is MyBatis mapper interface for CRUD operation on {@link Header} instance in the database.
 *
 */
@Repository
public interface HeaderMapper {

    /**
     * Saves {@link Header} instance to the database.
     * 
     * @param header Header instance should be saved in the database
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO Header(name, value, requestId) VALUES(#{name}, #{value}, #{request.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(Header header);

    /**
     * Saves {@link Header} instances for the Request in the database.
     * 
     * @param request {@link Request} instance, whose headers should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("<script>INSERT INTO Header(name, value, requestId) VALUES "
            + "<foreach collection='headers' item='header' separator=','> "
            + "(#{header.name}, #{header.value}, #{id}) " + "</foreach></script>")
    int saveByRequest(Request request);

    /**
     * Loads {@link Header} instance from database by its identifier.
     * 
     * @param id identifier of Header instance
     * @return Header instance
     * @throws DataAccessException
     */
    @Select("SELECT id, name, value FROM Header WHERE id = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
               @Result(property = "value", column = "value", jdbcType = JdbcType.VARCHAR) })
    Header load(int id);

    /**
     * Loads all {@link Header} instances for the Request from the database.
     * 
     * @param id identifier of {@link Request} instance, whose headers should be loaded
     * @return List of Header instances
     * @throws DataAccessException
     */
    @Select("SELECT id, name, value FROM Header WHERE requestId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
               @Result(property = "value", column = "value", jdbcType = JdbcType.VARCHAR) })
    List<Header> loadByRequestId(int id);

    /**
     * Updates {@link Header} instance in the database.
     * 
     * @param header Header instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE Header SET name = #{name}, value = #{value}, requestId = #{request.id} WHERE id = #{id}")
    int update(Header header);

    /**
     * Deletes {@link Header} instance from the database.
     * 
     * @param id identifier of Header instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM Header WHERE id = #{id}")
    int delete(int id);

    /**
     * Deletes {@link Header} instances from the database.
     * 
     * @param id identifier of {@link Request} instances, whose headers should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("DELETE FROM Header WHERE requestId = #{id}")
    int deleteByRequestId(int id);

}