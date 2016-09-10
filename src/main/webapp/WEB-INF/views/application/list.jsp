<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href=<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" /> 
  rel="stylesheet" />

    <div class="row">
      <div class="col-md-12">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3>Application</h3>
            <button type="button" class="btn btn-success" onclick="location.href='<c:url value="/configuration/applications/create"/>'">
               Create
            </button>
          </div>
          <div class="panel-body">
            <table class="table table-striped table table-hover table-bordered table-condensed text-left panel-body" id="applicationTable">
              <thead>
                <tr>
                  <th class="col-md-4">Name</th>
                  <th class="col-md-7">Description</th>
                  <th class="col-md-1">Delete</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${applications}" var="application">
                  <tr>
                    <td>
                      <a href=<c:url value="/configuration/applications/modify/${application.id}" />>
                        <c:out value="${application.name}"></c:out>
                      </a>
                    </td>
                    <td>
                      <c:out value="${application.description}"></c:out>
                    </td>
                    <td class="td-centered">
                      <a href=<c:url value="/configuration/applications/delete/${application.id}" />>
                         <span class="fa fa-trash fa-lg"></span>
                      </a>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/jquery.dataTables.min.js" />></script>

<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/dataTables.bootstrap.min.js" />></script>

<!-- Main page script -->
<script src=<c:url value="/resources/js/application/applications.js" />></script>