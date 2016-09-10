<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/css/result.css" /> rel="stylesheet" />
<script src=<c:url value="/resources/dist/js/codemirror-5.12/lib/codemirror.js" />></script>
<script src=<c:url value="/resources/dist/js/codemirror-5.12/mode/javascript/javascript.js" />></script>
<link href=<c:url value="/resources/dist/js/codemirror-5.12/lib/codemirror.css" /> rel="stylesheet" />
<link href=<c:url value="/resources/dist/js/codemirror-5.12/addon/merge/merge.css" /> rel="stylesheet" />
<script src=<c:url value="/resources/dist/js/codemirror-5.12/mode/xml/xml.js" />></script>
<script src=<c:url value="/resources/dist/js/codemirror-5.12/mode/css/css.js" />></script>
<script src=<c:url value="/resources/dist/js/codemirror-5.12/mode/htmlmixed/htmlmixed.js" />></script>
<script src=<c:url value="https://cdnjs.cloudflare.com/ajax/libs/diff_match_patch/20121119/diff_match_patch.js" />></script>
<script src=<c:url value="/resources/dist/js/codemirror-5.12/addon/merge/merge.js" />></script>
<script src=<c:url value="/resources/dist/js/code_mirror_custom.js" />></script>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3>Result details for request with ID: <c:out value="${result.request.id}" /></h3>
  </div>
  <div class="panel-body">
    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Status</b></p>
        </div>
        <div class="col-sm-9">
          ${(result.status==true)?'pass':'fail'}
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Request Name</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.requestName}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Request Description</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.requestDescription}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Application Name</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.application.name}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Service Name</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.service.name}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Labels</b></p>
        </div>
        <div class="col-sm-9">
          <c:forEach items="${result.labels}" var="result">
            <span class='label label-info'/>${result.name}</span>
          </c:forEach>
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Start Time</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.timeStart}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>RunId</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.runId}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>URL</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.url}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Request Headers</b></p>
        </div>
        <div class="col-md-6 elementContainer">
          <div class="panel panel-default">
            <div class="table-responsive">
              <table class="table table-hover table-bordered table-condensed text-center table-striped panel-body" id="headers">
                <thead id="head1">
                <tr>
                  <th class="col-xs-4 col-sm-4 col-md-4">Name</th>
                  <th class="col-xs-8 col-sm-8 col-md-8">Value</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${result.headerHistories}" var="result">
                  <tr>
                    <td class="td-left"><c:out value="${result.name}" /></td>
                    <td class="td-left"><c:out value="${result.value}" /></td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <c:if test="${!(result.request.requestMethod == 'GET')}">
      <div class="col-md-12">
        <div class="row">
          <div class="col-sm-3">
            <p><b>Request Body</b></p>
          </div>
          <div class="table-responsive">
            <div class="col-sm-9">
              <c:out value="${result.requestBody}" />
            </div>
          </div>
        </div>
      </div>
    </c:if>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Status Line</b></p>
        </div>
        <div class="table-responsive">
          <div class="col-sm-9">
            <c:out value="${result.statusLine}" />
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Response Type</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.responseType}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Expected Response Time</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.expectedResponseTime}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Actual Response Time</b></p>
        </div>
        <div class="col-sm-9">
          <c:out value="${result.responseTime}" />
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>Message</b></p>
        </div>
        <div class="table-responsive">
          <div class="col-sm-9">
            <c:out value="${result.message}" />
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-12">
      <div class="row">
        <div class="col-sm-3">
          <p><b>DB Validation Results</b></p>
        </div>
        <div class="col-md-6 elementContainer">
          <div class="panel panel-default">
            <div class="table-responsive">
              <table class="table table-hover table-bordered table-condensed text-center table-striped panel-body" id="headers">
                <thead id="head">
                <tr>
                  <th class="col-xs-6 col-sm-6 col-md-6">SQL Query</th>
                  <th class="col-xs-2 col-sm-2 col-md-2">Expected Value</th>
                  <th class="col-xs-2 col-sm-2 col-md-2">Actual Value</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${result.dbValidationHistories}" var="result">
                  <tr>
                    <td><textarea readonly class="form-control" rows="1" name="text"><c:out value="${result.sqlQuery}" /></textarea></td>
                    <td class="td-left"><c:out value="${result.expectedValue}" /></td>
                    <td class="td-left"><c:out value="${result.actualValue}" /></td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-12 elementContainer">
        <div class="panel panel-default">
          <div class="panel-heading">
            <label>Response Validation Results</label>
          </div>
          <div class="table-responsive">
            <table class="table table-hover table-bordered table-condensed text-center table-striped panel-body" id="headers">
              <thead>
              <tr>
                <th class="col-xs-4 col-sm-6 col-md-6">Expected Response</th>
                <th class="col-xs-4 col-sm-6 col-md-6">Actual Response</th>
              </tr>
              </thead>
              <div id="view"></div>
            </table>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <table class="table-hover">
          <tr id ="textar">
            <td>
              <table class="table-bordered">
                <tr>
                  <td><textarea type=hidden id="code1" name="code" textarea rows="10" cols="50" name="text">${result.expectedResponse}</textarea></td>
                  <td><textarea type=hidden id="code2" name="code" textarea rows="10" cols="50" name="text">${result.actualResponse}</textarea></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>