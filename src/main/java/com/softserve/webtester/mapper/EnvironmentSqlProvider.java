package com.softserve.webtester.mapper;

import org.apache.ibatis.jdbc.SQL;

/**
 * Provides Database SQL queries for CRUD operations for {@link Environment} object
 *
 */
public class EnvironmentSqlProvider {

    /**
     * Database table which stores {@link Environment} entity
     */
    private static final String TABLE_NAME = "Environment";

    /**
     * @return SQL insert query for saving {@link Environment} instance to database
     */
    public String insertSql() {
        return new SQL() {
            {
                INSERT_INTO(TABLE_NAME);
                VALUES("name, baseUrl", "#{name}, #{baseUrl}");
                VALUES("dbType, dbUrl, dbPort", "#{dbType}, #{dbUrl}, #{dbPort}");
                VALUES("dbName, dbUsername, dbPassword", "#{dbName}, #{dbUsername}, #{dbPassword}");
                VALUES("timeMultiplier", "#{timeMultiplier}");
            }
        }.toString();
    }

    /**
     * @return SQL query for selecting {@link Environment} entity, which has not been
     *         marked as "deleted"
     */
    public String selectSql() {
        return new SQL() {
            {
                SELECT("id, name, baseUrl, dbType, dbUrl, dbPort, dbName");
                SELECT("dbUsername, dbPassword, timeMultiplier, deleted");
                FROM(TABLE_NAME);
                WHERE("id = #{id}");
                WHERE("deleted = b'0'");
            }
        }.toString();
    }

    /**
     * @return SQL query for selecting all {@link Environment} entities, which have not
     *         been marked as "deleted"
     */
    public String selectAllSql() {
        return new SQL() {
            {
                SELECT("id, name, baseUrl, dbType, dbUrl, dbPort, dbName");
                SELECT("dbUsername, dbPassword, timeMultiplier, deleted");
                FROM(TABLE_NAME);
                WHERE("deleted = b'0'");
            }
        }.toString();
    }

    /**
     * @return SQL query for updating {@link Environment} entity in database
     */
    public String updateSql() {
        return new SQL() {
            {
                UPDATE(TABLE_NAME);
                SET("name = #{name}, baseUrl = #{baseUrl}");
                SET("dbType = #{dbType}, dbUrl = #{dbUrl}");
                SET("dbPort = #{dbPort}, dbName = #{dbName}");
                SET("dbUsername = #{dbUsername}, dbPassword = #{dbPassword}");
                SET("timeMultiplier = #{timeMultiplier}");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    /**
     * @return SQL query for marking as "deleted" {@link Environment} entity in database
     */
    public String deleteSql() {
        return new SQL() {
            {
                UPDATE(TABLE_NAME);
                SET("deleted = b'1'");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    /**
     * @return SQL query for checking the unique of {@link Environment} property "name"
     */
    public String isNameFree() {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(TABLE_NAME);
                WHERE("name = #{name}");
                WHERE("id != #{id}");
            }
        }.toString();
    }
}
