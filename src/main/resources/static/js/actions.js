$(document).ready(function() {
    $('#movies_table').DataTable( {
        order: [[ 3, 'desc' ], [ 0, 'asc' ]]
    } );
} );