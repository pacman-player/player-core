//отрисовываем таблицу с песнями
getSongsTable();

function getSongsTable() {
    $.ajax({
        method: 'GET',
        url: "http://localhost:8080/api/admin/song/all_songs",
        success: function (listSong) {
            $('#all-songs').empty();
            var songTable = "";
            for (var i = 0; i < listSong.length; i++) {
                songTable += ('<tr id="listSongs">');
                songTable += ('<td id="tableSongId">' + listSong[i].id + '</td>');
                songTable += ('<td id="tableSongName">' + listSong[i].name + '</td>');
                songTable += ('<td id="tableSongAuthor">' + listSong[i].author.name + '</td>');
                songTable += ('<td id="tableSongGenre">' + listSong[i].genre.name + '</td>');
                songTable += ('<td><a id="editSongBtn' + listSong[i].id + '" onclick="editSong(' + listSong[i].id + ')" class="btn btn-sm btn-info" role="button" data-toggle="modal"' +
                    ' data-target="#editSong">Изменить</a></td>');
                songTable += ('<td><button id="deleteSongBtn" class="btn btn-sm btn-info" type="button">Удалить</button></td>');
                songTable += ('</tr>');
            }
            //добавил тег tbody
            $('#all-songs').append(songTable);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//заполняем модалку edit song
function editSong(id) {
    $.ajax({
        url: 'http://localhost:8080/api/admin/song/' + id,
        method: 'GET',
        success: function (editData) {
            //заполняю модалку
            $("#updateSongId").val(editData.id);
            $("#updateSongName").val(editData.name);
            $("#updateSongAuthor").val(editData.author.name);
            $("#updateSongGenre").val(editData.genre.name);
            //получаем жанр песни и список жанров из БД для edit song
            getAllGenreForEdit(editData.genre.name);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//получаем жанр песни и список жанров из БД для edit song
function getAllGenreForEdit(genreName) {
    //очищаем option в модалке
    $('#updateSongGenre').empty();
    var genreForEdit = '';
    $.getJSON("http://localhost:8080/api/admin/song/all_genre", function (data) {
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

//обновляем песню PUT song
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
    $.ajax({
        method: 'PUT',
        url: 'http://localhost:8080/api/admin/song/update_song',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(editSong),
        success: function () {
            $('#editSong').modal('hide');
            $('#song-table-nav').tab('show');
            getSongsTable();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//удаляем песню DELETE song
$(document).on('click', '#deleteSongBtn', function () {
    var id = $(this).closest('tr').find('#tableSongId').text();
    deleteSong(id);
});

function deleteSong(id) {
    $.ajax({
        method: 'DELETE',
        url: 'http://localhost:8080/api/admin/song/delete_song/' + id,
        success: function() {
            getSongsTable();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//добавляем новую песню POST song
$('#addSongBtn').click(function (event) {
    event.preventDefault();
    addSongForm();
});

function addSongForm() {
    var addSong = {};
    addSong.name = $('#addSongName').val();
    addSong.author = $('#addSongAuthor').val();
    addSong.genre = $('#addSongGenre').val();
    $.ajax({
        method: 'POST',
        url: 'http://localhost:8080/api/admin/song/add_song',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(addSong),
        success: function () {
            $('#addSongName').val('');
            $('#addSongAuthor').val('');
            $('#tab-song-panel').tab('show');
            getSongsTable();
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//получаем все жанры песни из БД на выбор
$('#add-song-nav').click(function () {
    getAllGenreForAdd();
});

function getAllGenreForAdd() {
    //очищаю жанры option
    $('#addSongGenre').empty();
    var genreForAdd = '';
    $.getJSON("http://localhost:8080/api/admin/song/all_genre", function (data) {
        $.each(data, function (key, value) {
            genreForAdd += '<option ';
            genreForAdd += ' value="' + value.name + '">' + value.name + '</option>';
        });
        $('#addSongGenre').append(genreForAdd);
    });
}