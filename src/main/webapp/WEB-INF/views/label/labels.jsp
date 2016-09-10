<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link href=<c:url value="/resources/dist/css/select2.min.css" /> rel="stylesheet" />

<link href=<c:url value="/resources/dist/css/select2-bootstrap.css" /> rel="stylesheet" />

<link href=<c:url value="/resources/bower_components/bootstrap-dataTables/css/dataTables.bootstrap.min.css" />
              rel="stylesheet" />


<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>Labels</h3>
                <div class="row">
                    <a href=<c:url value ="/configuration/labels/create" /> class="btn btn-success" >Create</a>
                </div>
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered table-striped" id="labels">
                        <thead>
                        <tr>
                            <th class="col-md-11">Name</th>
                            <th class="col-md-1">Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${labels}" var="labels">
                            <tr>
                                <td class="col-md-11">
                                    <a href=<c:url value="/configuration/labels/modify/${labels.id}" />>
                                        ${labels.name}
                                    </a>
                                </td>
                                <td class="col-md-1" align="center">
                                    <a href=<c:url value="/configuration/labels/delete/${labels.id}" />>
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
</div>


<script src=<c:url value="/resources/dist/js/select2.min.js" />></script>
<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/jquery.dataTables.min.js" />></script>
<script src=<c:url value="/resources/bower_components/bootstrap-dataTables/js/dataTables.bootstrap.min.js" />></script>
<script src=<c:url value="/resources/js/label/label.js" />></script>
