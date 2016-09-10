<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('ROLE_QA')">
<div class="row">
  <div class="col-md-12">
    <h1 class="page-header">Configuration</h1>
  </div>
</div>

<div class="row">
  <div class="col-md-5">
    <div class="panel custom-Purple4 cursorPointer" id="applicationsBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-cog fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
            <div class="huge">Applications</div>
          </div>
        </div>
      </div>
      <a href=<c:url value="/configuration/applications" />>
        <div class="panel-footer">
          <span class="pull-left"><font color= #551A8B>View page</font></span>
          <span class="pull-right"><font color= #551A8B><i class="fa fa-arrow-circle-right"></i></font></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>

  <div class="col-md-5">
    <div class="panel custom-OliveDrab4 cursorPointer" id="serviceBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-cog fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
            <div class="huge">Services</div>
          </div>
        </div>
      </div>
      <a href=<c:url value="/configuration/services" />>
        <div class="panel-footer">
          <span class="pull-left"><font color= #698B22>View page</font></span>
          <span class="pull-right"><font color= #698B22><i class="fa fa-arrow-circle-right"></i></font></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-md-5">
    <div class="panel panel-red cursorPointer" id="labelsBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-cog fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
            <div class="huge">Labels</div>
          </div>
        </div>
      </div>
      <a href=<c:url value="/configuration/labels" />>
        <div class="panel-footer">
          <span class="pull-left">View page</span>
          <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>

  <div class="col-md-5">
    <div class="panel panel-yellow cursorPointer" id="BuildVersionsBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-cog fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
            <div class="huge">BuildVersions</div>
          </div>
        </div>
      </div>
      <a href=<c:url value="/configuration/buildVersions" />>
        <div class="panel-footer">
          <span class="pull-left">View page</span>
          <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-md-5">
    <div class="panel panel-pink cursorPointer" id="environmentsBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-cog fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
            <div class="huge">Environments</div>
          </div>
        </div>
      </div>
      <a href=<c:url value="/configuration/environments" />>
        <div class="panel-footer">
          <span class="pull-left">View page</span>
          <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-md-12">
    <h1 class="page-header">Tests</h1>
  </div>
</div>

<div class="row">
  <div class="col-md-5">
    <div class="panel panel-primary cursorPointer" id="requestBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-tasks fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
             <div class="huge">Requests</div> 
          </div>
        </div>
      </div>
      <a href=<c:url value="/tests/requests" />>
        <div class="panel-footer">
          <span class="pull-left">View page</span>
          <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>
  
  <div class="col-md-5">
    <div class="panel panel-green cursorPointer" id="collectionsBlock">
      <div class="panel-heading">
        <div class="row">
          <div class="col-xs-3">
            <i class="fa fa-tasks fa-4x"></i>
          </div>
          <div class="col-xs-9 text-right">
             <div class="huge">Collections</div> 
          </div>
        </div>
      </div>
      <a href=<c:url value="/tests/collections" />>
        <div class="panel-footer">
          <span class="pull-left">View page</span>
          <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
          <div class="clearfix"></div>
        </div>
      </a>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <h1 class="page-header">Results</h1>
    </div>
  </div>
  </div>

  
  <div class="row">
    <div class="col-md-5">
      <div class="panel panel-yellow cursorPointer" id="requestResultBlock">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-eye fa-4x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">Requests</div>
            </div>
          </div>
        </div>
        <a href=<c:url value="/results/requests" />>
          <div class="panel-footer">
            <span class="pull-left">View page</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>

    <div class="col-md-5">
      <div class="panel panel-yellow cursorPointer" id="collectionResultBlock">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-eye fa-4x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">Collections</div>
            </div>
          </div>
        </div>
        <a href=<c:url value="/results/collections" />>
          <div class="panel-footer">
            <span class="pull-left">View page</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
</div>
</sec:authorize>

  <sec:authorize access="isAuthenticated()">
  <div class="row">
    <div class="col-md-12">
      <h1 class="page-header">Reports</h1>
    </div>
  </div>

  <div class="row">
    <div class="col-md-5">
      <div class="panel panel-blue cursorPointer" id="statisticReportBlock">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-calendar-o fa-4x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">Statistic</div>
            </div>
          </div>
        </div>
        <a href=<c:url value="/reports/statistic" />>
          <div class="panel-footer">
            <span class="pull-left">View page</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>

    <div class="col-md-5">
      <div class="panel panel-blue cursorPointer" id="graphicReportBlock">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-3">
              <i class="fa fa-calendar-o fa-4x"></i>
            </div>
            <div class="col-xs-9 text-right">
              <div class="huge">Graphics</div>
            </div>
          </div>
        </div>
        <a href=<c:url value="/reports/graphics" />>
          <div class="panel-footer">
            <span class="pull-left">View page</span>
            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
            <div class="clearfix"></div>
          </div>
        </a>
      </div>
    </div>
  </div>
  </sec:authorize>

  <!-- Main page script -->
<script src=<c:url value="/resources/js/home.js" />></script>