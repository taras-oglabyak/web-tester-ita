$(function() {
	
  $('#applicationsBlock').click(function() {
	window.location.href = 'configuration/applications';
  });
  
  $('#serviceBlock').click(function() {
	window.location.href = 'configuration/services';
  });

  $('#labelsBlock').click(function() {
    window.location.href = 'configuration/labels';
  });

  $('#BuildVersionsBlock').click(function() {
    window.location.href = 'configuration/buildVersions';
  });

  $('#requestBlock').click(function() {
    window.location.href = 'tests/requests';
  });

  $('#collectionsBlock').click(function() {
    window.location.href = 'tests/collections';
  });

  $('#requestResultBlock').click(function() {
    window.location.href = 'tests/collections';
  });

  $('#collectionResultBlock').click(function() {
    window.location.href = 'tests/collections';
  });

  $('#environmentsBlock').click(function() {
      window.location.href = 'configuration/environments';
  });

  $('#statisticReportBlock').click(function() {
      window.location.href = 'reports/statistic';
  });
  
  $('#graphicReportBlock').click(function() {
      window.location.href = 'reports/graphics';
  });

});