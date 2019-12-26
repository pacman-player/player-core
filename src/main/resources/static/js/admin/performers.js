$(document).ready(function () {

    getTable();

    function getTable(){
        $.ajax({
            type: 'get',
            url: "/api/admin/all_authors",
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
                    htmlTable += ('<td id="authorId">' + listAuthors[i].id + '</td>');
                    htmlTable += ('<td id="authorName">' + listAuthors[i].name + '</td>');
                    htmlTable += ('<td><button id="editAuthorBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal" data-target="#editAuthor">Изменить</button></td>');
                    htmlTable += ('<td><button id="deleteAuthor" class="btn btn-sm btn-info" type="button">Удалить</button> </td>');
                    htmlTable += ('</tr>');
                }
                $("#AuthorTable #list").remove();
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
            url: "/api/admin/add_author",
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
            url: "/api/admin/delete_author",
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
            "id":$("#editAuthorId").val(),
            "name":$("#editAuthorName").val()
        };

        $.ajax({
            type: 'put',
            url: "/api/admin/update_author",
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

    //modal form заполнение
    $(document).on('click', '#editAuthorBtn', function () {

        $("#editAuthorId").val($(this).closest("tr").find("#authorId").text());
        $("#editAuthorName").val($(this).closest("tr").find("#authorName").text());


    });

});