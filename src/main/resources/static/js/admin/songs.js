//отрисовываем таблицу с песнями
getSongsTable();

function getSongsTable() {
    $.ajax({
        method: 'GET',
        url: "/api/admin/song/all_songs",
        success: (listSong) => {
            console.log(listSong) // для дебага
            $('#all-songs').empty();
            var songTable = "";
            for (var i = 0; i < listSong.length; i++) {
                let checkedBox = "";
                let songId = listSong[i].id;

                songTable += ('<tr id="listSongs">');
                songTable += ('<td id="tableSongId">' + songId + '</td>');

                if (listSong[i].approved) {
                    checkedBox = " checked"
                }
                songTable += ('<td><input id="tableSongIsApproved" class="checkbox" type="checkbox" disabled' + checkedBox + '/></td>');
                songTable += ('<td id="tableSongName">' + listSong[i].name + '</td>');
                songTable += ('<td id="tableSongAuthor">' + listSong[i].authorName + '</td>');
                songTable += ('<td id="tableSongGenre">' + listSong[i].genreName + '</td>');
                songTable += ('<td><a id="editSongBtn' + songId + '" onclick="editSong(' + songId + ')" class="btn btn-sm btn-info" role="button" data-toggle="modal"' +
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
        url: '/api/admin/song/' + id,
        method: 'GET',
        success: function (editData) {
            console.log(editData)
            //заполняю модалку
            $("#updateSongId").val(editData.id);
            $("#updateSongName").val(editData.name);
            $("#updateSongAuthor").val(editData.authorName);
            $("#updateSongSearchTags").val(editData.searchTags);
            $("#updateSongGenre").val(editData.genreName);
            //получаем жанр песни и список жанров из БД для edit song
            getAllGenreForEdit(editData.genreName);
            $("#updateSongApproved").prop('checked', editData.approved);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

// Listen for click on toggle checkbox
$('#select-all').click(function(event) {

    if(this.checked) {
        // Iterate each checkbox
        $(':checkbox').each(function() {
            if($(this).is(':visible')){
                 this.checked = true;
            }else{
                this.checked =false;
            }
        });
    } else {
        $(':checkbox').each(function() {
            this.checked = false;
        });
    }
});

//получаем жанр песни и список жанров из БД для модалки edit song
function getAllGenreForEdit(genreName) {
    //очищаем option в модалке
    $('#updateSongGenre').empty();
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
    editSong.approved = $("#updateSongApproved").prop('checked');
    editSong.name = $("#updateSongName").val();
    editSong.authorName = $("#updateSongAuthor").val();
    editSong.searchTags = $("#updateSongSearchTags").val();
    editSong.genreName = $("#updateSongGenre option:selected").val();
    $.ajax({
        method: 'PUT',
        url: '/api/admin/song/update_song',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(editSong),
        complete: function () {
            $('#editSong').modal('hide');
            $('#song-table-nav').tab('show');
            getSongsTable();
        },
        success: () => {
            notification(
                "update-song" + editSong.id,
                ` Песня ${editSong.name} изменена`,
                "song-panel");
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

//удаляем песню DELETE song
$(document).on('click', '#deleteSongBtn', function () {
    var id = $(this).closest('tr').find('#tableSongId').text();
    var name = $(this).closest('tr').find('#tableSongName').text();
    deleteSong(id, name);
});

function deleteSong(id, name) {
    $.ajax({
        method: 'DELETE',
        url: '/api/admin/song/delete_song/' + id,
        complete: () => {
            getSongsTable();
        },
        success: () => {
            notification(
                "delete-song" + id,
                ` Песня ${name} удалена`,
                "song-panel");
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
    addSong.authorName = $('#addSongAuthor').val();
    addSong.searchTags = $('#addSongTags').val();
    addSong.genreName = $('#addSongGenre').val();
    $.ajax({
        method: 'POST',
        url: '/api/admin/song/add_song',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(addSong),
        success: function () {
            $('#addSongName').val('');
            $('#addSongAuthor').val('');
            $('#addSongTags').val('');
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
    $.getJSON("/api/admin/song/all_genre", function (data) {
        $.each(data, function (key, value) {
            genreForAdd += '<option ';
            genreForAdd += ' value="' + value.name + '">' + value.name + '</option>';
        });
        $('#addSongGenre').append(genreForAdd);
    });
}