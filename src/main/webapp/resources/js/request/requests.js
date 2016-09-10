$(function() {

  if (navigator.userAgent.toLowerCase().indexOf('chrome') < 0) {
    $('.interstitial-wrapper').remove();
  }
	
  var loading = $('#loadingDiv');
  $(document)
    .ajaxStart(function () {
      $('html, body').css({
        'overflow': 'hidden',
        'height': '100%'
      });
	  loading.css('visibility', 'visible');
    })
    .ajaxStop(function () {
      $('html, body').css({
        'overflow': 'auto',
        'height': 'auto'
      });
      loading.hide();
	});
  
  var contextPath = $('#contextPath').val();
  var requestsToSend = [];
  
  // enables DataTable.js on requests table
  $('#requests').DataTable({
    order: [
      [1, 'asc']
    ],
    columnDefs: [{
      targets: [0, 5, 6, 7, 8, 9],
      orderable: false,
    }]
  });

  // enables tag autocomplete in filtering fields
  $('.select2-multiple').select2({
    theme: 'bootstrap',
    width: '100%'
  });

  $(document).on('click', '.duplicate', function() {
    window.location.href = contextPath + '/tests/requests/create?fromId=' + $(this).prop('id');
    return false;
  });

  // selects all request on page
  $('#requests #selectAll').click(function() {
    $('#requests input[type="checkbox"][name="operateSelect"]').prop('checked', this.checked);
  });

  // performs request run
  $(document).on('click', '.run', function() {
    requestsToSend = [$(this).prop('id')];
    startTest();
    return false;
  });

  // performs run of selected requests on page
  $(document).on('click', '#runSelected', function() {;
    requestsToSend = $('#requests input:checked[name="operateSelect"]').map(function() { 
     return $(this).prop('id');
    }).get();
    if (requestsToSend.length != 0) {
      startTest();
    }
    return false;
  });

  // performs run of all requests on page
  $(document).on('click', '#runAll', function() {
    requestsToSend = $('#requests input:checkbox:not(:checked)[name="disableSelect"]').map(function() { 
      return $(this).prop('id');
    }).get();
    if (requestsToSend.length != 0) {
      startTest();
    }
  });

  // shows modal window with environments
  function startTest() {
    $('#environmentModal').modal('show');
  }

  // confirm request run
  $('#confirmEnvironmentModal').click(function(e) {
    var envId = $('#environment').val();
    $('#environmentModal').modal('hide')
    sendTestData(envId);
    return false;
  });

  // sends test data to the server
  function sendTestData(envId) {
	  console.log(requestsToSend);
	  $.ajax({
      type: 'POST',
      url: contextPath + '/tests/requests/run',
      data: {
        environmentId: envId,
        requestIdArray: requestsToSend
      },
      success: function(data, textStatus, jqXHR) {
          window.location.replace(contextPath + '/results/requests/run/' + data);
      },
      error: function(jqXHR) {
       alert('Smth wrong... code: ' + jqXHR.status);
      },
    });
  }

  // handles remove request button click
  $(document).on('click', '.removeInstance', function() {
    if (confirm('Do you really want to delete the request?')) {
      deleteRequests([$(this).prop('id')]);
    }
    return false;
  });

  // handles remove selected requests button click
  $(document).on('click', '#deleteSelected', function() {
    var selected = selected = $('#requests input:checked[name="operateSelect"]').map(function() { 
      return $(this).prop('id');
    }).get();
    if (selected.length != 0 && confirm('Do you really want to delete the requests?')) {
      deleteRequests(selected);
    }
    return false;
  });

  // sends requests to delete to the server
  function deleteRequests(input) {
    $.ajax({
      type: 'DELETE',
      url: contextPath + '/tests/requests/delete',
      contentType: 'application/json',
      data: JSON.stringify(input),
      success: function(data, textStatus, jqXHR) {
        for (var i = 0; i < input.length; i++) {
          var tr =  $('#requests input[type="checkbox"][id=' + input[i] + ']').parents('tr');
          $('#requests').dataTable().fnDeleteRow(tr[0]);
          tr.remove();
        }
      },
      error: function(jqXHR) {
        alert('Error (code: ' + jqXHR.status + ')');
      },
    });
  };

});