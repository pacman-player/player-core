$(document).ready(function () {

    getTable();

    function getTable(){

    }

    //delete form
    $(document).on('click', '#deleteAuthor', function () {
        var id = $(this).closest("tr").find("#authorId").text();
        deleteAuthor(id);
    });

    function deleteAuthor(id) {
        $.ajax({
            type: 'delete',
            url: '/api/admin/author/delete_author',
            contentType: 'application/json',
            data: JSON.stringify(id),
            headers: {
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            dataType: 'JSON',
        });
        location.reload();
    };

    //update form
    $("#editAuthor").click(function (event) {
        event.preventDefault();
        updateAuthor()
    })

    function updateAuthor() {

    }
})