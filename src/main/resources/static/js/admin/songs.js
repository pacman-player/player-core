$(document).ready(function () {

    getSongsTable();

    function getSongsTable() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/admin/all_songs",

            contentType: 'application/json;',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
            success: function (listSongs) {
                var htmlTable = "";

                for (var i = 0; i < listSongs.length; i++) {

                    htmlTable += ('<tr id="listSongs">');
                    htmlTable += ('<td id="tableSongId">' + listSongs[i].id + '</td>');
                    htmlTable += ('<td id="tableSongName">' + listSongs[i].name + '</td>');
                    htmlTable += ('<td id="tableSongAuthor">' + listSongs[i].author.name + '</td>');
                    htmlTable += ('<td id="tableSongGenre">' + listSongs[i].genre.name + '</td>');
                    htmlTable += ('<td><button id="editSongBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editSong">Изменить</button></td>');
                    htmlTable += ('<td><button id="deleteSongBtn" class="btn btn-sm btn-info" type="button">Удалить</button></td>');
                    htmlTable += ('</tr>');
                }

                $("#songsTable #list").remove();
                $("#getSongsTable").after(htmlTable);
            }
        });
    }

    //edit song GET
    $(document).on('click', '#editSongBtn', function () {

        //заполняем модалку
        $("#updateSongId").val($(this).closest("tr").find("#tableSongId").text());
        $("#updateSongName").val($(this).closest("tr").find("#tableSongName").text());
        $("#updateSongAuthor").val($(this).closest("tr").find("#tableSongAuthor").text());

        //получаю жанр песни из таблицы по нажатию Изменить
        var genre = ($(this).closest("tr").find("#tableSongGenre").text());

        //получаю жанры из бд
        getAllGenre();

        //получение всех жанров из БД
        function getAllGenre() {

            //очищаю option в модалке
            $('#updateSongGenre').empty();

            var genreRow = '';
            $.getJSON("http://localhost:8080/api/admin/all_genre", function (data) {
                $.each(data, function (key, value) {
                    genreRow += '<option ';

                    //если жанр из таблицы совпадает с жанром из БД - устанавлваем в selected
                    if (genre == value.name) {
                        genreRow += 'selected';
                    }
                    genreRow += ' value="' + value.name + '">' + value.name + '</option>';
                });
                $('#updateSongGenre').append(genreRow);
            });
        }
    });

    //ОСТАНОВИЛСЯ ЗДЕСЬ!
    //edit song PUT
    $("#editSongBtn").click(function (event) {
        event.preventDefault();
        updateSongForm();
    });

    function updateSongForm() {
        var editSong = {};
        editSong.id = $("#updateSongId").val();
        editSong.name = $("#updateSongName").val();
        editSong.author = $("#updateSongAuthor").val();
        editSong.genre = $("#updateSongGenre").val();

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/api/admin/update_song',
            contentType: 'application/json',
            data: JSON.stringify(editSong),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: true,
            cache: false,
            dataType: 'JSON',
        });
        location.reload();
    }

    //delete song
    $(document).on('click', '#deleteSongBtn', function () {
        var id = $(this).closest('tr').find('#tableSongId').text();
        deleteSong(id);
    });

    function deleteSong(id) {
        $.ajax({
            type: 'delete',
            url: 'http://localhost:8080/api/admin/delete_song',
            contentType: 'application/json',
            data: JSON.stringify(id),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            dataType: 'JSON'
        });
        location.reload();
    }
});