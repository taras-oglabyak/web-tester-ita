package com.softserve.webtester.mapper;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.dto.ReportDataDTO;
import com.softserve.webtester.dto.StatisticDataDTO;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.model.Service;

/**
 * ReportMapper is MyBatis mapper interface for selecting report data from database.
 * 
 */
@Repository
public interface ReportMapper {

    /**
     * Loads report data with average response time for each build version testing run of {@link RequestCollection} 
     * for the service from database by it`s identifier.
     * 
     * @param serviceId identifier of Service
     * @param buildVersionId array of build versions identtifiers
     * @return list of ReportDataDTO instances 
     * @throws DataAccessException
     */
    @Select({ "<script>SELECT serviceId, buildVersionId, AVG(responseTime) as responseTime FROM ResultHistory",
            "WHERE serviceId = #{serviceId} AND buildVersionId IN",
            "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
            " #{item} </foreach>", "GROUP BY buildVersionId </script>" })
    @Results({
            @Result(property = "serviceName", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.loadServiceName") ),
            @Result(property = "responseTime", column = "responseTime", jdbcType = JdbcType.INTEGER),
            @Result(property = "buildVersionName", column = "buildVersionId",
                    one = @One(select = "com.softserve.webtester.mapper.BuildVersionMapper.loadBuildVersionName") ) })
    List<ReportDataDTO> loadAvg(@Param(value = "serviceId") int serviceId,
                                @Param(value = "buildVersionId") int[] buildVersionId);

    /**
     * Loads report data with maximum response time for each build version testing run of {@link RequestCollection} 
     * for the service from database by it`s identifier.
     * 
     * @param serviceId identifier of Service
     * @param buildVersionId array of build versions identtifiers
     * @return list of ReportDataDTO instances 
     * @throws DataAccessException
     */
    @Select({ "<script>SELECT serviceId, buildVersionId, MAX(responseTime) as responseTime FROM ResultHistory",
            "WHERE serviceId = #{serviceId} AND buildVersionId IN",
            "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
            " #{item} </foreach>", "GROUP BY buildVersionId </script>" })
    @Results({
            @Result(property = "serviceName", column = "serviceId",
                    one = @One(select = "com.softserve.webtester.mapper.ServiceMapper.loadServiceName") ),
            @Result(property = "responseTime", column = "responseTime", jdbcType = JdbcType.INTEGER),
            @Result(property = "buildVersionName", column = "buildVersionId",
                    one = @One(select = "com.softserve.webtester.mapper.BuildVersionMapper.loadBuildVersionName") ) })
    List<ReportDataDTO> loadMax(@Param(value = "serviceId") int serviceId,
                                @Param(value = "buildVersionId") int[] buildVersionId);

    /**
     * Loads average response time of all testing runs {@link RequestCollection} for the service from database by it`s identifier.
     * 
     * @param serviceId identifier of Service
     * @return average response time of Service testing runs 
     * @throws DataAccessException
     */
    @Select({ "SELECT AVG(responseTime) from (SELECT AVG(responseTime) as responseTime FROM ResultHistory"
            + " WHERE serviceId = #{serviceId} AND buildVersionId in (select b.id from BuildVersion b where b.deleted = 0)"
            + " GROUP BY buildVersionId ORDER BY buildVersionId DESC LIMIT 3) as temp;"
           })
    int loadAverage(int serviceId);

    /**
     * Loads average response time for each build version testing run of {@link RequestCollection} for the service 
     * from database by it`s identifier.
     * 
     * @param serviceId identifier of Service
     * @param buildVersionId array of build versions identtifiers
     * @return list of avarage response time of each build version
     * @throws DataAccessException
     */
    @Select({ "<script>select list.res ", 
        "from (select avg(t1.responseTime) as res, t1.buildVersionId ",
        "from ResultHistory t1 where t1.serviceId = #{serviceId} and t1.buildVersionId in",
        "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
        " #{item} </foreach>", " group by t1.buildVersionId) list right join ",
        "(select b.id from BuildVersion b where b.id in",
        "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
        " #{item} </foreach> ) q ",
        "on q.id=list.buildVersionId;</script>" })
    List<Integer> loadAvgStatistic(@Param(value = "serviceId") int serviceId,
                                   @Param(value = "buildVersionId") int[] buildVersionId);

    /**
     * Loads maximum response time for each build version testing run of {@link RequestCollection} for the service 
     * from database by it`s identifier.
     * 
     * @param serviceId identifier of Service
     * @param buildVersionId array of build versions identtifiers
     * @return list of maximum response time of each build version
     * @throws DataAccessException
     */
    @Select({ "<script>select list.res ", 
        "from (select max(t1.responseTime) as res, t1.buildVersionId ",
        "from ResultHistory t1 where t1.serviceId = #{serviceId} and t1.buildVersionId in",
        "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
        " #{item} </foreach>", " group by t1.buildVersionId) list right join ",
        "(select b.id from BuildVersion b where b.id in",
        "<foreach collection='buildVersionId' item='item' index='index' open='(' separator=',' close=')'>",
        " #{item} </foreach> ) q ",
        "on q.id=list.buildVersionId;</script>" })
    List<Integer> loadMaxStatistic(@Param(value = "serviceId") int serviceId,
                                   @Param(value = "buildVersionId") int[] buildVersionId);

    /**
     * Loads statistic data for {@link Service} from database by it`s identifier.
     * 
     * @param id identifier of Service
     * @return StatisticDataDTO instance
     * @throws DataAccessException
     */
    @Select("select  id, name, sla from Service where id=#{id}")
    @Results({ @Result(property = "serviceName", column = "name", jdbcType = JdbcType.VARCHAR),
               @Result(property = "sla", column = "sla", jdbcType = JdbcType.INTEGER),
               @Result(property = "averageResponseTime", column = "id", 
                       one = @One(select = "com.softserve.webtester.mapper.ReportMapper.loadAverage")) })
    StatisticDataDTO loadStatisticDataDTO(int id);
}
