<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
  <form:form action="" method="POST" modelAttribute="user" class="form-horizontal" role="form">
    <fieldset>

      <form:hidden path="id" />

      <div class="form-group">
        <c:if test="${not empty param.success}">
          <div class="alert alert-success col-md-4 col-md-offset-4">
            <strong>Success!</strong> Account has been successfully updated!
          </div>
        </c:if>
      </div>

      <legend>User account data</legend>

      <div class="row">
        <div class="form-group">
          <form:label path="username" class="col-md-4 control-label">Email</form:label>
          <div class="col-md-4">
            <form:input type="text" path="username" class="form-control" cssErrorClass="error" />
            <form:errors path="username" class="help-block with-errors" />
          </div>
        </div>

        <div class="form-group">
          <form:label path="password" class="col-md-4 control-label">Password</form:label>
          <div class="col-md-4">
            <form:input path="password" class="form-control" cssErrorClass="error" />
            <form:errors path="password" class="help-block" />
          </div>
        </div>

        <div class="form-group">
          <form:label path="firstName" class="col-md-4 control-label">First name</form:label>
          <div class="col-md-4">
            <form:input path="firstName" class="form-control" />
          </div>
        </div>

        <div class="form-group">
          <form:label path="lastName" class="col-md-4 control-label">Last name</form:label>
          <div class="col-md-4">
            <form:input path="lastName" class="form-control" />
          </div>
        </div>
      </div>

      <div class="row">
        <div class="form-group">
          <div class="col-md-4 col-md-offset-4">
            <button id="validate" type="submit" class="btn btn-lg btn-success btn-block">Save</button>
          </div>
        </div>
      </div>

    </fieldset>
  </form:form>
</div>

<script src=<c:url value="/resources/bower_components/jquery/dist/jquery.validate.min.js" />></script>

<!-- Main page script -->
<!-- <script src=<c:url value="/resources/js/account.js" />></script> -->