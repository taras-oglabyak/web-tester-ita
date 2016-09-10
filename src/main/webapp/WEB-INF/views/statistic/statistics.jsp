<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />
<link href="<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" />" 
  rel="stylesheet" />

<div class="row">
  <div class="col-md-12">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3>Statistic</h3>
        <form:form modelAttribute="statisticFilterDTO" method="GET">
          <fieldset>
            <div class="shift-left">

              <div class="col-md-4">
                <label for="serviceId" class="control-label">Select service for statistic generation</label>
                <form:select path="serviceId" items="${serviceName}" class="form-control select2-multiple" multiple="multiple" data-placeholder="service name"
                  itemLabel="name" itemValue="id" />
                <form:errors path="serviceId" cssClass="help-block with-errors" />
              </div>

              <div class="col-md-4">
                <label for="buildVersionId" class="control-label">Select build version</label>
                <form:select path="buildVersionId" items="${buildVersions}" class="form-control select2-multiple" multiple="multiple" data-placeholder="Build version name"
                  itemLabel="name" itemValue="id" />
                <form:errors path="buildVersionId" cssClass="help-block with-errors" />
              </div>
              
              <div class="col-md-4">
                <label for="responseTimeFilterMarker" class="control-label">Select response time type for report</label> 
                <form:select path="responseTimeFilterMarker" class="form-control" items="${responseTimeType}" 
                  itemLabel="text"/>
              </div>

            </div>
          </fieldset>
          <div>
            <label aria-hidden="true">&nbsp;</label>
            <div>
              <a href="<c:url value="/reports/statistic/ " />" class="btn btn-default">Reset</a>
              <input type="submit" class="btn btn-success" value="Generate" />
              <button id="exportXls" class="btn btn-default">Export to excel</button>
            </div>
          </div>
        </form:form>
      </div>

      <c:if test="${statistics!=null}">
        <div class="panel-body">
          <div class="table-responsive">
            <table class="table table-hover table-bordered table-striped" id="statistics">
              <thead>
                <tr>
                  <th rowspan="2" class="col-md-2">Service Name</th>
                  <th rowspan="2" class="col-md-1">SLA</th>
                  <th colspan="${fn:length(statisticsBuildVersions)}" class="col-md">Response time for buildVersions</th>
                  <th rowspan="2" class="col-md-2">Average for the last three releases</th>
                </tr>
                <tr>
                  <c:forEach items="${statisticsBuildVersions}" var="statisticsBuildVersion">
                    <th class="col-md-1">${statisticsBuildVersion}</th>
                  </c:forEach>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${statistics}" var="statistic">
                  <tr class="serviceStatistic">
                    <td class="td-centered">
                      <c:out value="${statistic.serviceName}" />
                    </td>
                    <td class="td-centered">
                      <c:out value="${statistic.sla}" />
                    </td>
                    <c:forEach items="${statistic.responseTimes}" var="responseTime">
                      <td class="td-centered data"><b>${responseTime}</b></td>
                    </c:forEach>
                    <td class="td-centered">
                      <c:out value="${statistic.averageResponseTime}"></c:out>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </c:if>
    </div>
  </div>
</div>

<script src=<c:url value="/resources/dist/js/select2.min.js" />></script>

<!-- Main page script -->
<script src=<c:url value="/resources/js/statistic/statistics.js" />></script>