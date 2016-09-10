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
import org.apache.ibatis.type.JdbcType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.softserve.webtester.model.BuildVersion;

/**
 * BuildVersionMapper is MyBatis mapper interface for CRUD operation on {@link BuildVersion} instance in the database.
 *
 * @author Anton Mykytiuk
 */
@Repository
public interface BuildVersionMapper {

    /**
     * Saves {@link BuildVersion} instance to database.
     *
     * @param buildVersion instance of class BuildVersion to store in the DB
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Insert("INSERT INTO BuildVersion (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveBuildVersion(BuildVersion buildVersion);

    /**
     * Loads {@link BuildVersion} instance from database by it's identifier.
     *
     * @param id identifier of specific BuildVersion instance which stored in the DB
     * @return BuildVersion instance
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT id, name, description FROM BuildVersion WHERE id = #{id} AND deleted = 0")
    @Results({ @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR)
    })
    BuildVersion loadBuildVersionById(int id);

    /**
     * Loads all {@link BuildVersion} instances from the database which aren't marked as "deleted".
     *
     * @return Set of BuildVersion instances
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT id, name, description from BuildVersion WHERE deleted = 0")
    @Results({ @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "description", column = "description", jdbcType = JdbcType.VARCHAR)
    })
    List<BuildVersion> loadAllBuildVersions();

    /**
     * Updates {@link BuildVersion} instance in the database.
     *
     * @param buildVersion instance should be updated
     * @return id of buildVersion instance should be updated
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Update("UPDATE BuildVersion SET name = #{name}, description = #{description}, " +
            "deleted = #{deleted} WHERE id = #{id}")
    int updateBuildVersion(BuildVersion buildVersion);

    /**
     * Deletes (Soft Delete) {@link BuildVersion} instance from the database by id.
     *
     * @param id of buildVersion instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Update("UPDATE BuildVersion SET deleted = 1 WHERE id = #{id}")
    int deleteBuildVersion(int id);

    /**
     * Deletes {@link BuildVersion} instance from the database by id. This method will not be used
     * @param id of buildVersion instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Delete("DELETE FROM BuildVersion WHERE id = #{id}")
    int hardDeleteBuildVersion(int id);

    /**
     * Checks the unique of build version's name.
     *
     * @param name name of {@link BuildVersion} should be checked
     * @param exclusionId id of {@link BuildVersion} should be excluded
     * @return true, if name is unique
     */
    @Select("SELECT IF(count(*) > 0, false, true) FROM BuildVersion WHERE name = #{name} AND id != #{exclusionId}")
    boolean isBuildVersionNameFree(@Param("name") String name, @Param("exclusionId") int exclusionId);

    /**
     * Loads BuildVersion name from database by its identifier
     * @param id
     * @return BuildVersion name
     * @throws DataAccessException
     */
    @Select("SELECT name FROM BuildVersion WHERE id = #{id} AND deleted = 0")
    String loadBuildVersionName(int id);
}