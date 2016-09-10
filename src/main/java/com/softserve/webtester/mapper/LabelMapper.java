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

import com.softserve.webtester.model.Label;
import com.softserve.webtester.model.Request;
import com.softserve.webtester.model.RequestCollection;
import com.softserve.webtester.model.ResultHistory;

/**
 * LabelMapper is MyBatis mapper interface for CRUD operation on {@link Label} instance in the database.
 *
 * @author Anton Mykytiuk and Taras Oglabyak
 */
@Repository
public interface LabelMapper {

    /**
     * Saves {@link Label} instance to database.
     *
     * @param label instance of class Label to store in the DB
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Insert("INSERT INTO Label (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveLabel(Label label);

    /**
     * Loads {@link Label} instance from database by its identifier.
     *
     * @param id identifier of specific Label instance which stored in the DB
     * @return Label instance
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT id, name FROM Label WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    Label loadLabelById(int id);

    /**
     * Loads all {@link Label} instances from the database.
     *
     * @return List of Label instances
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT * from Label")
    @Results(value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    List<Label> loadAllLabels();

    /**
     * Updates {@link Label} instance in the database.
     *
     * @param label instance should be updated
     * @return id of label instance which was updated
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Update("UPDATE Label SET name = #{name} WHERE id = #{id}")
    int updateLabel(Label label);


    /**
     * Deletes {@link Label} instance from the database by id.
     *
     * @param id of label instance should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Delete("DELETE FROM Label WHERE id = #{id}")
    int deleteLabel(int id);

    /**
     * Checks the unique of label's name.
     *
     * @param name name of {@link Label} should be checked
     * @param exclusionId id of {@link Label} should be excluded
     * @return true, if name is unique
     */
    @Select("SELECT IF(count(*) > 0, false, true) FROM Label WHERE name = #{name} AND id != #{exclusionId}")
    boolean isLabelNameFree(@Param("name") String name, @Param("exclusionId") int exclusionId);
    
    /**
     * Saves records to {@code Request_Label} junction table in the database by the Request.<br>
     * Using SQL batch insert this method saves all RequestId - LabelId relations in the database.
     * 
     * @param request {@link Request} instance, whose dbValidations should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Insert("<script>INSERT INTO Request_Label(requestId, labelId) VALUES "
	    + "<foreach collection='labels' item='label' separator=','> "
	    + "(#{id}, #{label.id}) </foreach></script>")
    int saveByRequest(Request request);

    /**
     * Saves records to {@code ResultHistory_Label} junction table in the database by the Request.<br>
     * Using SQL batch insert this method saves all ResultHistoryId - LabelId relations in the database.
     *
     * @param resultHistory {@link ResultHistory} instance should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Insert("<script>INSERT INTO ResultHistory_Label(resultHistoryId, labelId) VALUES "
            + "<foreach collection='labels' item='label' separator=','> "
            + "(#{id}, #{label.id}) </foreach></script>")
    int saveByResultHistory(ResultHistory resultHistory);
    
    /**
     * Saves records to {@code RequestCollection_Label} junction table in the database by the Request.<br>
     * Using SQL batch insert this method saves all RequestCollectionId - LabelId relations in the database.
     * 
     * @param requestCollection {@link RequestCollection} instance should be saved
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Insert("<script>INSERT INTO RequestCollection_Label(requestCollectionId, labelId) VALUES "
	    + "<foreach collection='labels' item='label' separator=','> "
	    + "(#{id}, #{label.id}) </foreach></script>")
    int saveByRequestCollection(RequestCollection requestCollection);
    
    /**
     * Loads all {@link Label} instances for the Request from the database.
     * 
     * @param id identifier of {@link Request} instance, whose labels should be loaded
     * @return List of Label instances
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT l.id, l.name FROM Label l INNER JOIN Request_Label rl ON rl.labelId = l.id "
    	    + "WHERE rl.requestId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
               @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR) })
    List<Label> loadByRequestId(int id);

    @Select("SELECT l.id, l.name FROM Label l INNER JOIN ResultHistory_Label rhl ON rhl.labelId = l.id "
            + "WHERE rhl.resultHistoryId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    List<Label> loadByResultHistoryId(int id);
    
    /**
     * Loads all {@link Label} instances for the RequestCollection from the database.
     * 
     * @param id identifier of {@link RequestCollection} instance, whose labels should be loaded
     * @return List of Label instances
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Select("SELECT l.id, l.name FROM Label l INNER JOIN RequestCollection_Label rcl ON rcl.labelId = l.id "
    	    + "WHERE rcl.requestCollectionId = #{id}")
    @Results({ @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
	       @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR)
    })
    List<Label> loadByRequestCollectionId(int id);
    
    /**
     * Deletes all RequestId - LabelId relations from {@code Request_Label} junction table in the database for the
     * Request instance using its identifier.
     * 
     * @param id identifier of {@link Request} instance, whose labels should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Delete("DELETE FROM Request_Label WHERE requestId = #{id}")
    int deleteByRequestId(int id);
    
    /**
     * Deletes all RequestCollectionId - LabelId relations from {@code Request_Label} junction table in the database for the
     * RequestCollection instance using its identifier.
     * 
     * @param id identifier of {@link RequestCollection} instance, whose labels should be deleted
     * @return number of rows affected by the statement
     * @throws DataAccessException appear when there is no access to the DB
     */
    @Delete("DELETE FROM RequestCollection_Label WHERE requestCollectionId = #{id}")
    int deleteByRequestCollectionId(int id);

}