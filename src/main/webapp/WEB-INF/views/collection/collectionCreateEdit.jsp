<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/resources/dist/css/select2.min.css" />" rel="stylesheet" />

<link href="<c:url value="/resources/dist/css/select2-bootstrap.css" />" rel="stylesheet" />

<link href="<c:url value="/resources/dist/css/multi-select.css" />" rel="stylesheet" />

<div class="panel panel-default">
  <div class="panel-heading">
    <h3>Collection Editing</h3>
  </div>
  <div class="panel body">
    <div class="row">
      <form:form action="" method="POST" commandName="requestCollection" class="form-horizontal" role="form">
        <fieldset>
          <div class="row">

            <div class="form-group">
              <form:label path="name" class="col-md-2 control-label">
                <p class="text-left">Name: *</p>
              </form:label>
              <div class="col-md-6">
                <form:input path="name" class="form-control" cssErrorClass="error"/>
                <form:errors path="name" cssClass="help-block with-errors" />
              </div>
            </div>

            <div class="form-group">
              <form:label path="description" class="col-md-2 control-label">
                <p class="text-left">Description: *</p>
              </form:label>
              <div class="col-md-6">
                <form:input path="description" class="form-control" cssErrorClass="error"/>
                <form:errors path="description" cssClass="help-block with-errors" />
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label" for="labels">
                <p class="text-left">Labels:</p>
              </label>
              <div class="col-md-6">
                <form:select path="labels" class="select2-multiple" multiple="multiple" 
                 items="${labels}" itemValue="id" itemLabel="name" />
                <form:errors path="labels" />
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label" for="requests">
                <p class="text-left">Requests: *</p>
              </label>
              <div class="col-md-6">
                <form:select path="requests" class="multiSelect" multiple="multiple" 
                 items="${requests}" itemValue="id" itemLabel="name" id="requests"/>
                <form:errors path="requests" cssClass="help-block with-errors" />
              </div>
            </div>

            <div class="row">
              <button type="submit" class="btn btn-success">Save</button>
              <button id="reset" class="btn btn-danger">Reset</button>
              <button id="clean" class="btn btn-warning">Clean all</button>
              <a href=<c:url value="/tests/collections" /> class="btn btn-default">Cancel</a>
            </div>

          </div>
        </fieldset>
      </form:form>
    </div>
  </div>
</div>

<script src=<c:url value="/resources/dist/js/select2.min.js" />></script>
<script src=<c:url value="/resources/dist/js/jquery.multi-select.js" />></script>

<!-- Main page script -->
<script src=<c:url value="/resources/js/collection/collectionCreateEdit.js" />></script>