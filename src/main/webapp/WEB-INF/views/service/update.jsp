<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<head>
  <c:set var="title" value="Create" />
  <c:if test="${isUpdate}">
    <c:set var="title" value="Update" />
  </c:if>

  <title>${title}</title>
</head>
  <div class="row">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3>${title}</h3>
      </div>
      <div class="panel body">
        <div class="row"></div>
        <form:form cssClass="form-horizontal" action="" method="POST" modelAttribute="service">
          <form:hidden path="id" />
          <fieldset>
            <div class="row">
              <div class="form-group">
              <div class="row">
                <form:label path="name" cssClass="col-sm-2 control-label">
                  <p class="text-left">Name: *</p>
                </form:label>
                <div class="col-sm-4">
                  <form:input cssClass="form-control" path="name" cssErrorClass="error" />
                  <form:errors path="name" cssClass="help-block with-errors" />
                </div>
                </div>
              </div>
              <div class="form-group">
              <div class="row">
                <form:label path="description" cssClass="col-sm-2 control-label">
                  <p class="text-left">Description: *</p>
                </form:label>
                <div class="col-sm-4">
                  <form:input cssClass="form-control" path="description" cssErrorClass="error" />
                  <form:errors path="description" cssClass="help-block with-errors" />
                </div>
              </div>
              </div>
              <div class="form-group">
              <div class="row">
                <form:label path="description" cssClass="col-sm-2 control-label">
                  <p class="text-left">SLA: *</p>
                </form:label>
                <div class="col-sm-2">
                  <form:input cssClass="form-control" path="sla" cssErrorClass="error" />
                  <form:errors path="sla" cssClass="help-block with-errors" />
                </div>
              </div>
              </div>
              <div class="row">
                <button type="submit" class="btn btn-success">Save</button>
                <button type="button" class="btn btn-danger" onclick="location.reload();">Reset</button>
                <button type="button" class="btn btn-default" onclick="location.href='<c:url value="/configuration/services "/>'">Cancel</button>
              </div>
            </div>
          </fieldset>
        </form:form>
      </div>
    </div>
  </div>