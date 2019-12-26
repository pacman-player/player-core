$(document).ready(function () {

    getTable();

    function getTable(){
        $.ajax({
            type: 'get',
            url: "/api/admin/author/all_author",
            contentType: 'application/json',
            headers: {
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (listAuthors) {
                var htmlTable = "";

                for (var i = 0; i < listAuthors.length; i++){
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="authorID">' + listAuthors[i].id + '</td>');
                    htmlTable += ('<td id="authorName">' + listAuthors[i].name + '</td>');
                    htmlTable += ('<td><button id="editAuthorBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal" data-target="#editAuthor">Изменить</button></td>');
                    htmlTable += ('<td><button id="deleteAuthor" class="btn btn-sm btn-info" type="button">Удалить</button> </td>');
                    htmlTable += ('</tr>');
                }
                $("#AuthorTable #list-authors").remove();
                $("#getAuthorTable").after(htmlTable);
            }
        });
    }

    //add form
    $('#addAuthorBtn').click(function (event) {
        event.preventDefault();
        addAuthor();
    });

    function addAuthor(){
        var name = $("#addAuthor").val();

        $.ajax({
            type: 'post',
            url: "/api/admin/author/add_author",
            contentType: 'application/json',
            data: JSON.stringify(name),
            headers: {
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            dataType: 'JSON'
        });
        location.reload();
    }

    //delete form
    $(document).on('click', '#deleteAuthor', function () {
        var id = $(this).closest("tr").find("#authorId").text();
        deleteAuthor(id);
    });

    function deleteAuthor(id) {
        $.ajax({
            type: 'delete',
            url: "/api/admin/author/delete_author",
            contentType: 'application/json',
            data: JSON.stringify(id),
            headers: {
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            dataType: 'JSON'
        });
        location.reload();
    }

    //update form
    $("#editAuthorBtn").click(function (event) {
        event.preventDefault();
        updateAuthor()
    });

    function updateAuthor() {
        var author = {
            "id":$("#updateAutorId").val(),
            "name":$("#updateAutorName").val()
        };

        $.ajax({
            type: 'put',
            url: "/api/admin/author/update_author",
            contentType: 'application/json',
            data: JSON.stringify(author),
            headers:{
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON'
        });
        location.reload();
    }

});