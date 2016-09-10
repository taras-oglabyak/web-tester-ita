<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
  <div class="navbar-header">
    <a class="navbar-brand" href=<c:url value="/" />>Web-tester</a>
  </div>

  <ul class="nav navbar-top-links navbar-left">
    <sec:authorize access="hasRole('ROLE_QA')">
    <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        <i class="fa fa-cog fa-fw"></i> Configuration <i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-alerts">
        <li>
          <a href=<c:url value="/configuration/applications" />>
            <i class="fa fa-wrench fa-fw"></i> Applications
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/configuration/services" />>
            <i class="fa fa-wrench fa-fw"></i> Services
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/configuration/labels" />>
            <i class="fa fa-wrench fa-fw"></i> Labels
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/configuration/buildVersions" />>
            <i class="fa fa-wrench fa-fw"></i> Build versions
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/configuration/environments" />>
            <i class="fa fa-wrench fa-fw"></i> Environments
          </a>
        </li>
      </ul>
    </li>

    <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        <i class="fa fa-tasks fa-fw"></i> Tests <i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-alerts">
        <li>
          <a href=<c:url value="/tests/requests" />>
            <i class="fa fa-flag fa-fw"></i> Requests
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/tests/collections" />>
            <i class="fa fa-flag fa-fw"></i> Collections
          </a>
        </li>
      </ul>
    </li>

    <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        <i class="fa fa-eye fa-fw"></i> Results <i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-alerts">
        <li>
          <a href=<c:url value="/results/requests" />>
            <i class="fa fa-book fa-fw"></i> Requests
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href=<c:url value="/results/collections" />>
            <i class="fa fa-book fa-fw"></i> Collections
          </a>
        </li>
      </ul>
    </li>
    </sec:authorize>
    
    <sec:authorize access="isAuthenticated()">
    <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        <i class="fa fa-calendar-o fa-fw"></i> Reports <i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-alerts">
        <li>
          <a href="<c:url value="/reports/statistic" />">
            <i class="fa fa-bar-chart fa-fw"></i> Statistic
          </a>
        </li>
        <li class="divider"></li>
        <li>
          <a href="<c:url value="/reports/graphics" />">
            <i class="fa fa-line-chart fa-fw"></i> Graphics
          </a>
        </li>
      </ul>
    </li>
    </sec:authorize>
  </ul>


  <ul class="nav navbar-top-links navbar-right">

    <li class="dropdown">
      <a class="dropdown-toggle" data-toggle="dropdown" href="#">
        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
      </a>
      <ul class="dropdown-menu dropdown-user">
        <li><a href=<c:url value="/account " />><i class="fa fa-user fa-fw"></i> User Profile</a>
        </li>

        <li class="divider"></li>
        <li><a href=<c:url value="/logout " />><i class="fa fa-sign-out fa-fw"></i> Logout</a>
        </li>
      </ul>
    </li>

  </ul>

</nav>