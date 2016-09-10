package com.softserve.webtester.mapper;

import com.softserve.webtester.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * MyBatis ResultHistoryMapper mapper for performing CRUD operations on ResultHistory database instance.
 *
 */

@Repository
public interface ResultHistoryMapper {

    /**
     * Saving collection with buildVersion
     */
    @Insert("INSERT INTO ResultHistory VALUES(NULL, #{status}, #{application.id}, #{service.id}, #{request.id}," +
            " #{requestName}, #{requestDescription}, #{url}, #{responseType}, #{requestBody}, " +
            "#{statusLine}, #{timeStart}, #{expectedResponseTime}, #{responseTime}, #{expectedResponse}," +
            " #{actualResponse}, #{message}, #{runId}, #{requestCollection.id}, #{buildVersion.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(ResultHistory resultHistory);

    /**
     * Saving collection without buildVersion
     */
    @Insert("INSERT INTO ResultHistory VALUES(NULL, #{status}, #{application.id}, #{service.id}, #{request.id}," +
            " #{requestName}, #{requestDescription}, #{url}, #{responseType}, #{requestBody}, " +
            "#{statusLine}, #{timeStart}, #{expectedResponseTime}, #{responseTime}, #{expectedResponse}," +
            " #{actualResponse}, #{message}, #{runId}, #{requestCollection.id}, null)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveCollection(ResultHistory resultHistory);

    /**
     * Saving only request
     */
    @Insert("INSERT INTO ResultHistory VALUES(NULL, #{status}, #{application.id}, #{service.id}, #{request.id}," +
            " #{requestName}, #{requestDescription}, #{url}, #{responseType}, #{requestBody}, " +
            "#{statusLine}, #{timeStart}, #{expectedResponseTime}, #{responseTime}, #{expectedResponse}," +
            " #{actualResponse}, #{message}, #{runId}, NULL, NULL)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveRequest(ResultHistory resultHistory);

    /**
     * Loading {@link ResultHistory} instance from DB by its identifier
     * @param id identifies ResultHistory instance
     * @return ResultHistory instance
     * @throws DataAccessException
     */
    @Select("SELECT id, status, applicationId, serviceId, requestId, requestName, requestDescription, url," +
            "responseType, requestBody, statusLine, timeStart, expectedResponseTime, responseTime, expectedResponse, actualResponse, message," +
            " runId, requestCollectionId, buildVersionId FROM ResultHistory WHERE id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "application", column = "applicationId",
                    one = @One(select = "com.softserve.webtester.mapper.ApplicationMapper.load")),
            @Result(property = "service", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.load")),
            @Result(property = "request", column = "requestId",
                    one = @One(select = "com.softserve.webtester.mapper.RequestMapper.load")),
            @Result(property = "requestName", column = "requestName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "requestDescription", column = "requestDescription", jdbcType = JdbcType.VARCHAR),
            @Result(property = "url", column = "url", jdbcType = JdbcType.VARCHAR),
            @Result(property = "responseType", column = "responseType", jdbcType = JdbcType.VARCHAR),
            @Result(property = "requestBody", column = "requestBody", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "statusLine", column = "statusLine", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "expectedResponseTime", column = "expectedResponseTime", jdbcType = JdbcType.INTEGER),
            @Result(property = "responseTime", column = "responseTime", jdbcType = JdbcType.INTEGER),
            @Result(property = "expectedResponse", column = "expectedResponse", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "actualResponse", column = "actualResponse", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "runId", column = "runId", jdbcType = JdbcType.INTEGER),
            @Result(property = "buildVersion", column = "buildVersionId",
                    one = @One(select = "com.softserve.webtester.mapper.BuildVersionMapper.loadBuildVersionById")),
            @Result(property = "requestCollection", column = "requestCollectionId",
                    one = @One(select = "com.softserve.webtester.mapper.RequestCollectionMapper.load")),
            @Result(property = "labels", column = "id",
                    many = @Many(select = "com.softserve.webtester.mapper.LabelMapper.loadByResultHistoryId")),
            @Result(property = "headerHistories", column = "id",
                    many = @Many(select = "com.softserve.webtester.mapper.HeaderHistoryMapper.loadByResultHistoryId")),
            @Result(property = "dbValidationHistories", column = "id",
                    many = @Many(select = "com.softserve.webtester.mapper.DbValidationHistoryMapper.loadByResultHistoryId"))
    })
    ResultHistory loadById(int id);

    /**
     * Loading all {@link ResultHistory} instances from DB
     * This method loads only main information about ResultHistory instance.
     * @param status using for filtering instances, which status identifier are in the array
     * @param applications using for filtering instances, which application identifier are in the array
     * @param services using for filtering instances, which service identifier are in the array
     * @return List of ResultHistory instances
     * @throws DataAccessException
     */
    @Select({"<script>SELECT DISTINCT id, status, applicationId, serviceId, requestName, message," +
            " requestDescription, timeStart FROM ResultHistory WHERE id > 0",
            "<if test='status!=null and status!=\"\"'> AND status =#{status}</if>",

            "<if test='applications!=null and applications.length>0'> AND applicationId IN",
            "<foreach collection='applications' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",

            "<if test='services!=null and services.length>0'> AND serviceId IN",
            "<foreach collection='services' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",
            "</script>"})
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "application", column = "applicationId",
                    one = @One(select = "com.softserve.webtester.mapper.ApplicationMapper.load")),
            @Result(property = "service", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.load")),
            @Result(property = "requestName", column = "requestName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "requestDescription", column = "requestDescription", jdbcType = JdbcType.VARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ResultHistory> loadAll(@Param(value = "status") boolean status,
                                @Param(value = "applications") int[] applications,
                                @Param(value = "services") int[] services);

    /**
     * Loading all {@link ResultHistory} instances by requestCollectionId and runId from DB
     * @param status using for filtering instances, which status identifier are in the array
     * @param applications using for filtering instances, which application identifier are in the array
     * @param services using for filtering instances, which service identifier are in the array
     * @param id using for selecting instances by requestCollectionId
     * @param runId using for selecting instances by runId
     * @return List of ResultHistory instances
     * @throws DataAccessException
     */
    @Select({"<script>SELECT DISTINCT id, status, applicationId, serviceId, runId, requestName, message, requestDescription, timeStart " +
            "FROM ResultHistory WHERE requestCollectionId = #{id} AND runId = #{runId}",
            "<if test='status!=null and status!=\"\"'> AND status =#{status}</if>",

            "<if test='applications!=null and applications.length>0'> AND applicationId IN",
            "<foreach collection='applications' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",

            "<if test='services!=null and services.length>0'> AND serviceId IN",
            "<foreach collection='services' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",
            "</script>"})
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "application", column = "applicationId",
                    one = @One(select = "com.softserve.webtester.mapper.ApplicationMapper.load")),
            @Result(property = "service", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.load")),
            @Result(property = "requestName", column = "requestName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "requestDescription", column = "requestDescription", jdbcType = JdbcType.VARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ResultHistory> loadAllRequestsByCollectionId(@Param(value = "status") boolean status,
                                                      @Param(value = "applications") int[] applications,
                                                      @Param(value = "services") int[] services,
                                                      @Param(value = "id") int id,
                                                      @Param(value = "runId") int runId);

    /**
     * Loading all {@link ResultHistory} instances from DB
     * @param status using for filtering instances, which status identifier are in the array
     * @param labels using for filtering instances, which labels identifier are in the array
     * @param buildVersions using for filtering instances, which buildVersions identifier are in the array
     * @return List of ResultHistory instances
     * @throws DataAccessException
     */
    @Select({"<script>SELECT r.id, r.runId, r.requestCollectionId, r.buildVersionId, MIN(r.status) AS status, r.message," +
            " r.timeStart FROM ResultHistory r ",
            "<if test='labels!=null and labels.length>0'>LEFT JOIN ResultHistory_Label rl ON r.id=rl.resultHistoryId ",
            "</if>",
            "WHERE r.requestCollectionId > 0",
            "<if test='status!=null and status!=\"\"'> AND status =#{status}</if>",

            "<if test='buildVersions!=null and buildVersions.length>0'> AND r.buildVersionId IN",
            "<foreach collection='buildVersions' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",

            "<if test='labels!=null and labels.length>0'> AND rl.labelId IN",
            "<foreach collection='labels' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}</foreach></if>",
            "GROUP BY r.requestCollectionId, r.runId",
            "</script>"})
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "runId", column = "runId", jdbcType = JdbcType.INTEGER),
            @Result(property = "requestCollection", column = "requestCollectionId",
                    one = @One(select = "com.softserve.webtester.mapper.RequestCollectionMapper.load")),
            @Result(property = "labels", column = "id",
                    many = @Many(select = "com.softserve.webtester.mapper.LabelMapper.loadByResultHistoryId")),
            @Result(property = "buildVersion", column = "buildVersionId",
                    one = @One(select = "com.softserve.webtester.mapper.BuildVersionMapper.loadBuildVersionById")),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ResultHistory> loadAllCollections(@Param(value = "status") boolean status,
                                           @Param(value = "labels") int[] labels,
                                           @Param(value = "buildVersions") int[] buildVersions);

    /**
     * Loading all {@link ResultHistory} instances by runId from DB
     * @param id using for selecting instances by RunId
     * @throws DataAccessException
     */
    @Select("SELECT id, status, applicationId, serviceId, runId, requestName, message, requestDescription, timeStart " +
            "FROM ResultHistory WHERE runId = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "application", column = "applicationId",
                    one = @One(select = "com.softserve.webtester.mapper.ApplicationMapper.load")),
            @Result(property = "service", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.load")),
            @Result(property = "requestName", column = "requestName", jdbcType = JdbcType.VARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "requestDescription", column = "requestDescription", jdbcType = JdbcType.VARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ResultHistory> loadAllRequestsByRunId(int id);

    /**
     * Loading all {@link ResultHistory} instances by runId from DB
     * @param id using for selecting instances by RunId
     * @throws DataAccessException
     */
    @Select({"SELECT id, runId, requestCollectionId, buildVersionId, status, message," +
            " timeStart FROM ResultHistory WHERE runId = #{id} GROUP BY requestCollectionId"})
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "runId", column = "runId", jdbcType = JdbcType.INTEGER),
            @Result(property = "requestCollection", column = "requestCollectionId",
                    one = @One(select = "com.softserve.webtester.mapper.RequestCollectionMapper.load")),
            @Result(property = "labels", column = "id",
                    many = @Many(select = "com.softserve.webtester.mapper.LabelMapper.loadByResultHistoryId")),
            @Result(property = "buildVersion", column = "buildVersionId",
                    one = @One(select = "com.softserve.webtester.mapper.BuildVersionMapper.loadBuildVersionById")),
            @Result(property = "status", column = "status", jdbcType = JdbcType.VARCHAR),
            @Result(property = "message", column = "message", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "timeStart", column = "timeStart", jdbcType = JdbcType.TIMESTAMP)
    })
    List<ResultHistory> loadAllCollectionsByRunId(int id);

    /**
     * Deleting {@link ResultHistory} instances from DB
     * @param arr identifiers ResultHistory instances to be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("<script>DELETE FROM ResultHistory WHERE id IN "
            + "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{item}</foreach></script>")
    int deleteSelectedResults(@Param("list") int[] arr);

    /**
     * Deleting {@link ResultHistory} instances from DB
     * @param arr identifiers ResultHistory instances to be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Delete("<script>DELETE FROM ResultHistory WHERE id IN "
            + "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{item}</foreach></script>")
    int deleteSelectedCollectionResults(@Param("list") int[] arr);

    /**
     * Deleting {@link ResultHistory} instance by its identifier from DB
     * @param id identifies ResultHistory instance
     * @throws DataAccessException
     */
    @Delete("DELETE FROM ResultHistory WHERE id = #{id}")
    int deleteByResultHistoryId(int id);

    /**
     * Deleting {@link ResultHistory} instance by its identifier from DB
     * @param id identifies ResultHistory instance
     * @throws DataAccessException
     */
    @Delete("DELETE FROM ResultHistory WHERE requestCollectionId = #{id}")
    int deteleByCollectionId(int id);

    /**
     * Selecting {@link ResultHistory} max value of runId field from DB
     * @throws DataAccessException
     */
    @Select("SELECT MAX(runId) FROM ResultHistory")
    int getMaxId();
}