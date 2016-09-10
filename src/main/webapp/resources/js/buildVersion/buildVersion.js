$(function() {

    $(document).ready(function() {
        $('#buildVersions').DataTable({
            language: {
                search: "Search by name:",
                searchPlaceholder: "search..."
            },
            order: [
                [0]
            ],
            columnDefs: [
                {targets: [1,2], orderable: false}
            ]
        });
    });

});

