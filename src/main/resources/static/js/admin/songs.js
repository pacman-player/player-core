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

    //edit song GET
    $(document).on('click', '#editSongBtn', function () {

        $("#updateSongId").val($(this).closest("tr").find("#tableSongId").text());
        $("#updateSongName").val($(this).closest("tr").find("#tableSongName").text());
        $("#updateSongAuthor").val($(this).closest("tr").find("#tableSongAuthor").text());
        $("#updateSongGenre").val($(this).closest("tr").find("#tableSongGenre").text());

        switch ($(this).closest("tr").find("#tableRole").text()) {
            case 'USER':
                $("#updateUserRole").val("user");
                break;
            case 'ADMIN':
                // $('#updateUserRole option:contains("ADMIN")').prop("selected", true);
                $("#updateUserRole").val("admin");
                break;
            default:
                // $('#updateUserRole option:contains("ADMIN, USER")').prop("selected", true);
                $("#updateUserRole").val("admin, user");
                break;
        }

    });

    //edit song PUT
    $("#editSongBtn").click(function (event) {
        event.preventDefault();
        updateSongForm();
    });

    function updateSongForm() {
        var song = {
            'id': $("#updateSongId").val(),
            'name': $("#updateSongName").val(),
            'author': $("#updateSongAuthor").val(),
            'genre': $("#updateSongGenre").val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/admin/update_song',
            contentType: 'application/json',
            data: JSON.stringify(song),
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
});