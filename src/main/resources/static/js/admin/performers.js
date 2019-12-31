$(document).ready(function () {

    getTable();

    function getTable(){
        $.ajax({
            type: 'get',
            url: "/api/admin/author/all_authors",
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
                $("#AuthorTable tbody").empty();
                for (var i = 0; i < listAuthors.length; i++){
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="authorId">' + listAuthors[i].id + '</td>');
                    htmlTable += ('<td id="authorName">' + listAuthors[i].name + '</td>');
                    htmlTable += ('<td><button id="editAuthorBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal" data-target="#editAuthor">Изменить</button></td>');
                    htmlTable += ('<td><button id="deleteAuthor" class="btn btn-sm btn-info" type="button">Удалить</button> </td>');
                    htmlTable += ('</tr>');
                }
                $("#AuthorTable tbody").append(htmlTable);
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
            complete:
                function () {
                    getTable();
                    $("#tab-author-panel").tab('show');
                },
            success:
                function () {
                    notification("add-author" + name.replace(/[^\w]|_/g,''),
                        " Исполнитель " + name + " добавлен");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
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
            complete:
                function () {
                    getTable();
                },
            success:
                function () {
                    notification("delete-author" + id, "  Исполнитель c id " + id + " удален");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
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
            url: "/api/admin/author/update_author",
            contentType: 'application/json',
            data: JSON.stringify(author),
            headers:{
                'Accept':'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                },
            success:
                function () {
                    notification("edit-author" + author.id, "  Изменения исполнителя с id  " + author.id + " сохранены");
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //modal form заполнение
    $(document).on('click', '#editAuthorBtn', function () {

        $("#editAuthorId").val($(this).closest("tr").find("#authorId").text());
        $("#editAuthorName").val($(this).closest("tr").find("#authorName").text());


    });

    function notification(notifyId, message) {
        let notify = document.createElement('div');
        notify.setAttribute('id', 'success-alert-' + notifyId);
        notify.classList.add("alert");
        notify.classList.add("alert-success");
        notify.classList.add("notify");
        notify.classList.add("alert-dismissible");
        notify.setAttribute("role", "alert");
        notify.setAttribute("hidden", "true");
        notify.innerHTML = '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>' + message;

        if(!document.querySelector('#field')){
            let field = document.createElement('div');
            field.id='field';
            document.getElementById('authors-panel').appendChild(field);
        }
        document.querySelector('#field').appendChild(notify)
        $('#success-alert-' + notifyId).fadeIn(400, "linear");
        setTimeout(() => {
            $('#success-alert-' + notifyId).fadeOut(500, "linear", $(this).remove());
        }, 1500);
    }


});