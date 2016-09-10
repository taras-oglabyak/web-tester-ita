$(function() {

    // enables tag autocomplete in filtering fields
    $('.select2-multiple').select2({
        theme: 'bootstrap',
        width: '100%'
    });

    $(document).ready(function() {
        $('#results').DataTable({
            order: [
                [1, 'asc']
            ],
            columnDefs: [{
                targets: [0, 6, 7, 8, 9 ],
                orderable: false
            }]
        });
    });

    // enables tag autocomplete in filtering fields
    $('#applicationFilter, #serviceFilter').select2({
        theme: 'bootstrap',
        width: '100%'
    });

    // selects all requests on page
    $('#selectAll').click(function() {
        $('#results input[type="checkbox"][name="operateSelect"]').prop('checked', this.checked);
    });

    // handles remove selected requests button click
    $(document).on('click', '#deleteSelected', function() {
        var selected = selected = $('#results input:checked[name="operateSelect"]').map(function() {
            return $(this).prop('id');
        }).get();
        if (selected.length != 0 && confirm('Do you really want to delete the results?')) {
            deleteResults(selected);
        }
        return false;
    });

    // sends requests to delete to the server
    function deleteResults(input) {
        $.ajax({
            type: 'DELETE',
            url: '/web-tester/results/requests',
            contentType: 'application/json',
            data: JSON.stringify(input),
            success: function(data, textStatus, jqXHR) {
                for (var i = 0; i < input.length; i++) {
                    $('input[type="checkbox"][id=' + input[i] + ']').parents('tr').remove();
                }
            },
            error: function(jqXHR) {
                alert('Error (code: ' + jqXHR.status + ')');
            },
        });
    };
});