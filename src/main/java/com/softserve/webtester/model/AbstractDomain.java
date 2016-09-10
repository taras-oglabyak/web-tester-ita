package com.softserve.webtester.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * The AbstractDomain abstract class represents {@code AbstractDomain} entity from which inherited
 * classes like Application, Service, BuildVersion.
 *
 * @author Anton Mykytiuk
 */

public class AbstractDomain {

    protected int id;

    @NotBlank
    @Size(max = 75)
    protected String name;

    @NotBlank
    @Size(max = 255)
    protected String description;

    protected boolean deleted;

    public AbstractDomain(int id, String name, String description, boolean deleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
    }

    public AbstractDomain() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
