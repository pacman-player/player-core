//отрисовываем таблицу с песнями
getSongsTable();


$.ajax({
    method: "GET",
    url: "/api/admin/tag/all_tags",
    contentType: "application/json",
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    dataType: "JSON",
    success: autocompleteCallBack
});


function autocompleteCallBack(tags) {
    $("#updateSongSearchTags").tagsInput({
        validationPattern: new RegExp('[A-Za-zА-Яа-я0-9]+'),
        autocomplete: {
            source: tags.map(t => t.name)
        }
    });
    $("#addSongTags").tagsInput({
        validationPattern: new RegExp('[A-Za-zА-Яа-я0-9]+'),
        autocomplete: {
            source: tags.map(t => t.name)
        }
    });

}

function getSongsTable() {
    let table = $('#all-songs');

    $.ajax({
        method: 'GET',
        url: "/api/admin/song/all_songs",
        success: (listSong) => {
            listSong.sort((a, b) => a.id - b.id);
            console.log(listSong); // для дебага
            table.empty();
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
                songTable += ('<td><a id="editSongBtn' + songId + '" onclick="editSong(' + songId + ')" class="btn btn-sm btn-info" role="button" data-toggle="modal"' +
                    ' data-target="#editSong">Изменить</a></td>');
                songTable += ('<td><button id="deleteSongBtn" class="btn btn-sm btn-info" type="button">Удалить</button></td>');
                songTable += ('</tr>');
            }
            //добавил тег tbody
            table.append(songTable);
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
            console.log(editData);
            //заполняю модалку
            $("#updateSongId").val(editData.id);
            $("#updateSongName").val(editData.name);
            $("#updateSongAuthor").val(editData.authorName);
            $('#updateSongSearchTags').importTags(editData.searchTags.join(", "));
            // $("#updateSongSearchTags").val('newTag1,newTag2,newTag3');
            // $("#updateSongSearchTags").val(editData.searchTags.map(t => t.name).join(", "));
            // $("#updateSongSearchTags").val(editData.searchTags.map(t => t.name));
            $("#updateSongApproved").prop('checked', editData.approved);
        },
        error: function (xhr, status, error) {
            alert(xhr.responseText + '|\n' + status + '|\n' + error);
        }
    });
}

// Listen for click on toggle checkbox
$('#select-all').click(function (event) {

    if (this.checked) {
        // Iterate each checkbox
        $(':checkbox').each(function () {
            if ($(this).is(':visible')) {
                this.checked = true;
            } else {
                this.checked = false;
            }
        });
    } else {
        $(':checkbox').each(function () {
            this.checked = false;
        });
    }
});



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
    editSong.searchTags = $("#updateSongSearchTags").val().split(",");
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

    let formData = new FormData();
    formData.append('name', $('#addSongName').val());
    formData.append('authorName', $('#addSongAuthor').val());
    formData.append('searchTags', $('#addSongTags').val().split(','));
    formData.append('file', $('#addSongFile').prop("files")[0]);

    $.ajax({
        method: "POST",
        url: "/api/admin/song/add_song",
        contentType: false,
        processData: false,
        mimeType: "multipart/form-data",
        data: formData,
        dataType: 'json',
        success: (response) => {
            if (response.success === true) {
                notification("add-song", response.data, "song-panel");
                $('#addSongName').val('');
                $('#addSongAuthor').val('');
                $('#addSongTags').importTags('');
                $('#addSongFile').val('');
                $('#tab-song-panel').tab('show');
                getSongsTable();
            } else if (response.hasOwnProperty('errorMessage') && response.errorMessage.hasOwnProperty('textMessage')) {
                alert(response.errorMessage.textMessage);
            } else {
                alert(response);
            }
        },
        error: (xhr, status, error) => {
            alert(xhr.responseText + "|\n" + status + "|\n" + error);
        }
    })
});





