$(function() {

    $('#selectAll').click(function () {
        $('input[type="checkbox"][name="operateSelect"]').prop('checked', this.checked);
    });

    /*$(document).on('click', '#deleteSelected', function() {
        var selected = [];
        $('#labels input:checked[name="operateSelect"]').each(function() {
            selected.push($(this).prop('id'));
        });
        if (selected.length != 0 && confirm('Do you really want to delete the labels?')) {
            deleteLabels(selected);
        }
        return false;
    });

    function deleteLabels(input) {
        $.ajax({
            type: 'DELETE',
            url: '/web-tester/metadata/labels',
            contentType: 'application/json',
            data: JSON.stringify(input),
            success: function(data, textStatus, jqXHR) {
                for (var i = 0; i < input.length; i++) {
                    $('#labels input[type="checkbox"][id=' + input[i] + ']').parents('tr').remove();
                }
                alert('code: ' + jqXHR.status);
            },
            error: function(jqXHR) {
                alert('oyva.. code: ' + jqXHR.status);
            },
        });
    };*/
});