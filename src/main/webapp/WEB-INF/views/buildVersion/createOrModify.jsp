<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3><c:out value="${pageTask}" /> Build Version</h3>
            </div>
            <div class="panel-body">
                <form:form action="" method="POST" class="form-horizontal" role="form" modelAttribute="buildVersion">
                    <div class="row">
                        <div class="form-group">
                            <form:label path="name" class="col-md-2 control-label">
                                <p class="text-left">Name: *</p>
                            </form:label>
                            <div class="col-md-4">
                                <form:input path="name" class="form-control" cssErrorClass="error"
                                            placeholder="Name" />
                                <form:errors path="name" cssClass="help-block with-errors" />
                            </div>
                        </div>
                        <div class="form-group">
                            <form:label path="description" class="col-md-2 control-label">
                                <p class="text-left">Description: *</p>
                            </form:label>
                            <div class="col-md-4">
                                <form:textarea path="description" class="form-control" rows="3"
                                               cssErrorClass="error" placeholder="Description" />
                                <form:errors path="description" cssClass="help-block with-errors" />
                            </div>
                        </div>
                        <div class="row">
                            <button type="submit" id="validate" class="btn btn-success">Save</button>
                            <button type="button" class="btn btn-danger"
                                    onclick="location.reload();">Reset</button>
                            <button type="button" class="btn btn-default" onclick="location.href='<c:url
                                    value ="/configuration/buildVersions" />'">Cancel</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
