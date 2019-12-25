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
                    htmlTable += ('<td id="listSongId">' + listSongs[i].id + '</td>');
                    htmlTable += ('<td id="tableSongName">' + listSongs[i].name + '</td>');
                    htmlTable += ('<td id="tableSongAuthor">' + listSongs[i].author.name + '</td>');
                    htmlTable += ('<td id="tableSongGenre">' + listSongs[i].genre.name + '</td>');
                    htmlTable += ('<td><button id="editSongBtn" class="btn btn-sm btn-info" type="button" data-toggle="modal"' +
                        ' data-target="#editSong">Изменить</button></td>');
                    htmlTable += ('<td><button id="deleteSong" class="btn btn-sm btn-info" type="button">Удалить</button></td>');
                    htmlTable += ('</tr>');
                }

                $("#songsTable #list").remove();
                $("#getSongsTable").after(htmlTable);
            }
        });
    };

    //delete song
    $(document).on('click', '#deleteSong', function () {
        var id = $(this).closest('tr').find('#listSongId').text();
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
    };


});