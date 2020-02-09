$(document).ready(function () {

    getTable();

    function getTable() {

        $.ajax({
            type: 'GET',
            url: "/api/v1/admin/genre/all_genres",
            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            success: function (listGenres) {
                var htmlTable = "";
                //  $("#GenresTable #list").remove();
                $("#genresTable tbody").empty();

                for (var i = 0; i < listGenres.length; i++) {
                    htmlTable += ('<tr id="list">');
                    htmlTable += ('<td id="genresId">' + listGenres[i].id + '</td>');
                    htmlTable += ('<td id="genresName">' + listGenres[i].name + '</td>');
                    htmlTable += ('<td><button id="editGenresBtn"  class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editGenres">изменить</button></td>');
                    htmlTable += ('<td><button id="deleteGenres" class="btn btn-sm btn-info" type="button">удалить</button></td>');
                    htmlTable += ('</tr>');
                }
                // $("#getGenresTable").after(htmlTable);
                $("#genresTable tbody").append(htmlTable);
            }

        });
    };

    //addGenre
    $("#addGenreBtn").click(function (event) {
        event.preventDefault();
        addGenre();
        $(':input', '#addForm').val('');
    });

    function addGenre() {

        var name = $("#addGenre").val();

        $.ajax({
            type: 'POST',
            url: "/api/v1/admin/genre/add_genre",

            contentType: 'application/json;',
            data: JSON.stringify(name),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-genres-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("add-genre" + name.replace(/[^\w]|_/g, ''),
                        " Жанр " + name+ " добавлен ",
                        'genres-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    //deleteForm
    $(document).on('click', '#deleteGenres', function () {
        var id = $(this).closest("tr").find("#genresId").text();
        deleteUser(id);
    });

    function deleteUser(id) {
        $.ajax({
            type: 'delete',
            url: "/api/v1/admin/genre/delete_genre",
            contentType: 'application/json;',
            data: JSON.stringify(id),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            complete:
                function () {
                    getTable();
                    $("#tab-genres-panel").tab('show');
                },
            success:
                function () {
                    sendNotification();
                    notification("delete-user" + id,
                        "  Жанр c id " + id + " удален",
                        'genres-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //updateForm
    $("#editGenresBtn").click(function (event) {
        event.preventDefault();
        updateForm();
        $(this).parent().find("#closeEditGenres").click();
    });

    function updateForm() {
        var genre = {
            "id": $("#updateGenresId").val(),
            "name": $("#updateGenresName").val()
        };

        $.ajax({
            type: 'put',
            url: "/api/v1/admin/genre/update_genre",

            contentType: 'application/json;',
            data: JSON.stringify(genre),
            headers: {
                'Accept': 'application/json',
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
                 sendNotification();
                    notification("edit-genre" + genre.id,
                        " Жанр изменен ",
                        'genres-panel');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    };

    //modal form заполнение
    $(document).on('click', '#editGenresBtn', function () {
        $("#updateGenresId").val($(this).closest("tr").find("#genresId").text());
        $("#updateGenresName").val($(this).closest("tr").find("#genresName").text());
    });
});