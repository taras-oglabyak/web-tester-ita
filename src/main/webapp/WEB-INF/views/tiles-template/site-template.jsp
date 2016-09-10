<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <title><tiles:insertAttribute name="title" ignore="true" /></title>

  <link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">

  <link href="<c:url value="/resources/dist/css/sb-admin-2.css" />" rel="stylesheet">

  <link href="<c:url value="/resources/bower_components/metisMenu/dist/metisMenu.min.css" />" rel="stylesheet">

  <link href="<c:url value="/resources/bower_components/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
 
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
  <script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>

  <script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>

  <script src="<c:url value="/resources/dist/js/sb-admin-2.js" />"></script>

  <script src="<c:url value="/resources/bower_components/metisMenu/dist/metisMenu.min.js" />"></script>

</head>

<body>

  <tiles:insertAttribute name="menu" />
  
  <div id="page-wrapper">
    <tiles:insertAttribute name="body" />
  </div>
  
</body>

</html>