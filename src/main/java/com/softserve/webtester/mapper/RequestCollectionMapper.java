package com.softserve.webtester.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.softserve.webtester.model.RequestCollection;

/**
 * RequestCollectionMapper is MyBatis mapper interface for CRUD operation on {@link RequestCollection} instance in the
 * database.
 * 
 */
@Repository
public interface RequestCollectionMapper {

    /**
     * Saves {@link Request—ollection} instance to database
     *
     * @param requestCollection Request—ollection instance should be saved in the database
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Insert("INSERT INTO RequestCollection (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(RequestCollection requestCollection);

    /**
     * Loads all {@link RequestCollection} instances from the database.
     * This method loads only main information about requestCollection instance.
     * 
     * @return List of RequestCollection instances
     * @throws DataAccessException
     */
    @Select({ "<script>SELECT DISTINCT rc.id, rc.name, rc.description FROM RequestCollection rc ",
            "<if test='labelFilter!=null and labelFilter.length>0'>LEFT JOIN RequestCollection_Label rcl "
            + "ON rc.id=rcl.requestCollectionId </if>",
            "WHERE rc.id > 0",
            "<if test='requestCollectionNameFilter!=null and requestCollectionNameFilter!=\"\"'>",
            " AND lower(rc.name) LIKE CONCAT('%', lower(#{requestCollectionNameFilter}),'%')</if>",
            "<if test='labelFilter!=null and labelFilter.length>0'> AND rcl.labelId IN",
            "<foreach collection='labelFilter' item='item' index='index' open='(' separator=',' close=')'>",
            " #{item} </foreach></if>", "</script>" })
    @Results({ @Result(property = "id", column = "ID", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR),
            @Result(property = "labels", column = "id", 
                    many = @Many(select = "com.softserve.webtester.mapper.LabelMapper.loadByRequestCollectionId") ) })
    List<RequestCollection> loadAll(@Param(value = "requestCollectionNameFilter") String requestCollectionNameFilter,
            @Param(value = "labelFilter") int[] labelFilter);

    /**
     * Loads {@link RequestCollection} instance from database by its identifier.
     * 
     * @param id identifier of RequestCollection instance
     * @return RequestCollection instance
     * @throws DataAccessException
     */
    @Select("SELECT * FROM RequestCollection WHERE id = #{id}")
    @Results({ @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR),
            @Result(property = "labels", column = "id", many = @Many(select = "com.softserve.webtester.mapper.LabelMapper.loadByRequestCollectionId") ),
            @Result(property = "requests", column = "id", many = @Many(select = "com.softserve.webtester.mapper.RequestMapper.loadByRequestCollectionId") ) })
    RequestCollection load(int id);

    /**
     * Updates {@link RequestCollection} instances in the database.
     * 
     * @param requestCollection RequestCollection instance should be updated
     * @return number of rows affected by the statement
     * @throws DataAccessException
     */
    @Update("UPDATE RequestCollection SET name = #{name}, description = #{description} WHERE id = #{id}")
    void update(RequestCollection requestCollection);    

    /**
     * Deletes {@link RequestCollection} instance from the database.
     * 
     * @param requestCollectionIdArray identifier of RequestCollection instances should be deleted
     * @throws DataAccessException
     */
    @Delete("<script>DELETE FROM RequestCollection WHERE id IN "
            + "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>"
            + "#{item}</foreach></script>")
    void deleteRequestCollections(@Param("list") int[] requestCollectionIdArray);
    
    /**
     * Checks the unique of requestCollection's name.
     * 
     * @param name name of {@link RequestCollection} should be checked
     * @param exclusionId id of {@link RequestCollection} should be excluded
     * @return true, if name is unique
     */
    @Select("SELECT IF(count(*) > 0, false, true) FROM RequestCollection WHERE name = #{name} AND id != #{exclusionId}")
    boolean isRequestCollectionNameFree(@Param("name") String name, @Param("exclusionId") int exclusionId);

}
