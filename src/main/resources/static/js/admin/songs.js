// $(document).ready(function () {

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
            success: function (listSong) {
                var htmlTable = "";

                for (var i = 0; i < listSong.length; i++) {

                    htmlTable += ('<tr id="listSongs">');
                    htmlTable += ('<td id="tableSongId">' + listSong[i].id + '</td>');
                    htmlTable += ('<td id="tableSongName">' + listSong[i].name + '</td>');
                    htmlTable += ('<td id="tableSongAuthor">' + listSong[i].author.name + '</td>');
                    htmlTable += ('<td id="tableSongGenre">' + listSong[i].genre.name + '</td>');
                    htmlTable += ('<td><a id="editSongBtn' + listSong[i].id  + '" onclick="editSong(' + listSong[i].id + ')" class="btn btn-sm btn-info" role="button" data-toggle="modal"' +
                        ' data-target="#editSong">Изменить</a></td>');
                    htmlTable += ('<td><button id="deleteSongBtn" class="btn btn-sm btn-info" type="button">Удалить</button></td>');
                    htmlTable += ('</tr>');
                }

                $("#songsTable #list").remove();
                $("#getSongsTable").after(htmlTable);
            }
        });
    }

    //edit song GET
    function editSong(id) {

        //полючаю песню по id
        $.ajax({
            url: 'http://localhost:8080/api/admin/song/' + id,
            method: 'GET',
            success: function (editData) {

                //заполняю модалку
                $("#updateSongId").val(editData.id);
                $("#updateSongName").val(editData.name);
                $("#updateSongAuthor").val(editData.author.name);
                $("#updateSongGenre").val(editData.genre.name);

                // var authorObj = editData.author;

                //получаем жанр песни и список жанров из БД на выбор
                getAllGenreForEdit(editData.genre.name);

            },
            error: function (error) {
                alert("err: " + error);
            }
        });
    }

    //получаем жанр песни и список жанров из БД на выбор
    function getAllGenreForEdit(genreName) {

        //очищаю option в модалке
        $('#updateSongGenre').empty();

        var genreForEdit = '';
        $.getJSON("http://localhost:8080/api/admin/all_genre", function (data) {
            $.each(data, function (key, value) {
                genreForEdit += '<option id="' + value.id + '" ';

                //если жанр из таблицы песен совпадает с жанром из БД - устанавлваем в selected
                if (genreName == value.name) {
                    genreForEdit += 'selected';
                }
                genreForEdit += ' value="' + value.name + '">' + value.name + '</option>';
            });
            $('#updateSongGenre').append(genreForEdit);
        });
    }

    //PUT song
    $("#updateSongBtn").click(function (event) {
        event.preventDefault();
        updateSongForm();
    });

    function updateSongForm() {
        var editSong = {};

        editSong.id = $("#updateSongId").val();
        editSong.name = $("#updateSongName").val();
        editSong.author = $("#updateSongAuthor").val();
        editSong.genre = $("#updateSongGenre option:selected").val();

        alert("editSong before put " + JSON.stringify(editSong));

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

    //DELETE song
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

    //ОСТАНОВИЛСЯ ЗДЕСЬ!
    //GET add form
    $('#add-song-panel').click(function () {
        getAllGenreForAdd();
    });

    //POST song
    $('#addSongBtn').click(function (event) {
        event.preventDefault();
        addSongForm();
    });

    function addSongForm() {


        var addSong = {};

        addSong.name = $('#addSongName').val();
        addSong.author = $('#addSongAuthor').val();
        addSong.genre = $('#addSongGenre option:selected').val();

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/admin/add_song',
            contentType: 'application/json',
            data: JSON.stringify(addSong),
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

    //получаем все жанры из БД на выбор
    function getAllGenreForAdd() {

        //очищаю option в модалке
        $('#addSongGenre').empty();

        var genreForAdd = '';
        $.getJSON("http://localhost:8080/api/admin/all_genre", function (data) {
            $.each(data, function (key, value) {
                genreForAdd += '<option id="' + value.id + '" ';

                //если жанр из таблицы песен совпадает с жанром из БД - устанавлваем в selected
                // if (genreName == value.name) {
                //     genreRow += 'selected';
                // }
                genreForAdd += ' value="' + value.name + '">' + value.name + '</option>';
            });
            $('#addSongGenre').append(genreForAdd);
        });
    }
