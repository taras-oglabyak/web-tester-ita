$(function() {

    $(document).ready(function() {
        $('#labels').DataTable({
            language: {
                search: "Search by name:",
                searchPlaceholder: "search..."
            },
            order: [
                [0]
            ],
            columnDefs: [
                {targets: [1], orderable: false}
            ]
        });
    });

});
