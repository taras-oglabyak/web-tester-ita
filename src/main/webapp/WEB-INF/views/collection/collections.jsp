<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/resources/dist/css/select2.min.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/dist/css/select2-bootstrap.css" />" rel="stylesheet" />
<link href="<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" />" 
  rel="stylesheet" />
<link href=<c:url value="/resources/dist/t-rex/css/main.css" /> rel="stylesheet" />

<div class="row">
  <div class="col-md-12">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3>Collections</h3>
        <form:form modelAttribute="requestCollectionFilterDTO" method="GET">
          <fieldset>
            <h4>Filters</h4>
            <div class="shift-left">
            <div class="col-md-2">
              <form:input type="text" path="requestCollectionNameFilter" class="form-control" 
               placeholder="Collection name" />
            </div>

            <div class="col-md-2">
              <form:select path="labelFilter" items="${labels}" class="form-control select2-multiple" 
               multiple="multiple" data-placeholder="Labels" itemLabel="name" itemValue="id" />
            </div>

            <div class="col-md-4">
              <div>
                <a href=<c:url value="/tests/collections/" /> class="btn btn-default">Reset</a>
                <input type="submit" class="btn btn-success" value="Filter" />
              </div>
            </div>
            </div>
          </fieldset>
        </form:form>

        <div class="row shift-left">
          <span aria-hidden="true">&nbsp;</span>
          <div class="col-md-12">
            <a href=<c:url value="/tests/collections/newCollection" /> class="btn btn-success">Create</a>
            <button id="runAll" class="btn btn-info">Run all</button>
            <button id="runSelected" class="btn btn-info">Run selected</button>
            <button id="deleteSelected" class="btn btn-default">Delete selected</button>
          </div>
        </div>
      </div>
      <div class="panel-body">
        <div class="table-responsive">
          <table class="table table-hover table-bordered table-striped" id="collections">
            <thead>
              <tr>
                <th><input id="selectAll" class="cursorPointer" type="checkbox" title="Select all"></th>
                <th class="col-md-2">Name</th>
                <th class="col-md-4">Description</th>
                <th class="col-md-2">Labels</th>
                <th class="col-md-1">Run</th>
                <th class="col-md-1">See results</th>
                <th class="col-md-1">Disable</th>
                <th class="col-md-1">Delete</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${collectionList}" var="requestCollection">
                <tr class="dataRow">
                  <td class="td-centered"><input id="${requestCollection.id}" class="cursorPointer" type="checkbox" 
                   name="operateSelect"></td>
                  <td><a href=<c:url value="/tests/collections/modify/${requestCollection.id}"/>> 
                   ${requestCollection.name}</a>
                  </td>
                  <td>
                    <c:out value="${requestCollection.description}"></c:out>
                  </td>
                  <td>
                    <c:forEach items="${requestCollection.labels}" var="label">
                      <span class="label label-info">${label.name}</span>
                    </c:forEach>
                  </td>
                  <td class="td-centered">
                    <i id="${requestCollection.id}" class="run fa fa-play cursorPointer"></i>
                  </td>
                  <td class="td-centered">
                    <a href=<c:url value="/results/requestCollections/${requestCollection.id}"/>>results</a>
                  </td>
                  <td class="td-centered">
                    <input id=<c:out value="${requestCollection.id}" /> class="cursorPointer" type="checkbox" 
                     name="disableSelect">
                  </td>
                  <td id="${requestCollection.id}" class="removeInstance td-centered">
                    <i class="fa fa-trash fa-lg cursorPointer"></i>
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

<div class="modal fade" id="runOptions" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Run Options</h4>
      </div>
      <div class="modal-body">
        <div>
          <h4 class="modal-title">Select environment</h4>
          <select id="environment" class="form-control">
          <c:forEach items="${environments}" var="environment">
            <option value="${environment.id}">${environment.name}</option>
          </c:forEach>
        </select>
        </div>
        <span aria-hidden="true">&nbsp;</span>
        <div>
          <h4 class="modal-title">Select build version (optional)</h4>
          <select id="buildVersion" class="form-control">
          <option value="0" label="Run without build version"></option>
          <c:forEach items="${buildVersions}" var="buildVersion">
            <option value="${buildVersion.id}">${buildVersion.name}</option>
          </c:forEach>
        </select>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="confirmRunOptions" class="btn btn-primary">Start</button>
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
<script src=<c:url value="/resources/js/collection/collections.js" />></script>