$(document).ready(function () {
    getReportTable();

    $("#sendMessage").click(function (event) {
        event.preventDefault();
        sendMessage();
        $("#idConversation").val("");
        $("#message").val("");
    });

    function sendMessage() {

        var conversation = {
            id: $("#idConversation").val(),
            message: $("#message").val(),

        };

        $.ajax({
            type: 'POST',
            url: "/api/admin/vk_bot/send",
            contentType: 'application/json;',
            data: JSON.stringify(conversation),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            async: false,
            cache: false,
            complete:
                function () {

                },
            success:
                function () {
                    notification("",
                        "Отчет отправлен в беседу " + $("#idConversation").val(),
                        'conversation');
                },
            error:
                function (xhr, status, error) {
                    alert(xhr.responseText + '|\n' + status + '|\n' + error);
                }
        });
    }

    function getReportTable() {
        $.ajax({
            method: "GET",
            url: "/api/admin/song/authors_songs_genres_for_today",
            contentType: "application/json",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            dataType: "JSON",
            success: (data) => {
                let tableAuthorsBody = $("#AuthorTable tbody");
                tableAuthorsBody.empty();

                let tableSongsBody = $("#SongsTable tbody");
                tableAuthorsBody.empty();

                let tableGenresBody = $("#GenresTable tbody");
                tableAuthorsBody.empty();

                let authors = data.authors;
                let songs = data.songs;
                let genres = data.genres;

                for (let i = 0; i < authors.length; i++) {
                    let id = authors[i].id;
                    let name = authors[i].name;
                    let createTime = new Date(authors[i].createdAt);

                    let tr = $("<tr/>");
                    tr.append(`
                            <td>${id}</td>   
                            <td>${name}</td>
                            <td>${checkTime(createTime.getHours()) + ':' +
                                    checkTime(createTime.getMinutes()) + ':' + 
                                    checkTime(createTime.getSeconds())}</td>
                            </td>`);
                    tableAuthorsBody.append(tr);
                }

                for (let i = 0; i < songs.length; i++) {
                    let id = songs[i].id;
                    let name = songs[i].name;
                    let createTime = new Date(songs[i].createdAt);

                    let tr = $("<tr/>");
                    tr.append(`
                            <td>${id}</td>   
                            <td>${name}</td>
                            <td>${checkTime(createTime.getHours()) + ':' +
                                    checkTime(createTime.getMinutes()) + ':' +
                                    checkTime(createTime.getSeconds())}</td>
                            </td>`);
                    tableSongsBody.append(tr);
                }

                for (let i = 0; i < genres.length; i++) {
                    let id = genres[i].id;
                    let name = genres[i].name;
                    let createTime = new Date(genres[i].createdAt);

                    let tr = $("<tr/>");
                    tr.append(`
                            <td>${id}</td>   
                            <td>${name}</td>
                            <td>${checkTime(createTime.getHours()) + ':' +
                                    checkTime(createTime.getMinutes()) + ':' +
                                    checkTime(createTime.getSeconds())}</td>
                            </td>`);
                    tableGenresBody.append(tr);
                }
            }
        });
    }

    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
});