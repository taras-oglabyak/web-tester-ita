<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" /> 
  rel="stylesheet" />
<link href=<c:url value="/resources/dist/t-rex/css/main.css" /> rel="stylesheet" />

<div class="row">
  <div class="col-md-12">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3>Requests</h3>
        <form:form modelAttribute="requestFilterDTO" method="GET">
          <fieldset>
            <h4>Filters</h4>
            <div class="shift-left">
              <div class="col-md-2">
                <form:input type="text" path="requestNameFilter" class="form-control" placeholder="name includes" />
              </div>

              <div class="col-md-2">
                <form:select path="applicationFilter" items="${applications}" class="form-control select2-multiple" 
                  multiple="multiple" data-placeholder="applications" itemLabel="name" itemValue="id" />
              </div>

              <div class="col-md-2">
                <form:select path="serviceFilter" items="${services}" class="form-control select2-multiple" 
                  multiple="multiple" data-placeholder="services" itemLabel="name" itemValue="id" />
              </div>

              <div class="col-md-2">
                <form:select path="labelFilter" items="${labels}" class="form-control select2-multiple" 
                  multiple="multiple" data-placeholder="labels" itemLabel="name" itemValue="id" />
              </div>

              <div class="col-md-4">
                <a href=<c:url value="/tests/requests/" /> class="btn btn-default">Reset</a>
                <input type="submit" class="btn btn-success" value="Filter" />
              </div>
            </div>
          </fieldset>
        </form:form>

        <div class="row shift-left">
          <label aria-hidden="true">&nbsp;</label>
          <div class="col-md-12">
            <a href=<c:url value="/tests/requests/create" /> class="btn btn-success">Create</a>
            <button id="runAll" class="btn btn-info">Run all</button>
            <button id="runSelected" class="btn btn-info">Run selected</button>
            <button id="deleteSelected" class="btn btn-default">Delete selected</button>
          </div>
        </div>
      </div>
      <div class="panel-body">
        <div class="table-responsive">
          <table class="table table-hover table-bordered table-striped" id="requests">
            <thead>
              <tr>
                <th><input id="selectAll" type="checkbox" title="Select all"></th>
                <th>Name</th>
                <th>Application</th>
                <th>Service</th>
                <th>Endpoint</th>
                <th>Run</th>
                <th class="col-md-1">See results</th>
                <th class="col-md-1">Duplicate</th>
                <th class="col-md-1">Disable</th>
                <th class="col-md-1">Delete</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${requests}" var="request">
                <tr class="dataRow">
                  <td class="td-centered"><input id="${request.id}" type="checkbox" name="operateSelect"></td>
                  <td>
                    <a href=<c:url value="/tests/requests/modify/${request.id}" />>
                      <c:out value="${request.name}" />
                    </a>
                  </td>
                  <td><c:out value="${request.application.name}" /></td>
                  <td><c:out value="${request.service.name}" /></td>
                  <td><c:out value="${request.endpoint}" /></td>
                  <td class="td-centered"><i id="${request.id}" class="run cursorPointer fa fa-play"></i></td>
                  <td class="td-centered"><a href=<c:url value="/results/requests/${request.id}" />>results</a>
                  </td>
                  <td class="td-centered">
                    <i id="${request.id}" class="duplicate cursorPointer fa fa-copy fa-lg"></i>
                  </td>
                  <td class="td-centered"><input id="${request.id}" type="checkbox" name="disableSelect"></td>
                  <td class="td-centered">
                    <i id="${request.id}" class="removeInstance cursorPointer fa fa-trash fa-lg"></i>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="environmentModal" tabindex="-1" role="dialog" aria-labelledby="environmentModalLabel">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="environmentModalLabel">Select environment</h4>
      </div>
      <div class="modal-body">
        <select id="environment" class="form-control">
          <c:forEach items="${environments}" var="environment">
            <option value="${environment.id}">${environment.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="confirmEnvironmentModal" class="btn btn-primary">Start</button>
      </div>
    </div>
  </div>
</div>

<input id="contextPath" type="hidden" value="${pageContext.request.contextPath}" />

<div id="loadingDiv" class="offline">
  <div id="main-frame-error" class="interstitial-wrapper">
    <%@ include file="../t-rex.jsp" %>
  </div>
  <div class="centered">
    <h1>Please, wait</h1>
    <img alt="loading" src=<c:url value="/resources/ajax_loader.gif" /> >
  </div>
</div>

<script src=<c:url value="/resources/dist/js/select2.min.js" />></script>
<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/jquery.dataTables.min.js" />></script>
<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/dataTables.bootstrap.min.js" />></script>
<script src=<c:url value="/resources/dist/t-rex/js/game.js" />></script>

<!-- Main page script -->
<script src=<c:url value="/resources/js/request/requests.js" />></script>