$(function() {

  if (navigator.userAgent.toLowerCase().indexOf('chrome') < 0) {
    $('.interstitial-wrapper').remove();
  }

  var loading = $('#loadingDiv');
  $(document)
    .ajaxStart(function() {
      $('html, body').css({
        'overflow': 'hidden',
        'height': '100%'
      });
      loading.css('visibility', 'visible');
    })
    .ajaxStop(function() {
      $('html, body').css({
        'overflow': 'auto',
        'height': 'auto'
      });
      loading.hide();
    });

  var contextPath = $('#contextPath').val();
  var collectionsToSend = [];

  $(document).ready(function() {
    $('#collections').DataTable({
      language: {
        search: "Search by name:",
        searchPlaceholder: "search..."
      },
      order: [
        [1, 'asc']
      ],
      columnDefs: [{
        targets: [0, 2, 3, 4, 5, 6, 7],
        orderable: false,
      }]
    });
  });

  // enables tag autocomplete in filtering fields
  $('#labelFilter').select2({
    theme: 'bootstrap',
    width: '100%'
  });

  $('#selectAll').click(function() {
    $('input[type="checkbox"][name="operateSelect"]').prop('checked', this.checked);
  });

  //handles deleting single collection
  $(document).on('click', '.removeInstance', function() {
    if (confirm('Do you really want to delete this Collection?')) {
      deleteRequestCollections([$(this).prop('id')]);
    }
    return false;
  });

  //handles deleting of all selected collections 
  $(document).on('click', '#deleteSelected', function() {
    var selected = [];
    $('#collections input:checked[name="operateSelect"]').each(function() {
      selected.push($(this).prop('id'));
    });
    if (selected.length != 0 && confirm('Do you really want to delete the requestCollections?')) {
      deleteRequestCollections(selected);
    }
    return false;
  });
  
  //delete collections  
  function deleteRequestCollections(input) {
    $.ajax({
      type: 'DELETE',
      url: '/web-tester/tests/collections/delete',
      contentType: 'application/json',
      data: JSON.stringify(input),
      success: function(data, textStatus, jqXHR) {
        for (var i = 0; i < input.length; i++) {
          var tr = $('#collections input[type="checkbox"][id=' + input[i] + ']').parents('tr');
          $('#collections').dataTable().fnDeleteRow(tr[0]);
          tr.remove();
        }

      },
      error: function(jqXHR) {
        alert('Smth wrong... code: ' + jqXHR.status);
      },
    });
  };

  // performs collection run
  $(document).on('click', '.run', function() {
    collectionsToSend = [$(this).prop('id')];
    startTest();
    return false;
  });

  // performs run of selected collections on page
  $(document).on('click', '#runSelected', function() {;
    collectionsToSend = [];
    $('#collections input:checked[name="operateSelect"]').each(function() {
      collectionsToSend.push($(this).prop('id'));
    });
    if (collectionsToSend.length != 0) {
      startTest();
    }
    return false;
  });

  // performs run of all collections on page
  $(document).on('click', '#runAll', function() {
    collectionsToSend = [];
    $('#collections input:checkbox:not(:checked)[name="disableSelect"]').each(function() {
      collectionsToSend.push($(this).prop('id'));
    });
    if (collectionsToSend.length != 0) {
      startTest();
    }
  });

  // shows modal window with environments
  function startTest() {
    $('#runOptions').modal('show');
  }

  // confirm collection run
  $('#confirmRunOptions').click(function(e) {
    var envId = $('#environment').val();
    var builVerId = $('#buildVersion').val();
    sendTestData(envId, builVerId);
    return false;
  });

  // sends test data to the server
  function sendTestData(envId, builVerId) {
    $.ajax({
      type: 'POST',
      url: contextPath + '/tests/collections/run',
      data: {
        environmentId: envId,
        buildVersionId: builVerId,
        requestCollectionIdArray: collectionsToSend
      },
      success: function(data, textStatus, jqXHR) {
        window.location.replace(contextPath + '/results/collections/run/' + data);
      },
      error: function(jqXHR) {
        alert('Smth wrong... code: ' + jqXHR.status);
      },
    });
  }

});