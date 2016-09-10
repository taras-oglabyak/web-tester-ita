<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" />
  rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/graphic.css" /> rel="stylesheet" />

<div class="row">
  <div class="col-md-12">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3>Graphics</h3>
        <form:form modelAttribute="reportFilterDTO" method="GET">
          <fieldset>
            <div class="shift-left">

              <div class="col-md-4">
                <label for="serviceId" class="control-label">Select service for statistic generation</label>
                <form:select path="serviceId" id="Service" items="${serviceName}" class="form-control"
                             itemValue="id" itemLabel="name" />
                <form:errors path="serviceId" cssClass="help-block with-errors" />
              </div>

              <div class="col-md-4">
                <label for="buildVersionId" class="control-label">Select build version</label>
                <form:select path="buildVersionId" id="BuildVersion" onchange="enableDisableButton()" items="${buildVersions}" class="form-control select2-multiple"
                  multiple="multiple" data-placeholder="Build version name" itemValue="id" itemLabel="name" />
                <form:errors path="buildVersionId" cssClass="help-block with-errors" />
              </div>
                        
              <div class="col-md-4">
                <label for="responseTimeFilterMarker" class="control-label">Select response time type for report</label> 
                <form:select path="responseTimeFilterMarker" id="Time" class="form-control" items="${responseTimeType}"
                  itemLabel="text"/>
              </div>
              
            </div>
          </fieldset>
          
          <div>
            <label aria-hidden="true">&nbsp;</label>
            <div>
              <a href="<c:url value="/reports/graphics/" />" class="btn btn-default">Reset</a>
              <input type="submit" disabled="disabled" title="Please fill in all the required fields first"
                     id="Submit" class="btn btn-success" value="Generate" />
            </div>
          </div>
        </form:form>
      </div>

      <div class="panel-body">
        <div class="inner">
          <c:if test="${not empty graphicData}">
            <h4 >Performance by ${reportFilterDTO.getResponseTimeFilterMarker() == "AVERAGE"?'Average':'Maximum'} value of response time</h4>
          </c:if>
        </div>
      </div>

      <div id="outer">
        <div class="inner">
          <canvas id="canvas" height="450" width="600"></canvas>
        </div>
      </div>

      <div class="inner2">
        <c:if test="${not empty graphicData}">
          <h5>Build Version</h5>
        </c:if>
      </div>
    </div>
  </div>
</div>

<div id="data" class="hidden">
  <c:forEach var="row" items="${graphicData}">
    <span class="x">${row.buildVersionName}</span>
    <span class="y">${row.responseTime}</span>
  </c:forEach>
  <span class="sla">${sla}</span>
</div>

<script src=<c:url value="/resources/js/graphic/graphic.js" />></script>
<script src=<c:url value="/resources/js/graphic/Chart.js" />></script>
<script src=<c:url value="/resources/dist/js/select2.min.js" />></script>
