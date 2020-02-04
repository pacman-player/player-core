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
                    if (listAuthors[i].authorGenres.length > 1) {
                        var htmlRole = "";
                        for (var j = 0; j < listAuthors[i].authorGenres.length - 1; j++) {
                            htmlRole = htmlRole + listAuthors[i].authorGenres[j].name + ', '
                        }
                            htmlRole = htmlRole + listAuthors[i].authorGenres[j].name;
                    } else {
                        var htmlRole = listAuthors[i].authorGenres[0].name;
                    }
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="authorId">' + listAuthors[i].id + '</td>');
                    htmlTable += ('<td id="authorName">' + listAuthors[i].name + '</td>');
                    htmlTable += ('<td id="authorGenre">' + htmlRole + '</td>');
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
                        " Исполнитель " + name + " добавлен",
                        'authors-panel');
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
                    notification("delete-author" + id,
                        "  Исполнитель c id " + id + " удален",
                        'authors-panel');
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
        var id = $("#editAuthorId").val();
        var name = $("#editAuthorName").val();
        var genres = $("#editAuthorGenre option:selected").val();
        var editAuthor = {
            'id':id,
            'name':name,
            'genres':genres
            };
        $.ajax({
            type: 'put',
            url: "/api/admin/author/update_author",
            contentType: 'application/json',
            data: JSON.stringify(editAuthor),
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
                    notification("edit-author" + id,
                        "  Изменения исполнителя с id  " + id + " сохранены",
                        'authors-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

//получаем жанр песни и список жанров из БД для edit song
function getAllGenreForEdit(genreName) {
    //очищаем option в модалке
    $('#editAuthorGenre').empty();
    var genreForEdit = '';
    $.getJSON("/api/admin/song/all_genre", function (data) {
        $.each(data, function (key, value) {
            genreForEdit += '<option id="' + value.id + '" ';
            //если жанр из таблицы песен совпадает с жанром из БД - устанавлваем в selected
            if (genreName == value.name) {
                genreForEdit += 'selected';
            }
            genreForEdit += ' value="' + value.name + '">' + value.name + '</option>';
        });
        $('#editAuthorGenre').append(genreForEdit);
    });
}
    //modal form заполнение
//    $(document).on('click', '#editAuthorBtn', function () {
//        $("#editAuthorId").val($(this).closest("tr").find("#authorId").text());
//        $("#editAuthorName").val($(this).closest("tr").find("#authorName").text());
//        $("#editAuthorGenre").val($(this).closest("tr").find("#authorGenre").text());
//        getAllGenreForEdit($(this).closest("tr").find("#authorGenre").text());
//    });
    $(document).on('click', '#editAuthorBtn', function () {
        $.ajax({
            url: '/api/admin/author/' + $(this).closest("tr").find("#authorId").text(),
            method: "GET",
            dataType: "json",
            success: function (data) {
                $("#editAuthorId").val(data.id);
             $("#editAuthorName").val(data.name);
                $("#editAuthorGenre").val(data.genres);
                getAllGenreForEdit($(this).closest("tr").find("#authorGenre").text());
            }
        })
    });
});