package com.softserve.webtester.model;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO class represents Environment object which stores database connection
 * properties for request run.
 *
 */
public class Environment implements Serializable {

    private static final long serialVersionUID = 2341324368509837076L;

    /**
     * Auto generated unique primary key
     */
    private int id;

    /**
     * Unique name of environment entity
     */
    @NotBlank
    @Size(max = 75)
    private String name;

    /**
     * Address of the host which will be tested
     */
    @NotBlank
    @Size(max = 75)
    @Pattern(regexp = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String baseUrl;

    /**
     * Database type
     */
    @NotNull
    private EnvironmentDbType dbType;

    /**
     * Address of the server with database to perform queries for variables and
     * db validation.
     */
    @NotBlank
    @Size(max = 100)
    private String dbUrl;

    /**
     * Port of the database
     */
    @NotNull
    private int dbPort;

    /**
     * Name of the database to which the connection is established
     */
    @NotBlank
    @Size(max = 75)
    private String dbName;

    /**
     * Username to perform login to the database
     */
    @NotBlank
    @Size(max = 75)
    private String dbUsername;

    /**
     * Password for the specified username
     */
    @NotBlank
    @Size(max = 75)
    private String dbPassword;

    /**
     * Value which will be used to multiply response time of the each request
     */

    @DecimalMin("0.1")
    private float timeMultiplier;

    /**
     * Flag which will show that environment entity is deleted
     */
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public EnvironmentDbType getDbType() {
        return dbType;
    }

    public void setDbType(EnvironmentDbType dbType) {
        this.dbType = dbType;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public float getTimeMultiplier() {
        return timeMultiplier;
    }

    public void setTimeMultiplier(float timeMultiplier) {
        this.timeMultiplier = timeMultiplier;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}