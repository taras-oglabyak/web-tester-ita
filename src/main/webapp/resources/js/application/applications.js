$(function() {

  $(document).ready(function() {
    $("#applicationTable").DataTable({
      language: {
        search: "Search by name:",
        searchPlaceholder: "search..."
      },
      order: [
        [0, 'asc']
      ],
      columnDefs: [{
        targets: [1,2],
        orderable: false,
      }]
    });
  });
});