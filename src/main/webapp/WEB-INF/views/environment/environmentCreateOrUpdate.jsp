<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />

<div class="panel panel-default">
  <div class="panel-heading">
    <h3>
      <c:out value="${pageTask}" />
      environment
    </h3>
  </div>
  <div class="panel body">
    <div class="row">
      <form:form class="form-horizontal" role="form" method="POST" modelAttribute="environment" action="">
        <fieldset>
          <div class="row">
            <div class="form-group">
              <label for="name" class="col-sm-2 control-label text-left">
                <p class="text-left">Name: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="name" type="text" class="form-control" id="name" placeholder="Name"
                  cssErrorClass="error" />
                <form:errors path="name" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="baseUrl" class="col-sm-2 control-label">
                <p class="text-left">Base URL: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="baseUrl" type="text" class="form-control" id="baseUrl" placeholder="Base URL"
                  cssErrorClass="error" />
                <form:errors path="baseUrl" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbType" class="col-sm-2 control-label">
                <p class="text-left">Database Type: *</p>
              </label>
              <div class="col-sm-4">
                <form:select path="dbType" class="form-control" id="dbType" items="${dbTypes}" cssErrorClass="error" />
                <form:errors path="dbType" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbUrl" class="col-sm-2 control-label">
                <p class="text-left">Database URL: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="dbUrl" type="text" class="form-control" id="dbUrl" placeholder="Database URL"
                  cssErrorClass="error" />
                <form:errors path="dbUrl" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbPort" class="col-sm-2 control-label">
                <p class="text-left">Database port: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="dbPort" type="number" class="form-control" id="dbPort" placeholder="Database Port"
                  cssErrorClass="error" />
                <form:errors path="dbPort" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbName" class="col-sm-2 control-label">
                <p class="text-left">Database name: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="dbName" type="text" class="form-control" id="dbName" placeholder="Database name"
                  cssErrorClass="error" />
                <form:errors path="dbName" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbUsername" class="col-sm-2 control-label">
                <p class="text-left">Database username: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="dbUsername" type="text" class="form-control" id="dbUsername"
                  placeholder="Database username" cssErrorClass="error" />
                <form:errors path="dbUsername" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="dbPassword" class="col-sm-2 control-label">
                <p class="text-left">Database password: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="dbPassword" type="text" class="form-control" id="dbPassword"
                  placeholder="Database password" cssErrorClass="error" />
                <form:errors path="dbPassword" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <label for="timeMultiplier" class="col-sm-2 control-label">
                <p class="text-left">Response time multiplier: *</p>
              </label>
              <div class="col-sm-4">
                <form:input path="timeMultiplier" type="number" step="any" class="form-control" id="timeMultiplier"
                  placeholder="2" cssErrorClass="error" />
                <form:errors path="timeMultiplier" cssClass="help-block with-errors" />
              </div>
            </div>
            <div class="form-group">
              <div class="row">
                <div class="col-sm-12">
                  <button type="submit" class="btn btn-success">
                    <c:out value="${pageTask}" />
                  </button>
                  <c:url value="/configuration/environments" var="environments" />
                  <c:choose>
                    <c:when test="${not empty id}">
                      <c:url value="/configuration/environments/modify/${id}" var="resetButton" />
                    </c:when>
                    <c:otherwise>
                      <c:url value="/configuration/environments/create" var="resetButton" />
                    </c:otherwise>
                  </c:choose>
                  <button type="reset" class="btn btn-danger" onclick="location.href='${resetButton}'">Reset</button>
                  <button type="button" class="btn btn-default" onclick="location.href='${environments}'">Cancel</button>
                </div>
              </div>
            </div>
          </div>
        </fieldset>
      </form:form>
    </div>
  </div>
</div>
