$(function() {

  $(document).ready(function() {
    $("#serviceTable").DataTable({
      language: {
        search: "Search by name:",
        searchPlaceholder: "search..."
      },
      order: [
        [0, 'asc']
      ],
      columnDefs: [{
        targets: [1,2,3],
        orderable: false,
      }]
    });
  });
});